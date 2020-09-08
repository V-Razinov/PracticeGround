package ru.practiceground.presentation.viewpager.pages.favs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practiceground.App
import ru.practiceground.data.room.LikeableDataBase
import ru.practiceground.data.room.repos.LikeableRepository
import ru.practiceground.presentation.base.BaseViewModel
import ru.practiceground.presentation.viewpager.ClickHandler
import ru.practiceground.presentation.viewpager.LikeableItem

class PageFavsViewModel : BaseViewModel() {

    private val repository: LikeableRepository by lazy {
        LikeableRepository(LikeableDataBase.getInstance(App.context, viewModelScope).likeableDao())
    }
    val items: LiveData<List<LikeableItem>> by lazy { repository.favs }
    val clickHandler = MutableLiveData(ClickHandler(::onLikeClick, ::onDeleteClick))

    private fun onLikeClick(item: LikeableItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(item)
        }
    }

    private fun onDeleteClick(item: LikeableItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(item)
        }
    }
}