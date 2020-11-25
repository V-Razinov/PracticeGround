package ru.practiceground.presentation.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import ru.practiceground.R
import ru.practiceground.databinding.FragmentDrawerBinding
import ru.practiceground.other.extensions.setOnMenuItemClickListener
import ru.practiceground.presentation.base.BaseFragment

class DrawerFragment : BaseFragment() {

    override val viewModel: DrawerViewModel by viewModels()
    private lateinit var binding: FragmentDrawerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDrawerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.drawerTb.apply {
            setNavigationOnClickListener { binding.drawerDl.openDrawer(GravityCompat.START) }
            setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.drawer_tb_menu) {
                    binding.drawerDl.openDrawer(GravityCompat.END)
                }
                item.itemId == R.id.drawer_tb_menu
            }
        }
        binding.drawerDl.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerOpened(drawerView: View) = Unit
            override fun onDrawerClosed(drawerView: View) = Unit
            override fun onDrawerStateChanged(newState: Int) = Unit

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                binding.drawerContent.translationX =
                    drawerView.width * slideOffset * if (binding.drawerDl.isDrawerVisible(GravityCompat.START)) 1 else -1
            }
        })
        binding.drawerNavView.setOnMenuItemClickListener(viewModel::onMenuClick)
        binding.drawerNavView2.setOnMenuItemClickListener(viewModel::onMenuClick)
    }
}