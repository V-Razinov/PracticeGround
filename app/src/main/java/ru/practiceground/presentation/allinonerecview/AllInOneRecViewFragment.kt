package ru.practiceground.presentation.allinonerecview

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.R
import ru.practiceground.databinding.FragmentAllInOneBinding
import ru.practiceground.other.getBinding
import ru.practiceground.other.getColor
import ru.practiceground.presentation.base.BaseFragment

class AllInOneRecViewFragment : BaseFragment() {

    override val viewModel: AllInOneRecViewViewModel by viewModels()
    override val bgDrawable: Drawable? = ColorDrawable(getColor(R.color.whiteFFF))

    private lateinit var binding: FragmentAllInOneBinding
    private val adapter = AllInOneAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(container, R.layout.fragment_all_in_one)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.allInOneRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@AllInOneRecViewFragment.adapter
        }

        viewModel.items.setObserver(adapter::items::set)
    }
}