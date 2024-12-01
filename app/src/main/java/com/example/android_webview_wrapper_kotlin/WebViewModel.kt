import android.app.Application
import android.webkit.PermissionRequest
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

            // Load your initial URL
            loadUrl("http://localhost:8080/")
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Properly destroy the WebView
        webView.removeAllViews()
        webView.destroy()
    }
}