package ru.practiceground.presentation.custommessages

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import ru.practiceground.databinding.FragmentCustomMessagesBinding
import ru.practiceground.databinding.ToastCustomBinding
import ru.practiceground.presentation.base.BaseFragment
import kotlin.random.Random

class CustomMessagesFragment : BaseFragment() {
    override val viewModel: CustomMessagesViewModel by viewModels()
    private lateinit var binding: FragmentCustomMessagesBinding
    private val gravities = listOf(
        Gravity.START, Gravity.START or Gravity.TOP,
        Gravity.TOP, Gravity.TOP or Gravity.END,
        Gravity.END, Gravity.END or Gravity.BOTTOM,
        Gravity.BOTTOM, Gravity.BOTTOM or Gravity.START,
        Gravity.CENTER
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCustomMessagesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.showToastBtn.setOnClickListener {
            showToast("Я тостер", gravities.getRandom())
            binding.showToastBtn.animate()
                .scaleXBy(0.05f)
                .scaleYBy(0.05f)
                .setDuration(100)
                .withEndAction {
                    binding.showToastBtn.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start()
                }.start()
        }
        binding.showSnackBar.setOnClickListener {
            showSnackBar("Сообщение", "Eбани по мне") {
                showToast("Больно", gravities.getRandom())
            }
        }
    }

    private fun <T>Collection<T>.getRandom(): T = elementAt(Random.nextInt(size))

    @Suppress("DEPRECATION")
    private fun showToast(text: String, gravity: Int) {
        Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(gravity, 0, 0)
            view = ToastCustomBinding.inflate(layoutInflater, null, false).apply {
                toastText.text = text
            }.root
        }.show()
    }

    private fun showSnackBar(text: String, actionText: String, action: () -> Unit) {
        Snackbar
            .make(binding.coordinator, text, Snackbar.LENGTH_LONG)
            .setAction(actionText) { action() }
            .show()
    }
}