package ru.practiceground.presentation.roomlivedata.pages.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practiceground.data.room.LikeableDataBase
import ru.practiceground.data.room.repos.LikeableRepository
import ru.practiceground.presentation.base.BaseViewModel
import ru.practiceground.presentation.roomlivedata.ClickHandler
import ru.practiceground.presentation.roomlivedata.LikeableItem

class PageAllViewModel : BaseViewModel() {

    private val repository: LikeableRepository =
        LikeableRepository.getInstance(LikeableDataBase.getInstance(context, viewModelScope).likeableDao())
// Если не нужна пагинация
//    val items = MediatorLiveData<Pair<List<LikeableItem>, DiffUtil.DiffResult>>().apply {
//        addSource(repository.all) { newValue ->
//            viewModelScope.launch(Dispatchers.IO) {
//                val diffUtilResult =
//                    DiffUtil.calculateDiff(LikeableItem.DiffsCallback(value?.first ?: emptyList(), newValue))
//                withContext(Dispatchers.Main) { value = newValue to diffUtilResult }
//            }
//        }
//    }
    val items: LiveData<PagedList<LikeableItem>> = repository.allPaging
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