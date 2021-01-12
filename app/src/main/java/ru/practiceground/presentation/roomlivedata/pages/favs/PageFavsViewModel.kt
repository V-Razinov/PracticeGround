package ru.practiceground.presentation.roomlivedata.pages.favs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practiceground.App
import ru.practiceground.data.room.LikeableDataBase
import ru.practiceground.data.room.repos.LikeableRepository
import ru.practiceground.presentation.base.BaseViewModel
import ru.practiceground.presentation.roomlivedata.ClickHandler
import ru.practiceground.presentation.roomlivedata.LikeableItem
import javax.inject.Inject

class PageFavsViewModel : BaseViewModel() {

    @Inject
    lateinit var repository: LikeableRepository

    init { appComponent.viewPagerComponent().inject(this) }

    val items: LiveData<PagedList<LikeableItem>> = repository.favsPaging
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