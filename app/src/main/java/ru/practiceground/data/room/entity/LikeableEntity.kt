package ru.practiceground.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practiceground.data.room.*
import ru.practiceground.presentation.viewpager.LikeableItem

@Entity(tableName = TABLE_NAME_LIKEABLE)
data class LikeableEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = LIKEABLE_TEXT)
    val text: String,
    @ColumnInfo(name = LIKEABLE_IS_LIKED)
    val isLiked: Boolean = false
) {
    constructor(item: LikeableItem) : this(item.id, item.text, item.isLiked)
}