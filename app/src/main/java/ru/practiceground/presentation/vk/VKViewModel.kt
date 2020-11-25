package ru.practiceground.presentation.vk

import androidx.lifecycle.MutableLiveData
import ru.practiceground.other.base.SingleLiveEvent
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.base.BaseViewModel
import ru.practiceground.presentation.vk.pages.hub.HubFragment
import ru.practiceground.presentation.vk.pages.news.*

class VKViewModel : BaseViewModel() {
    val pages = MutableLiveData<List<BaseFragment>>()
    val tabs = MutableLiveData<List<TabItem>>()

    val newsItems = MutableLiveData<List<NewsPageAdapterBaseItem>>()
    val stories = MutableLiveData<StoriesItem>()
    val command = SingleLiveEvent<Command>()

    private val avatarLink = "https://sun9-63.userapi.com/Z-Gh4BQlsAlzgA_27qTz3gqZMLzVWSVtcOQq9Q/114aCMqFHgQ.jpg"

    init {
        pages.value = listOf(
            NewsPageFragment(),
            HubFragment(),
            NewsPageFragment(),
            NewsPageFragment(),
            NewsPageFragment(),
            NewsPageFragment()
        )
        tabs.value = listOf(
            TabItem("News", true),
            TabItem("Hub", false),
            TabItem("Music", false),
            TabItem("Photos", false),
            TabItem("Coronavirus", false),
            TabItem("Focus", false),
        )
        setItems()
    }

    private fun setItems() {
        stories.value = StoriesItem(
            stories = listOf(
                StoryItem(0, avatarLink, "myStory1", "https://developers.google.com/training/images/tacoma_narrows.mp4"),
                StoryItem(1, avatarLink, "myStory2", "https://developers.google.com/training/images/tacoma_narrows.mp4"),
                StoryItem(2, avatarLink, "myStory3", "https://developers.google.com/training/images/tacoma_narrows.mp4"),
                StoryItem(3, avatarLink, "myStory4", "https://developers.google.com/training/images/tacoma_narrows.mp4"),
                StoryItem(4, avatarLink, "myStory5", "https://developers.google.com/training/images/tacoma_narrows.mp4"),
                StoryItem(5, avatarLink, "myStory6", "https://developers.google.com/training/images/tacoma_narrows.mp4")
            ),
            onStoryClick = ::onStoryClick
        )
        newsItems.value = listOf(
            CreatePostItem(
                avatarLink = avatarLink,
                onCreateClick = ::onCreatePostClick,
                onClipClick = ::onClipClick,
                onLiveClick = ::onLiveClick
            ),
            getNewsPostItem(),
            getNewsPostItem(),
            getNewsPostItem(),
            getNewsPostItem(),
            getNewsPostItem()
        )
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onStoryClick(story: StoryItem, x: Float, y: Float) {
        command.value = Command.ShowStoriesView(x to y)
    }

    private fun onCreatePostClick() {
        command.value = Command.ShowCreatePostDialog
    }

    private fun onClipClick() { }

    private fun onLiveClick() { }

    @Suppress("UNUSED_PARAMETER")
    private fun onMenuItemClick(id: Int) { }

    private fun getNewsPostItem() = NewsPostItem(
        avatarLink = avatarLink,
        author = "Life.ru",
        isVerified = true,
        timePassed = "4 minutes ago",
        menuActions = listOf(
            MenuAction(id = 0, title = "Add to Bookmarks"),
            MenuAction(id = 1, title = "Notify about new posts"),
            MenuAction(id = 2, title = "Not interesting"),
            MenuAction(id = 3, title = "Hide page's posts"),
            MenuAction(id = 4, title = "Copy Link"),
            MenuAction(id = 5, title = "Report")
        ),
        onMenuItemClick = ::onMenuItemClick,
        text = "Подруги позируют в одинаковой одежде, показывая, как одни и " +
                "те же наряды выглядят на девушках разных размеров",
        imageLink = "https://sun9-16.userapi.com/U1PBCJVPyHLHuC4aAwuB1-v1bCvnThXqKv_ZtA/spn4ZEOu6po.jpg",
        likesAmount = "12",
        commentsAmount = "30",
        rePostsAmount = "0",
        viewsAmount = "500"
    )
}