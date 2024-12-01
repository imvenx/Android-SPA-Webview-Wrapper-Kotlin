package com.example.android_webview_wrapper_kotlin

import WebViewPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import fi.iki.elonen.NanoHTTPD
import android.content.res.AssetManager
import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {

    private var localServer: LocalWebServer? = null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {
            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 1001)

        // Start the local HTTP server
        startLocalServer()

        // Set the content using Compose
        setContent {
            WebViewPage()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the local server when the activity is destroyed
        stopLocalServer()
    }

    /** Starts the local HTTP server */
    private fun startLocalServer() {
        localServer = LocalWebServer(8080, assets)
        localServer?.start()
    }

    /** Stops the local HTTP server */
    private fun stopLocalServer() {
        localServer?.stop()
    }

    /** Local HTTP server class serving files from the assets folder */
    private class LocalWebServer(
        port: Int,
        private val assetManager: AssetManager
    ) : NanoHTTPD(port) {

        override fun serve(session: IHTTPSession): Response {
            var uri = session.uri
            // Redirect root to index.html
            if (uri == "/") {
                uri = "/index.html"
            }

            return try {
                // Open the requested file from the assets folder
                val fileStream = assetManager.open(uri.removePrefix("/"))
                // Determine the MIME type
                val mimeType = getMimeType(uri)
                // Serve the file as a chunked response
                newChunkedResponse(Response.Status.OK, mimeType, fileStream)
            } catch (e: Exception) {
                // File not found, return 404 response
                newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "404 Not Found")
            }
        }

        /** Determines the MIME type based on the file extension */
        fun getMimeType(uri: String?): String? {
            return when {
                uri == null -> null
                uri.endsWith(".html") -> "text/html"
                uri.endsWith(".js") -> "application/javascript"
                uri.endsWith(".css") -> "text/css"
                uri.endsWith(".png") -> "image/png"
                uri.endsWith(".jpg") || uri.endsWith(".jpeg") -> "image/jpeg"
                uri.endsWith(".gif") -> "image/gif"
                uri.endsWith(".svg") -> "image/svg+xml"
                else -> "application/octet-stream"
            }
        }
    }
}