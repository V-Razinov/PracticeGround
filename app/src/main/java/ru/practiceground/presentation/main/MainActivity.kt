package ru.practiceground.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import ru.practiceground.R
import ru.practiceground.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MainActivityBinding = MainActivityBinding.inflate(layoutInflater)
        binding.vm = viewModel
        setContentView(binding.root)
        viewModel.onCreate(supportFragmentManager, binding.container.id, ::finish)
        viewModel.showLoader.observe(this) {
            binding.progressBar.isVisible = it
        }
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }
}