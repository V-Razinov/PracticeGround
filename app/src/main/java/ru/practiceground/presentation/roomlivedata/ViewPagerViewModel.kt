package ru.practiceground.presentation.roomlivedata

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practiceground.data.room.entity.LikeableEntity
import ru.practiceground.data.room.repos.LikeableRepository
import ru.practiceground.presentation.base.BaseViewModel
import javax.inject.Inject

class ViewPagerViewModel : BaseViewModel() {

    @Inject
    lateinit var repository: LikeableRepository

    init { appComponent.viewPagerComponent().inject(this) }

    fun onAddClick(text: String, currentItem: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(LikeableEntity(text = text, isLiked = currentItem == 1))
        }
    }
}