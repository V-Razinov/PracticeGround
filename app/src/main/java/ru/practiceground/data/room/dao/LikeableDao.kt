package ru.practiceground.data.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import ru.practiceground.data.room.LIKEABLE_IS_LIKED
import ru.practiceground.data.room.TABLE_NAME_LIKEABLE
import ru.practiceground.data.room.entity.LikeableEntity

@Dao
interface LikeableDao {

    @Query("SELECT * from $TABLE_NAME_LIKEABLE ORDER BY id")
    fun getAllPaging(): DataSource.Factory<Int, LikeableEntity>

    @Query("SELECT * from $TABLE_NAME_LIKEABLE ORDER BY id")
    fun getAll(): LiveData<List<LikeableEntity>>

    @Query("SELECT * from $TABLE_NAME_LIKEABLE WHERE $LIKEABLE_IS_LIKED ORDER BY id")
    fun getFavsPaging(): DataSource.Factory<Int, LikeableEntity>

    @Query("SELECT * from $TABLE_NAME_LIKEABLE WHERE $LIKEABLE_IS_LIKED ORDER BY id")
    fun getFavs(): LiveData<List<LikeableEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: LikeableEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<LikeableEntity>)

    @Query("UPDATE $TABLE_NAME_LIKEABLE SET $LIKEABLE_IS_LIKED = :isLiked WHERE id = :id")
    fun update(id: Int, isLiked: Boolean)

    @Update(entity = LikeableEntity::class)
    fun update(entity: LikeableEntity)

    @Delete(entity = LikeableEntity::class)
    fun delete(entity: LikeableEntity)

    @Query("DELETE FROM $TABLE_NAME_LIKEABLE")
    fun clear()
}