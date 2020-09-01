package ru.practiceground.presentation.drawer

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_drawer.*
import ru.practiceground.R
import ru.practiceground.databinding.FragmentDrawerBinding
import ru.practiceground.other.extensions.setOnMenuItemClickListener
import ru.practiceground.other.getBinding
import ru.practiceground.other.getColor
import ru.practiceground.presentation.base.BaseFragment

class DrawerFragment : BaseFragment() {

    override val viewModel: DrawerViewModel by viewModels()
    override val bgDrawable: Drawable? = ColorDrawable(getColor(R.color.whiteFFF))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = getBinding<FragmentDrawerBinding>(container, R.layout.fragment_drawer)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@DrawerFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawer_tb.setNavigationOnClickListener { drawer_dl.openDrawer(GravityCompat.START) }
        drawer_tb.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.drawer_tb_menu) drawer_dl.openDrawer(GravityCompat.END)
            item.itemId == R.id.drawer_tb_menu
        }
        drawer_nav_view.setOnMenuItemClickListener(viewModel::onMenuClick)
        drawer_nav_view2.setOnMenuItemClickListener(viewModel::onMenuClick)
    }
}