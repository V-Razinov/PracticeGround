package ru.practiceground.data.room.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import ru.practiceground.data.room.dao.LikeableDao
import ru.practiceground.data.room.entity.LikeableEntity
import ru.practiceground.presentation.roomlivedata.LikeableItem

class LikeableRepository(private val likeableDao: LikeableDao) {

    val allPaging: LiveData<PagedList<LikeableItem>> = likeableDao.getAllPaging().map(::LikeableItem).toLiveData(config)
    val favsPaging: LiveData<PagedList<LikeableItem>> = likeableDao.getFavsPaging().map(::LikeableItem).toLiveData(config)

    val all: LiveData<List<LikeableItem>> = Transformations.map(likeableDao.getAll(), ::mapToLikeableItem)
    val favs: LiveData<List<LikeableItem>> = Transformations.map(likeableDao.getFavs(), ::mapToLikeableItem)

    private fun mapToLikeableItem(items: List<LikeableEntity>) = items.map(::LikeableItem)

    private val config: PagedList.Config get() = Config(
        pageSize = 20,
        prefetchDistance = 40,
        enablePlaceholders = false,
        initialLoadSizeHint = 20
    )

    suspend fun insert(item: LikeableEntity) {
        likeableDao.insert(item)
    }

    suspend fun update(item: LikeableItem) {
        likeableDao.update(LikeableEntity(item))
    }

    suspend fun delete(item: LikeableItem) {
        likeableDao.delete(LikeableEntity(item))
    }
}