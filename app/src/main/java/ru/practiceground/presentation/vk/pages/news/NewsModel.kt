package ru.practiceground.presentation.vk.pages.news

abstract class NewsPageAdapterBaseItem(val viewType: Int) {
    companion object {
        const val ITEM_TYPE_CREATE_POST = 0
        const val ITEM_TYPE_POST = 1
    }
}

class StoriesItem(
    val stories: List<StoryItem>,
    val onStoryClick: (story: StoryItem, x: Float, y: Float) -> Unit
)

class CreatePostItem(
    val avatarLink: String,
    val onCreateClick: () -> Unit,
    val onClipClick: () -> Unit,
    val onLiveClick: () -> Unit
) : NewsPageAdapterBaseItem(ITEM_TYPE_CREATE_POST)

class NewsPostItem(
    val avatarLink: String,
    val author: String,
    val isVerified: Boolean,
    val timePassed: String,
    val menuActions: List<MenuAction>,
    val onMenuItemClick: (id: Int) -> Unit,
    val text: CharSequence,
    val imageLink: String,
    var isLiked: Boolean = false,
    val likesAmount: String,
    val commentsAmount: String,
    val rePostsAmount: String,
    val viewsAmount: String
) : NewsPageAdapterBaseItem(ITEM_TYPE_POST)

class StoryItem(
    val id: Int,
    val avatarLink: String,
    val text: String,
    val storyLink: String
)

class MenuAction(
    val id: Int,
    val title: String
)