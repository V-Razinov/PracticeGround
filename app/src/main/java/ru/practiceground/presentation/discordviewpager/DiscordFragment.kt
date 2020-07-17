package ru.practiceground.presentation.discordviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.practiceground.R
import ru.practiceground.databinding.DiscordFragmentBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment

class DiscordFragment : BaseFragment() {

    override val viewModel: DiscordViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = getBinding<DiscordFragmentBinding>(container, R.layout.discord_fragment)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}