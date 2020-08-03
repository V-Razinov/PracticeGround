package ru.practiceground.other.views

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import ru.practiceground.other.extensions.setListener
import kotlin.math.abs
import kotlin.properties.Delegates

class DragToShowActionsLayout : ConstraintLayout {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, style: Int) : super(ctx, attrs, style)

    var currentPanel: Panels by Delegates.observable(Panels.CENTER) { _, oldValue, newValue ->
        if (oldValue != newValue) mOnCurrentPanelChangedListener?.onPanelChanged(newValue)
    }
        private set

    private lateinit var panelStart: View
    private lateinit var panelCenter: View
    private lateinit var panelEnd: View

    private var mOnCurrentPanelChangedListener: OnCurrentPanelChangedListener? = null
    private var mOnMovingListener: OnMovingListener? = null
    private var panelCenterOnClickListener: OnClickListener? = null

    private var panelCenterMinX: Float = 0f
    private var panelCenterMaxX: Float = 0f

    private var isInited = false

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (childCount != 3)
            throw Exception("${DragToShowActionsLayout::class.java}: 3 childs required (start-end-center)")

        panelStart = getChildAt(0)
        panelEnd = getChildAt(1)
        panelCenter = getChildAt(2)

        panelCenter.setOnClickListener(panelCenterOnClickListener)

        panelCenterMaxX = panelStart.x1
        panelCenterMinX = panelStart.x0 - panelEnd.width

        setCenterPanelTouchListener()
        isInited = true
        setCurrentPanel(currentPanel, false)
    }

    override fun setOnClickListener(l: OnClickListener?) = Unit

    fun setCurrentPanel(panel: Panels, animate: Boolean = true) {
        val direction = when (panel) {
            Panels.START -> MoveDirections.RIGHT
            Panels.CENTER -> MoveDirections.CENTER
            Panels.END -> MoveDirections.LEFT
        }
        currentPanel = panel
        if (isInited)
            moveCenterPanel(direction, animate)
    }

    fun setOnCentralPanelClickListener(action: (View) -> Unit) {
        panelCenterOnClickListener = OnClickListener { action.invoke(it) }
    }

    fun setOnPanelChangedListener(onPanelChanged: (Panels) -> Unit) {
        mOnCurrentPanelChangedListener = object : OnCurrentPanelChangedListener {
            override fun onPanelChanged(panel: Panels) = onPanelChanged.invoke(panel)
        }
    }

    fun setOnPanelChangedListener(listener: OnCurrentPanelChangedListener) {
        mOnCurrentPanelChangedListener = listener
    }

    fun setOnMovingListener(
        onStarted: (panelStart: View, panelEnd: View, panelCenter: View) -> Unit = { _, _, _ -> },
        onMoving: (panelStart: View, panelEnd: View, panelCenter: View) -> Unit = { _, _, _ -> },
        onEnded: (panelStart: View, panelEnd: View, panelCenter: View) -> Unit = { _, _, _ -> }
    ) {
        mOnMovingListener = object : OnMovingListener {
            override fun onMovingStarted(panelStart: View, panelEnd: View, panelCenter: View) =
                onStarted.invoke(panelStart, panelEnd, panelCenter)

            override fun onMoving(panelStart: View, panelEnd: View, panelCenter: View) =
                onMoving.invoke(panelStart, panelEnd, panelCenter)

            override fun onMovingEnded(panelStart: View, panelEnd: View, panelCenter: View) =
                onEnded.invoke(panelStart, panelEnd, panelCenter)
        }
    }

    fun setOnMovingListener(listener: OnMovingListener) {
        mOnMovingListener = listener
    }

    private fun setCenterPanelTouchListener() {
        val ACTION_CLICK_TIME = 100L
        var startX = Float.NaN
        var prevX = Float.NaN
        var prevY = Float.NaN
        var times = 0
        var isDragging by Delegates.observable(false) { _, oldValue, newValue ->
            parent.requestDisallowInterceptTouchEvent(newValue)
            mOnMovingListener?.let { mOnMovingListener ->
                if (!oldValue && newValue)
                    mOnMovingListener.onMovingStarted(panelStart, panelEnd, panelCenter)
                if (oldValue && newValue)
                    mOnMovingListener.onMoving(panelStart, panelEnd, panelCenter)
            }
        }

        panelCenter.setOnTouchListener { view, event ->
            return@setOnTouchListener when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    view.animation?.cancel()
                    startX = event.x
                    prevX = startX
                    prevY = event.y
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (event.eventTime < ACTION_CLICK_TIME) return@setOnTouchListener false
                    times = tryCatchDragging(isDragging, prevX, event, prevY, times)
                    isDragging = times > 5
                    if (!isDragging) return@setOnTouchListener false

                    mOnMovingListener
                    val nextX = view.x + event.x - startX
                    view.x = if (nextX <= panelStart.x1 && nextX + view.width >= panelEnd.x0)
                        nextX
                    else
                        if (abs(abs(view.x0) - abs(panelStart.x1)) < abs(abs(view.x1) - abs(panelEnd.x0)))
                            panelStart.x1
                        else
                            panelEnd.x - view.width

                    prevX = event.x
                    prevY = event.y
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (event.eventTime - event.downTime < ACTION_CLICK_TIME)
                        view.performClick()
                    else getMoveDirection(view)?.let { direction ->
                        setCurrentPanel(direction)
                        moveCenterPanel(direction)
                    }
                    isDragging = false
                    startX = Float.NaN
                    times = 0
                    false
                }
                MotionEvent.ACTION_CANCEL -> {
                    isDragging = false
                    startX = Float.NaN
                    times = 0
                    false
                }
                else -> false
            }
        }
    }

    private val View.x0 get() = x
    private val View.x1 get() = x + width
    private val View.xCenter get() = x + width.div(2)

    private fun setCurrentPanel(direction: MoveDirections) {
        currentPanel = when(direction) {
            MoveDirections.LEFT -> Panels.END
            MoveDirections.CENTER -> Panels.CENTER
            MoveDirections.RIGHT -> Panels.START
        }
    }

    private fun getMoveDirection(view: View): MoveDirections? =
        if (view.x0 in panelStart.run { x0..xCenter } || view.x1 in panelEnd.run { xCenter..x1 })
            MoveDirections.CENTER
        else
            if (view.x0 in panelStart.run { xCenter..x1 })
                MoveDirections.RIGHT
            else if (view.x1 in panelEnd.run { x0..xCenter })
                MoveDirections.LEFT
            else
                null

    private fun tryCatchDragging(isDragging: Boolean, prevX: Float, event: MotionEvent, prevY: Float, times: Int): Int {
        var times1 = times
        if (!isDragging)
            if (abs((abs(prevX) - abs(event.x))) >= (abs(abs(prevY - abs(event.y)))))
                times1++
            else
                times1 = 0
        return times1
    }

    private fun moveCenterPanel(direction: MoveDirections, animate: Boolean = true) {
        val toX: Float = when (direction) {
            MoveDirections.LEFT -> panelCenterMinX
            MoveDirections.CENTER -> 0f
            MoveDirections.RIGHT -> panelCenterMaxX
        }
        if (animate) {
            panelCenter.animate()
                .x(toX)
                .setListener(onEnd = {
                    mOnMovingListener?.onMovingEnded(panelStart, panelEnd, panelStart)
                })
                .start()
        } else {
            panelCenter.x = toX
            mOnMovingListener?.onMovingEnded(panelStart, panelEnd, panelStart)
        }
    }

    enum class Panels {
        START, CENTER, END
    }

    enum class MoveDirections {
        LEFT, CENTER, RIGHT
    }

    interface OnCurrentPanelChangedListener {
        fun onPanelChanged(panel: Panels)
    }

    interface OnMovingListener {
        fun onMovingStarted(panelStart: View, panelEnd: View, panelCenter: View)
        fun onMoving(panelStart: View, panelEnd: View, panelCenter: View)
        fun onMovingEnded(panelStart: View, panelEnd: View, panelCenter: View)
    }
}