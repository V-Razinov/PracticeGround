package ru.practiceground.presentation.fingerprintcheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.viewModels
import ru.practiceground.databinding.FragmentFingerprintBinding
import ru.practiceground.presentation.base.BaseFragment

class FingerPrintFragment : BaseFragment() {

    override val viewModel: FingerPrintViewModel by viewModels()

    private lateinit var binding: FragmentFingerprintBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFingerprintBinding.inflate(inflater, container, false).apply {
            fingerprintCheckBtn.setOnClickListener { viewModel.onCheckFingerPrintClick() }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    private fun subscribe() {
        with(viewModel) {
            showBiometricPrompt.observe {
                val info = BiometricPrompt.PromptInfo.Builder()
                    .setTitle(it.title)
                    .setSubtitle(it.subtitle)
                    .setNegativeButtonText(it.negativeText)
                    .build()
                val prompt = BiometricPrompt(this@FingerPrintFragment, it.executor, it.callback)
                prompt.authenticate(info)
            }
            fingerPrintStatus.observe(binding.fingerprintStatusTv::setText)
        }
    }
}