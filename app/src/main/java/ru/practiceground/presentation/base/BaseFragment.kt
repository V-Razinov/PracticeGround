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
import ru.practiceground.other.getColor

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel
    abstract val bgDrawable: Drawable?
    protected val defaultBgColor = ColorDrawable(getColor(R.color.whiteFFF))

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

    protected fun <T> MutableLiveData<T>.setObserver(action: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer(action))
    }

    protected fun <T> LiveData<T>.setObserver(action: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer(action))
    }

    private fun setBgDrawable() {
        activity?.window?.setBackgroundDrawable(bgDrawable)
    }
}