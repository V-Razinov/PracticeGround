package ru.practiceground.presentation.fingerprintcheck

import androidx.biometric.BiometricPrompt
import java.util.concurrent.Executor

class BiometricDialogData(
    val title: String,
    val subtitle: String,
    val negativeText: String,
    val executor: Executor,
    val callback: BiometricPrompt.AuthenticationCallback
)