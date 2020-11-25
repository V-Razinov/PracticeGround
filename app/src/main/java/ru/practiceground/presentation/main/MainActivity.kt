package ru.practiceground.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ru.practiceground.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        viewModel.onCreate(supportFragmentManager, binding.container.id, ::finish)
        viewModel.showLoader.observe(this, binding.progressBar::isVisible::set)
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}