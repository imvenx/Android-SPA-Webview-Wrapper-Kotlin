import android.app.Application
import android.view.ViewGroup

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun WebViewPage() {
    val context = LocalContext.current

    // Obtain the WebViewModel with a custom factory
    val viewModel: WebViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WebViewModel(context.applicationContext as Application) as T
            }
        }
    )

    val webView = viewModel.webView

    // Observe orientation changes and update layout parameters
    SideEffect {
        // Update the WebView's layout parameters to match the new dimensions
        webView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        // Request layout to apply changes
        webView.requestLayout()
    }

    AndroidView(
        factory = { webView },
        modifier = Modifier.fillMaxSize()
    )
}

