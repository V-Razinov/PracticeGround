package ru.practiceground.presentation.viewpager

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import ru.practiceground.R
import ru.practiceground.databinding.FragmentViewPagerBinding
import ru.practiceground.other.getBinding
import ru.practiceground.other.getDialogPadding
import ru.practiceground.presentation.base.BaseFragment

class ViewPagerFragment : BaseFragment() {

    override val viewModel: ViewPagerViewModel by viewModels()
    override val bgDrawable: Drawable? = defaultBgColor
    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(container, R.layout.fragment_view_pager)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pager.adapter = ViewPagerAdapter(activity ?: return)

        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> "All"
                1 -> "Favs"
                else -> "Error"
            }
        }.attach()

        binding.fab.setOnClickListener {
            context?.let { context ->
                var entityText = ""
                AlertDialog.Builder(context)
                    .setTitle("Enter new entity")
                    .setView(FrameLayout(context).apply {
                        addView(EditText(context).apply {
                            doAfterTextChanged { it?.toString()?.let { entityText = it } }
                            layoutParams = MarginLayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.WRAP_CONTENT).apply {
                                val margin = getDialogPadding(context)
                                setMargins(margin, margin.div(2), margin, 0)
                            }
                        })
                    })
                    .setPositiveButton("Add") { _, _ -> if (entityText.isNotEmpty()) viewModel.onAddClick(entityText) }
                    .setNegativeButton("Close") { _, _ -> }
                    .show()
            }
        }
    }
}