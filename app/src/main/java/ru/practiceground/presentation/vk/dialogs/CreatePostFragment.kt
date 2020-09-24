package ru.practiceground.presentation.vk.dialogs

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.viewModels
import ru.practiceground.App
import ru.practiceground.R
import ru.practiceground.databinding.FragmentCreatePostBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment

class CreatePostFragment : BaseFragment() {

    override val viewModel: CreatePostViewModel by viewModels()
    override val bgDrawable: Drawable? = null

    private lateinit var binding: FragmentCreatePostBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(inflater, container, R.layout.fragment_create_post)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.apply {
            setNavigationOnClickListener { App.router.back() }
            setOnMenuItemClickListener { item ->
                (item.itemId == R.id.menu_item_check).also { if (it) viewModel.onCheckClick() }
            }
        }
        binding.more.apply {
            val popMenu = PopupMenu(ContextThemeWrapper(context, R.style.PopupMenuTheme), this)

            val items = listOf("Video", "Document", "Poll", "Map", "Product")
            items.forEachIndexed { index, item ->
                popMenu.menu.add(index, index, index, item)
            }

            popMenu.setOnMenuItemClickListener { menuItem ->
                Toast.makeText(context, items[menuItem.itemId], Toast.LENGTH_LONG).show()
                true
            }
            setOnClickListener { popMenu.show() }
        }
    }
}