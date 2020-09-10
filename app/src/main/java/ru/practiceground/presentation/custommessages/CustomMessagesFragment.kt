package ru.practiceground.presentation.custommessages

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.toast_custom.view.*
import ru.practiceground.R
import ru.practiceground.databinding.FragmentCustomMessagesBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment
import kotlin.random.Random

class CustomMessagesFragment : BaseFragment() {
    override val viewModel: CustomMessagesViewModel by viewModels()
    override val bgDrawable: Drawable? = defaultBgColor
    private lateinit var binding: FragmentCustomMessagesBinding
    private val gravities = listOf(
        Gravity.START, Gravity.START or Gravity.TOP,
        Gravity.TOP, Gravity.TOP or Gravity.END,
        Gravity.END, Gravity.END or Gravity.BOTTOM,
        Gravity.BOTTOM, Gravity.BOTTOM or Gravity.START,
        Gravity.CENTER
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(container, R.layout.fragment_custom_messages)
        binding.lifecycleOwner = this
        binding.vm = viewModel
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

    private fun showToast(text: String, gravity: Int) {
        Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(gravity, 0, 0)
            view = layoutInflater.inflate(R.layout.toast_custom, null).apply {
                this.toast_text.text = text
            }
        }.show()
    }

    private fun showSnackBar(text: String, actionText: String, action: () -> Unit) {
        Snackbar
            .make(binding.coordinator, text, Snackbar.LENGTH_LONG)
            .setAction(actionText) { action() }
            .show()
    }
}