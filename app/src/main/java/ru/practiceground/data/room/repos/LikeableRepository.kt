package ru.practiceground.data.room.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.practiceground.data.room.dao.LikeableDao
import ru.practiceground.data.room.entity.LikeableEntity
import ru.practiceground.presentation.roomlivedata.LikeableItem

class LikeableRepository(private val likeableDao: LikeableDao) {

    val all: LiveData<List<LikeableItem>> = Transformations.map(likeableDao.getAll(), ::mapToLikeableItem)
    val favs: LiveData<List<LikeableItem>> = Transformations.map(likeableDao.getFavs(), ::mapToLikeableItem)

    private fun mapToLikeableItem(items: List<LikeableEntity>) = items.map(::LikeableItem)

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