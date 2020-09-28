package ru.practiceground.presentation.vk.dialogs

import androidx.lifecycle.MutableLiveData
import ru.practiceground.presentation.base.BaseViewModel
import ru.practiceground.presentation.vk.Topic
import java.util.*

class CreatePostViewModel : BaseViewModel() {
    val isPublic = MutableLiveData(true)
    val time = MutableLiveData<Calendar>()
    val topics = MutableLiveData<List<Topic>>(emptyList())

    fun onCheckClick() {
        router.back()
    }

    fun onPostTypeClick() {
        isPublic.value?.let { isPublic.value = !it }
    }

    fun onTimeClick() {
        if (time.value == null)
            time.value = Calendar.getInstance()
        else
            time.value = null
    }

    fun onTopicClick() {
        if (topics.value?.isEmpty() == true)
            topics.value = listOf(Topic())
        else
            topics.value = emptyList()
    }

    fun onChooseBgClick() {}

    fun onPickAttachmentsClick() {}

    fun onPickMusicClick() {}

    fun onTagsCLick() {}

    fun onSettingsClick() {}
}