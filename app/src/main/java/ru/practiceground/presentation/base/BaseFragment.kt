package ru.practiceground.presentation.base

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.practiceground.R
import ru.practiceground.other.base.SingleLiveEvent
import ru.practiceground.other.getColor

abstract class BaseFragment : Fragment() {
    abstract val viewModel: BaseViewModel
    abstract val bgDrawable: Drawable?
    protected val defaultBgColor get() = ColorDrawable(getColor(R.color.whiteFFF))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
        setBgDrawable()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    protected inline fun <T> MutableLiveData<T>.setObserver(crossinline action: (T) -> Unit) {
        observe(viewLifecycleOwner) { action(it) }
    }

    protected inline fun <T> SingleLiveEvent<T>.setObserver(crossinline action: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer { action.invoke(it ?: return@Observer) })
    }

    protected inline fun SingleLiveEvent<Unit>.setUnitObserver(crossinline action: () -> Unit) {
        observe(viewLifecycleOwner, { it ?: return@observe; action() })
    }

    protected inline fun <T> LiveData<T>.setObserver(crossinline action: (T) -> Unit) {
        observe(viewLifecycleOwner) { action(it) }
    }

    private fun setBgDrawable() {
        bgDrawable?.let { bgDrawable -> activity?.window?.setBackgroundDrawable(bgDrawable) }
    }
}