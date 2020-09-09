package ru.practiceground.presentation.roomlivedata.pages.favs

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practiceground.App
import ru.practiceground.data.room.LikeableDataBase
import ru.practiceground.data.room.repos.LikeableRepository
import ru.practiceground.presentation.base.BaseViewModel
import ru.practiceground.presentation.roomlivedata.ClickHandler
import ru.practiceground.presentation.roomlivedata.LikeableItem

class PageFavsViewModel : BaseViewModel() {

    private val repository: LikeableRepository =
        LikeableRepository(LikeableDataBase.getInstance(App.context, viewModelScope).likeableDao())

    val items = MediatorLiveData<Pair<List<LikeableItem>, DiffUtil.DiffResult>>().apply {
        addSource(repository.favs) { newValue ->
            viewModelScope.launch(Dispatchers.IO) {
                val diffUtilResult =
                    DiffUtil.calculateDiff(LikeableItem.DiffsCallback(value?.first ?: emptyList(), newValue))
                postValue(newValue to diffUtilResult)
            }
        }
    }
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