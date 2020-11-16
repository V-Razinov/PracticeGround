package ru.practiceground.presentation.discord

import androidx.lifecycle.MutableLiveData
import ru.practiceground.presentation.base.BaseViewModel

class DiscordViewModel : BaseViewModel() {

    val messages = MutableLiveData(listOf(
        MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn"),
        MessageItem(null, "Farewell...", "Today at 12:02 PM", "keep plebn")
    ))
}