package ru.practiceground.other.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import androidx.constraintlayout.widget.ConstraintLayout
import ru.practiceground.other.extensions.setListener
import kotlin.math.abs
import kotlin.properties.Delegates

class DragToShowActionsLayout : ConstraintLayout {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, style: Int) : super(ctx, attrs, style)

    enum class Panels { START, CENTER, END }
    enum class MoveDirections { LEFT, CENTER, RIGHT }

    private var ACTION_CLICK_TIME = 150L

    private lateinit var panelStart: View
    private lateinit var panelCenter: View
    private lateinit var panelEnd: View
    private var currentPanel: Panels by Delegates.observable(Panels.CENTER) { _, oldValue, newValue ->
        if (oldValue != newValue) mOnCurrentPanelChangedListener?.onPanelChanged(newValue)
    }

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
        setCurrentPanel(Panels.CENTER, false)
    }

    override fun setOnClickListener(l: OnClickListener?) = Unit

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

    fun setActionClickTime(time: Long) {
        ACTION_CLICK_TIME = time
    }

    private fun setCurrentPanel(panel: Panels, animate: Boolean = true) {
        val direction = when (panel) {
            Panels.START -> MoveDirections.RIGHT
            Panels.CENTER -> MoveDirections.CENTER
            Panels.END -> MoveDirections.LEFT
        }
        currentPanel = panel
        if (isInited)
            moveCenterPanel(direction, animate)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setCenterPanelTouchListener() {
        var startX = Float.NaN
        var prevX = Float.NaN
        var prevY = Float.NaN
        var times = 0
        var isDragging by Delegates.observable(false) { _, oldValue, newValue ->
            parent.requestDisallowInterceptTouchEvent(newValue)
            mOnMovingListener?.let { mOnMovingListener ->
                when {
                    !oldValue && newValue -> mOnMovingListener.onMovingStarted(panelStart, panelEnd, panelCenter)
                    oldValue && newValue -> mOnMovingListener.onMoving(panelStart, panelEnd, panelCenter)
                }
            }
        }

        panelCenter.setOnTouchListener { view, event ->
            return@setOnTouchListener when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    view.animation?.cancel()
                    prevX = startX
                    prevY = event.y
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (event.eventTime - event.downTime < ACTION_CLICK_TIME) {
                        prevX = event.x
                        prevY = event.y
                        return@setOnTouchListener false
                    }

                    times = tryCatchDragging(isDragging, prevX, event, prevY, times)
                    isDragging = times > 5
                    if (!isDragging) return@setOnTouchListener false

                    if (startX.isNaN()) startX = event.x

                    val nextX = view.x + event.x - startX
                    view.x = when {
                        nextX <= panelStart.x1 && nextX + view.width >= panelEnd.x0 -> nextX
                        nextX > panelStart.x1 -> panelStart.x1
                        nextX + view.width < panelEnd.x0 -> panelStart.x0 - panelEnd.width
                        else -> nextX
                    }
                    prevX = event.x
                    prevY = event.y
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (event.eventTime - event.downTime < ACTION_CLICK_TIME) {
                        view.isPressed = true
                    } else {
                        getMoveDirection(view)?.let { direction ->
                            setCurrentPanel(direction)
                            moveCenterPanel(direction)
                        }
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

    private fun getMoveDirection(view: View): MoveDirections? {
        if (view.x0 == 0f) return MoveDirections.CENTER
        return if (view.x0 in panelStart.run { x0..xCenter } || view.x1 in panelEnd.run { xCenter..x1 })
            MoveDirections.CENTER
        else
            if (view.x0 in panelStart.run { xCenter..x1 })
                MoveDirections.RIGHT
            else if (view.x1 in panelEnd.run { x0..xCenter })
                MoveDirections.LEFT
            else
                null
    }

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

    interface OnCurrentPanelChangedListener {
        fun onPanelChanged(panel: Panels)
    }

    interface OnMovingListener {
        fun onMovingStarted(panelStart: View, panelEnd: View, panelCenter: View)
        fun onMoving(panelStart: View, panelEnd: View, panelCenter: View)
        fun onMovingEnded(panelStart: View, panelEnd: View, panelCenter: View)
    }
}