package ru.practiceground.other.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import ru.practiceground.R
import ru.practiceground.other.extensions.toDp
import ru.practiceground.other.getColor
import ru.practiceground.presentation.vk.pages.news.StoriesItem
import ru.practiceground.presentation.vk.pages.news.StoryItem

class StoriesView : ConstraintLayout {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, style: Int) : super(ctx, attrs, style)

    private val ZOOM_ANIMATION_DURATION = 500L

    private val root: ConstraintLayout
    private val playerView: PlayerView
    private val timeLinesLL: LinearLayout
    private val avatar: CardView
    private val close: ImageView
    private val camera: TextView
    private val goToStore: TextView
    private val resend: ImageView
    private val more: ImageView

    private var onAvatarClick: () -> Unit = { }
    private var onCloseClick: () -> Unit = { }
    private var onCameraClick: () -> Unit = { }
    private var onGoToStoreClick: () -> Unit = { }
    private var onResendClick: () -> Unit = { }
    private var onMoreClick: () -> Unit = { }

    private var defX = 0f
    private var defY = 0f
    private var fromX = 0f
    private var fromY = 0f

    private var stories: List<StoryItem> = emptyList()
        set(value) {
            field = value
            mediaItems = value.map { MediaItem.fromUri(it.storyLink) }
        }
    private var mediaItems: List<MediaItem> = emptyList()
    private val exoPlayer: SimpleExoPlayer by lazy { SimpleExoPlayer.Builder(context).build() }

    init {
        with (inflate(context, R.layout.view_stories, this)) {
            root = findViewById(R.id.root)
            timeLinesLL = findViewById(R.id.time_lines)
            playerView = findViewById(R.id.player_video_view)
            avatar = findViewById(R.id.avatar_card)
            close = findViewById(R.id.close_iv)
            camera = findViewById(R.id.camera_iv)
            goToStore = findViewById(R.id.go_to_store)
            resend = findViewById(R.id.resend_iv)
            more = findViewById(R.id.more_iv)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        exoPlayer.stop()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isVisible = false

        playerView.player = exoPlayer

        avatar.setOnClickListener {
            onAvatarClick()
        }
        close.setOnClickListener {
            onCloseClick()
            hide()
        }
        camera.setOnClickListener {
            onCameraClick()
        }
        goToStore.setOnClickListener {
            onGoToStoreClick()
        }
        resend.setOnClickListener {
            onResendClick()
        }
        more.setOnClickListener {
            onMoreClick()
        }
    }

    fun setStories(item: StoriesItem) {
        stories = item.stories

        timeLinesLL.removeAllViews()
        item.stories.forEachIndexed { index, _ ->
            timeLinesLL.addView(getTimeLineView())
            exoPlayer.addMediaSource(getMediaSource(mediaItems[index]))
        }
    }

    fun show(fromX: Float, fromY: Float, toX: Float = defX, toY: Float = defY) {
        exoPlayer.prepare()

        isVisible = true
        scaleX = 0f
        scaleY = 0f

        post {
            this.fromX = fromX - root.width.div(2)
            this.fromY = fromY - root.height.div(2)

            x = this.fromX
            y = this.fromY

            zoomIn(toX, toY) { exoPlayer.playWhenReady = true }
        }
    }

    fun hide() {
        exoPlayer.playWhenReady = false
        zoomOut(fromX, fromY, defX, defY)
    }

    fun setDefXY(x: Float, y: Float) {
        defX = x
        defY = y
    }

    fun setOnCloseClickAction(onCloseClick: () -> Unit) {
        this.onCloseClick = onCloseClick
    }

    private fun getMediaSource(mediaItem: MediaItem): ProgressiveMediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(context, "farewell")
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
    }

    private fun getTimeLineView() = View(context).apply {
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 4.toDp(), 1f).apply {
            marginStart = 4.toDp()
            marginEnd = 4.toDp()
        }
        background = ContextCompat.getDrawable(context, R.drawable.background_rect_filled_16)?.apply {
            setTint(getColor(R.color.whiteFFF))
        }
    }

    private fun zoomIn(toX: Float, toY: Float, endAction: () -> Unit) = animate()
        .scaleX(1f)
        .scaleY(1f)
        .setDuration(ZOOM_ANIMATION_DURATION)
        .x(toX)
        .y(toY)
        .withEndAction { endAction() }
        .start()

    private fun zoomOut(toX: Float, toY: Float, defX: Float, defY: Float) = animate()
        .scaleX(0f)
        .scaleY(0f)
        .setDuration(ZOOM_ANIMATION_DURATION)
        .x(toX)
        .y(toY)
        .withEndAction {
            isVisible = false
            x = defX
            y = defY
        }
        .start()
}