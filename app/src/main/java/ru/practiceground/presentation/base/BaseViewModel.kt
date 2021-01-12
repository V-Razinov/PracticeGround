package ru.practiceground.presentation.base

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import ru.practiceground.App

abstract class BaseViewModel : ViewModel() {

    protected val router = App.router
    protected val context get() = App.context
    protected val appComponent get() = App.appComponent

    open fun onViewCreated() = Unit

    open fun onResume() = Unit

    open fun onStart() = Unit

    open fun onPause() = Unit

    open fun onStop() = Unit

    protected fun getString(@StringRes id: Int, vararg formatArgs: String) = context.getString(id, *formatArgs)

    protected fun showMessage(@StringRes id: Int) {
        Toast.makeText(context, getString(id), Toast.LENGTH_LONG).show()
    }

    protected fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}