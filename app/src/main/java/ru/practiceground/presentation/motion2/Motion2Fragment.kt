package ru.practiceground.presentation.motion2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practiceground.R
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.base.BaseViewModel

class Motion2ViewModel: BaseViewModel()

class Motion2Fragment : BaseFragment() {
    override val viewModel: BaseViewModel = Motion2ViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.motion2_fragment, container, false)
    }
}