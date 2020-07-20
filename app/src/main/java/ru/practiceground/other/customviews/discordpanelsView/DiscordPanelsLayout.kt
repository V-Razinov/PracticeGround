package ru.practiceground.other.customviews.discordpanelsView

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import kotlinx.android.synthetic.main.discord_fragment.view.*
import ru.practiceground.R
import ru.practiceground.other.getColor
import kotlin.math.abs

@SuppressLint("ClickableViewAccessibility")
class DiscordPanelsLayout : FrameLayout {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, style: Int) : super(ctx, attrs, style)

    enum class MoveDirection {
        LEFT, RIGHT, NONE
    }

    private lateinit var panelCenter: ViewGroup
    private lateinit var panelStart: ViewGroup
    private lateinit var panelEnd: ViewGroup

    private var overLappingWidth: Float = Float.NaN

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (childCount != 3)
            throw Exception("${DiscordPanelsLayout::class.java}: 3 childs required")

        panelStart = getChildAt(0) as ViewGroup
        panelEnd = getChildAt(1) as ViewGroup
        panelCenter = getChildAt(2) as ViewGroup

        post {
            overLappingWidth = panelCenter.measuredWidth * (1 - 80f / 100f)
            panelStart.updateLayoutParams<MarginLayoutParams> {
                marginEnd += overLappingWidth.toInt()
            }
            panelEnd.updateLayoutParams<MarginLayoutParams> {
                marginStart += overLappingWidth.toInt()
            }
            addBlockViewIntoCentralPanel()
        }

        setPanelStartTouchListener()
        setPanelCenterTouchListener()
        setPanelEndTouchListener()
    }

    fun movePanelToLeft() = panelCenter.animateX(-(panelCenter.width - overLappingWidth), true)

    fun movePanelToRight() = panelCenter.animateX(panelCenter.width - overLappingWidth, true)

    private fun addBlockViewIntoCentralPanel() {
        panelCenter.addView(View(context).apply {
            layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            background = ColorDrawable(getColor(R.color.black232_50_transparent))
        })
    }

    private fun setPanelCenterTouchListener() {

        var isDragging = false
        var moveDirection =
            MoveDirection.NONE
        var lastX: Float = panelCenter.x
        var deltaX: Float = Float.NaN

        panelCenter.setOnTouchListener { view, event ->
            val action: Int = event.action

            return@setOnTouchListener if (action == MotionEvent.ACTION_DOWN && !isDragging) {
                isDragging = true
                deltaX = event.x
                false
            } else if (isDragging) {
                when (action) {
                    MotionEvent.ACTION_MOVE -> {
                        view.x = view.x + event.x - deltaX
                        moveDirection = when {
                            abs(view.x) < overLappingWidth -> MoveDirection.NONE
                            lastX > event.x -> MoveDirection.RIGHT
                            else -> MoveDirection.LEFT
                        }
                        if (view.x > 0)
                            showStartPanel()
                        else
                            showEndPanel()
                        lastX = event.x
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        isDragging = false
                        when (moveDirection) {
                            MoveDirection.RIGHT -> view.animateX(view.width - overLappingWidth, true)
                            MoveDirection.LEFT -> view.animateX(-(view.width - overLappingWidth), true)
                            MoveDirection.NONE -> view.animateX(0f, false)
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }
            } else {
                false
            }
        }
    }

    private fun setPanelStartTouchListener() {

        var isDragging = false
        var moveDirection =
            MoveDirection.NONE
        var lastX: Float = panelCenter.x

        panelStart.setOnTouchListener { view, event ->
            val action: Int = event.action

            return@setOnTouchListener if (action == MotionEvent.ACTION_DOWN && !isDragging) {
                isDragging = true
                true
            } else if (isDragging) {
                when (action) {
                    MotionEvent.ACTION_MOVE -> {
                        moveDirection = if (lastX > event.x) MoveDirection.RIGHT else MoveDirection.LEFT
                        lastX = event.x
                        true
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        isDragging = false
                        if (moveDirection == MoveDirection.RIGHT)
                            panelCenter.animateX(0f, false)
                        true
                    }
                    else -> {
                        false
                    }
                }
            } else {
                false
            }
        }
    }

    private fun setPanelEndTouchListener() {

        var isDragging = false
        var moveDirection =
            MoveDirection.NONE
        var lastX: Float = panelCenter.x

        panelEnd.setOnTouchListener { view, event ->
            val action: Int = event.action

            return@setOnTouchListener if (action == MotionEvent.ACTION_DOWN && !isDragging) {
                isDragging = true
                true
            } else if (isDragging) {
                when (action) {
                    MotionEvent.ACTION_MOVE -> {
                        moveDirection = if (lastX > event.x) MoveDirection.RIGHT else MoveDirection.LEFT
                        lastX = event.x
                        true
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        isDragging = false
                        if (moveDirection == MoveDirection.LEFT)
                            panelCenter.animateX(0f, false)
                        true
                    }
                    else -> {
                        false
                    }
                }
            } else {
                false
            }
        }
    }

    private fun showStartPanel() {
        panelStart.isVisible = true
        panelEnd.isInvisible = true
    }

    private fun showEndPanel() {
        panelStart.isInvisible = true
        panelEnd.isVisible = true
    }

    private fun View.animateX(toX: Float, blockCenterPanel: Boolean) {
        animate()
            .x(toX)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    panel_center_block_view.isVisible = blockCenterPanel
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationStart(animation: Animator?) {}
            })
            .setUpdateListener {
                if (x > 0) showStartPanel() else showEndPanel()
            }
            .start()
    }
}