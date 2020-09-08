package ru.practiceground.presentation.viewpager

import ru.practiceground.data.room.entity.LikeableEntity

class LikeableItem(
    val id: Int,
    val text: String,
    var isLiked: Boolean = false
) {
    constructor(entity: LikeableEntity) : this(entity.id, entity.text, entity.isLiked)
}

class ClickHandler(
    val onLikeClick: (LikeableItem) -> Unit,
    val onDeleteClick: (LikeableItem) -> Unit
){
    companion object {
        fun empty() = ClickHandler({}, {})
    }
}