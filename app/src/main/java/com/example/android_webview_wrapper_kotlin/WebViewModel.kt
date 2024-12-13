import android.app.Application
import android.net.http.SslError
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.AndroidViewModel

class WebViewModel(application: Application) : AndroidViewModel(application) {
    val webView: WebView by lazy {
        WebView(application).apply {
            // Initialize settings
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = true
            settings.allowContentAccess = true

            webViewClient = WebViewClient()

            webChromeClient = object : WebChromeClient() {
                override fun onPermissionRequest(request: PermissionRequest) {
                    request.grant(request.resources)
                }
            }

            // Use this to serve the app externally while on develop mode
            // this way you won't need to do a build to see web app changes
            // this is commented because it seems google don't like it even
            // if we don't use it
//            val useDevUrl = false
//            if (useDevUrl){
//                webViewClient = object : WebViewClient() {
//                    override fun onReceivedSslError(
//                        view: WebView?,
//                        handler: SslErrorHandler?,
//                        error: SslError?
//                    ) {
//                        handler?.proceed()
//                    }
//                }
                // Here goes your project dev url
//                loadUrl("https://192.168.208.46:9000/")
//            }else{
                // This is for loading the app build that is on our assets folder
                loadUrl("http://localhost:8080/")
//            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Properly destroy the WebView
        webView.removeAllViews()
        webView.destroy()
    }
}