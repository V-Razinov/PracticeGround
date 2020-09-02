package ru.practiceground.other.customviews

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import ru.practiceground.R
import ru.practiceground.other.getColor
import kotlin.math.abs

@SuppressLint("ClickableViewAccessibility")
class DiscordPanelsLayout : CardView {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, style: Int) : super(ctx, attrs, style)

    enum class MoveDirection {
        LEFT, RIGHT, NONE
    }

    enum class CurrentPanel {
        LEFT, RIGHT, CENTER
    }

    private lateinit var panelCenter: ViewGroup
    private lateinit var panelStart: ViewGroup
    private lateinit var panelEnd: ViewGroup
    private var centerPanelBlockView: View? = null

    private var overLappingWidth: Float = Float.NaN
    private var currentPanel = CurrentPanel.CENTER

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (childCount != 3)
            throw Exception("${DiscordPanelsLayout::class.java}: 3 childs required")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

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

    fun movePanelToCenter() = panelCenter.animateX(0f, false)

    private fun addBlockViewIntoCentralPanel() {
        centerPanelBlockView = View(context).apply {
            layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                isClickable = true
                isFocusable = true
                isFocusableInTouchMode = false
                isVisible = false
            }
            background = context.getDrawable(R.drawable.bg_rounded_10dp)
            backgroundTintList = ColorStateList.valueOf(getColor(R.color.black232_50_transparent))
        }
        panelCenter.addView(centerPanelBlockView)
    }

    private fun setPanelCenterTouchListener() {

        var isDragging = false
        var moveDirection = MoveDirection.NONE
        var lastX: Float = panelCenter.x
        var deltaX: Float = Float.NaN

        panelCenter.setOnTouchListener { view, event ->
            val action: Int = event.action

            return@setOnTouchListener if (action == MotionEvent.ACTION_DOWN && !isDragging) {
                isDragging = true
                deltaX = event.x
                true
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
                            MoveDirection.RIGHT -> movePanelToRight()
                            MoveDirection.LEFT -> movePanelToLeft()
                            MoveDirection.NONE -> movePanelToCenter()
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
        var moveDirection = MoveDirection.NONE
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
                            movePanelToCenter()
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
        var moveDirection = MoveDirection.NONE
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
                            movePanelToCenter()
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
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    centerPanelBlockView?.isVisible = blockCenterPanel
                }
            })
            .setUpdateListener {
                if (x > 0) showStartPanel() else showEndPanel()
            }
            .start()
    }
}