package ru.practiceground.presentation.service

import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.practiceground.R
import ru.practiceground.databinding.FragmentMyServiceBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment

class MyServiceFragment : BaseFragment() {
    override val viewModel: MyServiceViewModel by viewModels()
    override val bgDrawable: Drawable? = null
    private lateinit var binding: FragmentMyServiceBinding
    private val receiver = MyBroadcastReceiver()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(container, R.layout.fragment_my_service)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.note.text = HtmlCompat.fromHtml(
            "Оповещения отправяются <u>раздельно</u> через <b>Service</b> и <b>BroadcastReceiver</b>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.send.setOnClickListener {
            viewModel.sendMessage(context ?: return@setOnClickListener)
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.let { acty ->
            viewModel.createBoundService(acty)
            acty.registerReceiver(receiver, IntentFilter(MyBroadcastReceiver.RECEIVER_ACTION))
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.let { acty ->
            acty.stopService(Intent(acty, MyService::class.java))
            viewModel.stopBoundService(acty)
            acty.unregisterReceiver(receiver)
        }
    }
}