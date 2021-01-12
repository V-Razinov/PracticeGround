package ru.practiceground.presentation.vk.dialogs

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.viewModels
import ru.practiceground.App
import ru.practiceground.R
import ru.practiceground.databinding.FragmentCreatePostBinding
import ru.practiceground.other.extensions.dp
import ru.practiceground.other.extensions.int
import ru.practiceground.other.extensions.setCompoundDrawableStart
import ru.practiceground.other.getColor
import ru.practiceground.other.getDrawable
import ru.practiceground.presentation.base.BaseFragment

class CreatePostFragment : BaseFragment() {

    override val viewModel: CreatePostViewModel by viewModels()

    private lateinit var binding: FragmentCreatePostBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false).apply {
            toolbar.apply {
                setNavigationOnClickListener { App.router.back() }
                setOnMenuItemClickListener { item ->
                    (item.itemId == R.id.menu_item_check).also { if (it) viewModel.onCheckClick() }
                }
            }
            privacy.setOnClickListener { viewModel.onPostTypeClick() }
            time.setOnClickListener { viewModel.onTimeClick() }
            topic.setOnClickListener { viewModel.onTopicClick() }
            palette.setOnClickListener { viewModel.onChooseBgClick() }
            attachments.setOnClickListener { viewModel.onPickAttachmentsClick() }
            music.setOnClickListener { viewModel.onPickMusicClick() }
            more.setOnClickListener { showMoreOptions(more) }
            settings.setOnClickListener { viewModel.onSettingsClick() }
            tag.setOnClickListener { viewModel.onTagsCLick() }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            isPublic.observe { isPublic ->
                binding.privacy.setCheckedColor(
                    !isPublic,
                    if (isPublic) R.drawable.ic_round_person_outline_24 else R.drawable.ic_round_lock_24
                )
            }
            time.observe(viewLifecycleOwner) { binding.time.setCheckedColor(it == null) }
            topics.observe { binding.topic.setCheckedColor(it.isNotEmpty()) }
        }
    }

    private fun showMoreOptions(targetView: ImageView) {
        val popMenu = PopupMenu(ContextThemeWrapper(targetView.context, R.style.PopupMenuTheme), targetView)
        val items = listOf("Video", "Document", "Poll", "Map", "Product")
        items.forEachIndexed { index, item ->
            popMenu.menu.add(Menu.NONE, Menu.NONE, index, item)
        }
        popMenu.setOnMenuItemClickListener { menuItem ->
            Toast.makeText(targetView.context, items[menuItem.itemId], Toast.LENGTH_LONG).show()
            true
        }
        popMenu.show()
    }

    private fun TextView.setCheckedColor(checked: Boolean, @DrawableRes drawableRes: Int) {
        val color = getColor(if (checked) R.color.blue728 else R.color.grey)
        setCompoundDrawableStart(getDrawable(drawableRes))
        (background as? GradientDrawable)?.setStroke(
            (if (checked) 2.dp else 1.dp).int,
            color
        )
        setTextColor(color)
        (compoundDrawables + compoundDrawablesRelative).forEach { it?.setTint(color) }
    }

    private fun TextView.setCheckedColor(checked: Boolean) {
        val color = getColor(if (checked) R.color.blue728 else R.color.grey)
        (background as? GradientDrawable)?.setStroke(
            (if (checked) 2.dp else 1.dp).int,
            color
        )
        setTextColor(color)
        (compoundDrawables + compoundDrawablesRelative).forEach { it?.setTint(color) }
    }
}