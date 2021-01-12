package ru.practiceground.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practiceground.data.room.dao.LikeableDao
import ru.practiceground.data.room.entity.LikeableEntity

@Database(entities = [LikeableEntity::class], version = 2, exportSchema = false)
abstract class LikeableDataBase : RoomDatabase() {

    abstract fun likeableDao(): LikeableDao
}