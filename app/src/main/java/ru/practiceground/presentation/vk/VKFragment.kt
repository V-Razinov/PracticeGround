package ru.practiceground.presentation.vk

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.practiceground.App
import ru.practiceground.R
import ru.practiceground.databinding.FragmentVkBinding
import ru.practiceground.other.castTo
import ru.practiceground.other.getBinding
import ru.practiceground.other.getColor
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.vk.dialogs.CreatePostFragment

class VKFragment : BaseFragment() {

    override val viewModel: VKViewModel by activityViewModels()
    override val bgDrawable: Drawable? = ColorDrawable(getColor(R.color.black232))

    private val adapter by lazy { VKViewPagerAdapter(this@VKFragment) }
    private lateinit var binding: FragmentVkBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(inflater, container, R.layout.fragment_vk)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.apply {
            adapter = this@VKFragment.adapter
            getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            registerOnPageChangeCallback(onPageChangedCallback)
        }
        subscribe()
    }

    override fun onStop() {
        super.onStop()
        App.router.removeOnBackPressedCallback()
    }

    private fun subscribe() {
        with(viewModel) {
            tabs.setObserver(::setTabs)
            stories.setObserver(binding.stories::setStories)
            pages.setObserver {
                binding.viewPager.offscreenPageLimit = it.size
                adapter.setPages(it)
            }
            command.setObserver(::executeCommand)
        }
    }

    private val onPageChangedCallback: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.chipGroup.children.elementAtOrNull(position)?.castTo<Chip>()?.isChecked = true
        }
    }

    private fun executeCommand(command: Command) {
        when (command) {
            is ShowCreatePostDialog -> showCreatePostDialog()
            is ShowStoriesView -> showView(command.xy)
        }
    }

    private fun showCreatePostDialog() {
        App.router.addFragment(CreatePostFragment())
    }

    private fun showView(xy: Pair<Float, Float>) {
        binding.stories.apply {
            setOnCloseClickAction {
                App.router.removeOnBackPressedCallback()
            }
            setDefXY(0f, 0f)
            show(xy.first, xy.second)
            App.router.setOnBackPressedCallback {
                hide()
                App.router.removeOnBackPressedCallback()
            }
        }
    }

    private fun setTabs(tabs: List<TabItem>) {
        var prevCheckedTabId = -1
        tabs.forEach {
            val tab = (layoutInflater.inflate(R.layout.item_tab, null) as Chip).apply {
                id = View.generateViewId()
                text = it.title
                isChecked = it.isChecked
                setCheckedColor()
            }

            if (it.isChecked)
                prevCheckedTabId = tab.id

            binding.chipGroup.addView(tab)
        }
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == -1) {
                group.setCheckedById(prevCheckedTabId, true)
            } else {
                prevCheckedTabId = checkedId
                group.getChipById(checkedId)?.let { chip ->
                    binding.tabsHsv.smoothScrollTo(
                        chip.run { x.toInt() + width.div(2) } - binding.root.width.div(2),
                        binding.tabsHsv.y.toInt()
                    )
                    binding.viewPager.currentItem = group.indexOfChild(chip)
                }
            }
        }
    }

    private fun ChipGroup.getChipById(id: Int): Chip? = children.find { it.id == id }?.castTo<Chip>()

    private fun ChipGroup.setCheckedById(id: Int, checked: Boolean) {
        getChipById(id)?.apply {
            isChecked = checked
            setCheckedColor()
        }
    }

    private fun Chip.setCheckedColor() {
        val color = context.resources.getColorStateList(R.color.chip_state, null)
        chipStrokeColor = color
        setTextColor(color)
    }
}