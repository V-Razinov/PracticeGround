package ru.practiceground.presentation.base

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.practiceground.App
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    protected val router = App.router
    protected val context get() = App.context
    protected val scope = viewModelScope

    open fun onViewCreated() { }

    open fun onResume() { }

    open fun onStart() { }

    open fun onPause() { }

    open fun onStop() { }

    protected fun getString(@StringRes id: Int) = context.getString(id)

    protected fun showMessage(@StringRes id: Int) {
        Toast.makeText(context, getString(id), Toast.LENGTH_LONG).show()
    }

    protected fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}