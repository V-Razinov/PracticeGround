package ru.practiceground.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practiceground.data.room.LIKEABLE_IS_LIKED
import ru.practiceground.data.room.LIKEABLE_TEXT
import ru.practiceground.data.room.TABLE_NAME_LIKEABLE
import ru.practiceground.presentation.roomlivedata.LikeableItem

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