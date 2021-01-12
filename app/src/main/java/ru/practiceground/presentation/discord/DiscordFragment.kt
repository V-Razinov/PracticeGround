package ru.practiceground.presentation.discord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ru.practiceground.R
import ru.practiceground.databinding.FragmentDiscordBinding
import ru.practiceground.presentation.base.BaseFragment

class DiscordFragment : BaseFragment() {

    override val viewModel: DiscordViewModel by viewModels()

    private lateinit var binding: FragmentDiscordBinding
    private val adapter = DiscordAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiscordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        binding.panelCenterRv.apply {
            adapter = this@DiscordFragment.adapter
            layoutManager = LinearLayoutManager(context)
            isGone = true
        }
        subscribe()
        Glide.with(this).apply {
            load(R.drawable.aniki_flip).into(binding.panelEndIv)
            load(R.drawable.rick_dance).into(binding.panelStartIv)
        }
    }

    private fun setupToolbar() {
        binding.panelCenterTb.apply {
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_menu_24)
            setNavigationOnClickListener { binding.discordPanels.movePanelToRight() }
            menu?.apply {
                findItem(R.id.search)?.setOnMenuItemClickListener {
                    Toast.makeText(context, "search click", Toast.LENGTH_SHORT).show()
                    true
                }
                findItem(R.id.members)?.setOnMenuItemClickListener {
                    binding.discordPanels.movePanelToLeft()
                    true
                }
            }
        }
    }

    private fun subscribe() {
        viewModel.apply {
            messages.observe(adapter::setItems)
        }
    }
}