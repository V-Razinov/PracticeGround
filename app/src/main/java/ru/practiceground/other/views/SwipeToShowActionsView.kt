package ru.practiceground.other.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.item_swipe_to_show_action.view.*
import ru.practiceground.R
import kotlin.math.abs

class SwipeToShowActionsView : ConstraintLayout {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, style: Int) : super(ctx, attrs, style)

    var currentPanel = Panels.CENTER

    private lateinit var panelStart: ViewGroup
    private lateinit var panelCenter: ViewGroup
    private lateinit var panelEnd: ViewGroup

    //Процент занимаемой ширины
    private var panelStartWidth = 20
    private var panelEndWidth = 20

    init {
        inflate(context, R.layout.item_swipe_to_show_action, this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        panelStart = panel_start
        panelCenter = panel_center
        panelEnd = panel_end
        panelStart.post {
        }
        panelEnd.post {
        }
        post {
            var lp = panelStart.layoutParams
            lp.width = panelCenter.measuredWidth.div(100).times(panelStartWidth)
            panelStart.layoutParams = lp

            lp = panelEnd.layoutParams
            lp.width = panelCenter.measuredWidth.div(100).times(panelStartWidth)
            panelEnd.layoutParams = lp
        }
        setCenterPanelTouchListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setCenterPanelTouchListener() {
        var startPosition = Float.NaN
        var prevX = Float.NaN
        var prevY = Float.NaN
        var times = 0
        var isDragging = false
        panelCenter.setOnTouchListener { view, event ->

            Log.d("MyLogs", "event.x: ${event?.x.toString()}")
            Log.d("MyLogs", "view.x: ${view?.x.toString()}\n---------------\n")
            Log.d("MyLogs", "panelEnd.x: ${panelEnd?.x.toString()}\n---------------\n")
            return@setOnTouchListener when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPosition = event.x
                    prevX = startPosition
                    prevY = event.y
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    times = shouldStartDragging(isDragging, prevX, event, prevY, times)

                    isDragging = times > 5

                    parent.requestDisallowInterceptTouchEvent(isDragging)

                    if (!isDragging) return@setOnTouchListener false

                    val nextX = view.x + event.x - startPosition
                    if (nextX <= panelStart.run { x + width } && nextX + view.width >= panelEnd.x)
                        view.x = nextX
                    else
                        view.x = if (abs(abs(view.x) - abs(panelStart.run { x + width })) < abs(abs(view.x + view.width) - abs(panelEnd.x))) {
                            panelStart.run { x + width }
                        } else {
                            panelEnd.x - view.width
                        }
                    prevX = event.x
                    prevY = event.y
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    parent.requestDisallowInterceptTouchEvent(false)
                    isDragging = false
                    startPosition = Float.NaN
                    times = 0
                    false
                }
                else -> false
            }
        }
    }

    private fun shouldStartDragging(isDragging: Boolean, prevX: Float, event: MotionEvent, prevY: Float, times: Int): Int {
        var times1 = times
        if (!isDragging)
            if (abs((abs(prevX) - abs(event.x))) >= (abs(abs(prevY - abs(event.y)))))
                times1++
            else
                times1 = 0
        return times1
    }

    enum class Panels {
        START, CENTER, END
    }
}