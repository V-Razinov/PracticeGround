package ru.practiceground.presentation.vk

class TabItem(
    val title: String,
    var isChecked: Boolean = false
)

sealed class Command {
    class ShowStoriesView(val xy: Pair<Float, Float>) : Command()
    object ShowCreatePostDialog : Command()
}

class Topic()