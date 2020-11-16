package ru.practiceground.presentation.roomlivedata

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practiceground.App
import ru.practiceground.data.room.LikeableDataBase
import ru.practiceground.data.room.entity.LikeableEntity
import ru.practiceground.data.room.repos.LikeableRepository
import ru.practiceground.presentation.base.BaseViewModel

class ViewPagerViewModel : BaseViewModel() {

    private val repository = LikeableRepository
        .getInstance(LikeableDataBase.getInstance(App.context, viewModelScope).likeableDao())

    fun onAddClick(text: String, currentItem: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(LikeableEntity(text = text, isLiked = currentItem == 1))
        }
    }
}