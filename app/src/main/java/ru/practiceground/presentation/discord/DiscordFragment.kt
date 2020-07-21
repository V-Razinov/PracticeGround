package ru.practiceground.presentation.discord

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_discord.*
import ru.practiceground.R
import ru.practiceground.databinding.FragmentDiscordBinding
import ru.practiceground.other.getBinding
import ru.practiceground.other.getColor
import ru.practiceground.presentation.base.BaseFragment

class DiscordFragment : BaseFragment() {

    override val viewModel: DiscordViewModel by viewModels()
    override val bgDrawable: Drawable? = ColorDrawable(getColor(R.color.whiteFFF))

    private val adapter = DiscordAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = getBinding<FragmentDiscordBinding>(container, R.layout.fragment_discord)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        panel_center_rv.apply {
            adapter = this@DiscordFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        subscribe()
        Glide.with(this).load(R.drawable.rick_dance).into(panel_start_iv)
        Glide.with(this).load(R.drawable.aniki_flip).into(panel_end_iv)
    }

    private fun setupToolbar() {
        panel_center_tb.apply {
            navigationIcon = context.getDrawable(R.drawable.ic_baseline_menu_24)
            setNavigationOnClickListener { discord_panels.movePanelToRight() }
            menu?.apply {
                findItem(R.id.search)?.setOnMenuItemClickListener {
                    Toast.makeText(context, "search click", Toast.LENGTH_SHORT).show()
                    true
                }
                findItem(R.id.members)?.setOnMenuItemClickListener {
                    discord_panels.movePanelToLeft()
                    true
                }
            }
        }
    }

    private fun subscribe() {
        viewModel.apply {
            messages.setObserver(adapter::setItems)
        }
    }
}