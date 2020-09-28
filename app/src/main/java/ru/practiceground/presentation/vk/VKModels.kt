package ru.practiceground.presentation.vk

class TabItem(
    val title: String,
    var isChecked: Boolean = false
)

abstract class Command

class ShowStoriesView(val xy: Pair<Float, Float>) : Command()
class ShowCreatePostDialog : Command()

class Topic()