package ru.practiceground.presentation.roomlivedata

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ru.practiceground.R
import ru.practiceground.databinding.FragmentViewPagerBinding
import ru.practiceground.other.getBinding
import ru.practiceground.other.getColor
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
        binding.apply {
            pager.apply {
                adapter = ViewPagerAdapter(this@ViewPagerFragment)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        binding.fab.backgroundTintList = ColorStateList.valueOf(
                            getColor(if (position == 0) R.color.blue728 else R.color.grey995)
                        )
                    }
                })
            }
            TabLayoutMediator(tabs, pager) { tab, position ->
                tab.text = when (position) {
                    0 -> "All"
                    1 -> "Favs"
                    else -> "Error"
                }
            }.attach()
            fab.setOnClickListener {
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
                        .setPositiveButton("Add") { _, _ ->
                            if (entityText.isNotEmpty())
                                viewModel.onAddClick(entityText, binding.pager.currentItem)
                        }
                        .setNegativeButton("Close") { _, _ -> }
                        .show()
                }
            }
        }
    }
}