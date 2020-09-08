package ru.practiceground.presentation.fingerprintcheck

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import ru.practiceground.presentation.base.BaseViewModel

class FingerPrintViewModel : BaseViewModel() {

    val showBiometricPrompt = MutableLiveData<BiometricDialogData>()
    val fingerPrintStatus = MutableLiveData<String>("Check fingerprint")

    fun onCheckFingerPrintClick() {
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showBiometricPrompt.value = BiometricDialogData(
                    title = "Biometric login for my app",
                    subtitle = "Log in using your biometric credential",
                    negativeText = "Cancel",
                    executor = ContextCompat.getMainExecutor(context),
                    callback = object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            fingerPrintStatus.value = "Success"
                        }
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            super.onAuthenticationError(errorCode, errString)
                            fingerPrintStatus.value = "Canceled"
                        }
                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            fingerPrintStatus.value = "Failed"
                        }
                    }
                )
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                showMessage("No biometric features available on this device.")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                showMessage("Biometric features are currently unavailable.")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                showMessage("The user hasn't associated any biometric credentials with their account.")
            }
        }
    }
}