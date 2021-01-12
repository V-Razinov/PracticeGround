package ru.practiceground.presentation.roomlivedata

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewGroup.MarginLayoutParams.MATCH_PARENT
import android.view.ViewGroup.MarginLayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ru.practiceground.databinding.FragmentViewPagerBinding
import ru.practiceground.other.extensions.float
import ru.practiceground.other.getDialogPadding
import ru.practiceground.presentation.base.BaseFragment

class ViewPagerFragment : BaseFragment() {

    override val viewModel: ViewPagerViewModel by viewModels()

    private lateinit var binding: FragmentViewPagerBinding

    internal val fab get() = binding.fabCard
    internal val counter get() = binding.counter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false).apply {
            pager.apply {
                adapter = ViewPagerAdapter(this@ViewPagerFragment)
                registerOnPageChangeCallback(onPageChangeCallback(this))
            }
            TabLayoutMediator(tabs, pager) { tab, position ->
                tab.text = when (position) {
                    0 -> "All"
                    1 -> "Favs"
                    else -> "Error"
                }
            }.attach()
            root.post {
                val newX = if (pager.currentItem == 0) fabCard.width.float else 0f
                fabRight.x = newX
                counterRight.x = newX
            }
            fabCard.setOnClickListener { showAddNewEntityDialog(context ?: return@setOnClickListener) }
            counterCard.setOnClickListener { Toast.makeText(context, "Всего элементов в адаптере", Toast.LENGTH_SHORT).show() }
        }
        return binding.root
    }

    private fun onPageChangeCallback(viewPager2: ViewPager2) = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (position == 1) {
                binding.fabRight.x = 0f
                binding.counterRight.x = 0f
            } else {
                val newX = binding.fabRight.width - binding.fabCard.width.times(positionOffset)
                binding.fabRight.x = newX
                binding.counterRight.x = newX
            }
        }

        override fun onPageSelected(position: Int) = (viewPager2.adapter as ViewPagerAdapter).callResume(position)
    }

    private fun showAddNewEntityDialog(context: Context) {
        var entityText = ""
        val view = FrameLayout(context).apply {
            addView(EditText(context).apply {
                doAfterTextChanged { entityText = it?.toString() ?: return@doAfterTextChanged }
                layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                    val margin = getDialogPadding(context)
                    setMargins(margin, margin.div(2), margin, 0)
                }
            })
        }
        AlertDialog.Builder(context)
            .setTitle("Enter new entity")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                if (entityText.isNotEmpty()) {
                    viewModel.onAddClick(entityText, binding.pager.currentItem)
                }
            }
            .setNegativeButton("Close") { _, _ -> }
            .show()
    }
}