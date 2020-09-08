package ru.practiceground.data.room.repos

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.practiceground.data.room.dao.LikeableDao
import ru.practiceground.data.room.entity.LikeableEntity
import ru.practiceground.presentation.viewpager.LikeableItem

class LikeableRepository(private val likeableDao: LikeableDao) {

    val all: LiveData<List<LikeableItem>> = Transformations.map(likeableDao.getAll()) { items ->
        items.map(::LikeableItem)
    }
    val favs: LiveData<List<LikeableItem>> = Transformations.map(likeableDao.getFavs()) { items ->
        items.map(::LikeableItem)
    }

    @WorkerThread
    suspend fun insert(item: LikeableEntity) {
        likeableDao.insert(item)
    }

    @WorkerThread
    suspend fun update(item: LikeableItem) {
        likeableDao.update(LikeableEntity(item))
    }

    @WorkerThread
    suspend fun delete(item: LikeableItem) {
        likeableDao.delete(LikeableEntity(item))
    }
}