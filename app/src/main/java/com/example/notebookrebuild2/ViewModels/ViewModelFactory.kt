package sin.android.notebook.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



/*
class ViewModelFactory(// @Inject constructor(
    private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider =
            viewModels[modelClass]
                ?: throw IllegalArgumentException("model class $modelClass not found")

        @Suppress("UNCHECKED_CAST")
        return viewModelProvider.get() as T
    }
}*/
