package ru.practiceground.presentation.roomlivedata

import androidx.recyclerview.widget.DiffUtil
import ru.practiceground.data.room.entity.LikeableEntity

class LikeableItem(
    val id: Int,
    val text: String,
    var isLiked: Boolean = false
) {
    constructor(entity: LikeableEntity) : this(entity.id, entity.text, entity.isLiked)

    fun compareContent(item: LikeableItem) = text == item.text && isLiked == item.isLiked

    class DiffsCallback(
        private val oldList: List<LikeableItem>,
        private val newList: List<LikeableItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].compareContent(newList[newItemPosition])
    }
}

class ClickHandler(
    val onLikeClick: (LikeableItem) -> Unit,
    val onDeleteClick: (LikeableItem) -> Unit
){
    companion object {
        fun empty() = ClickHandler({}, {})
    }
}