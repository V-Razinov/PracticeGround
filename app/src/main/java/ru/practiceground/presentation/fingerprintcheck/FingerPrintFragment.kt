package ru.practiceground.presentation.fingerprintcheck

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.viewModels
import ru.practiceground.R
import ru.practiceground.databinding.FragmentFingerprintBinding
import ru.practiceground.other.getBinding
import ru.practiceground.other.getColor
import ru.practiceground.presentation.base.BaseFragment

class FingerPrintFragment : BaseFragment() {
    override val viewModel: FingerPrintViewModel by viewModels()
    override val bgDrawable: Drawable? = ColorDrawable(getColor(R.color.whiteFFF))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = getBinding<FragmentFingerprintBinding>(container, R.layout.fragment_fingerprint)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showBiometricPrompt.setObserver {
            val info = BiometricPrompt.PromptInfo.Builder()
                .setTitle(it.title)
                .setSubtitle(it.subtitle)
                .setNegativeButtonText(it.negativeText)
                .build()
            val prompt = BiometricPrompt(this, it.executor, it.callback)
            prompt.authenticate(info)
        }
    }
}