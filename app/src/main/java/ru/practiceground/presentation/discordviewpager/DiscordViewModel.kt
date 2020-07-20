package ru.practiceground.presentation.discordviewpager

import androidx.lifecycle.MutableLiveData
import ru.practiceground.presentation.base.BaseViewModel

class DiscordViewModel : BaseViewModel() {

    val messages = MutableLiveData<List<MessageItem>>()

    override fun onViewCreated() {
        super.onViewCreated()
        messages.value = listOf(
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
            MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn")
        )
    }
}