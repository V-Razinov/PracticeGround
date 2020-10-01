package ru.practiceground.other.customviews

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import ru.practiceground.R

class SingleViewTouchableMotionLayout: MotionLayout {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, style: Int) : super(ctx, attrs, style)

    private val viewToDetectTouch by lazy { findViewById<View>(R.id.top_image_container) }
    private val viewRect = Rect()
    private var touchStarted = false
    private val transitionListenerList = mutableListOf<TransitionListener>()

    init {
        addTransitionListener1(object : TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float)  = Unit
            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) = Unit
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int)  = Unit

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                touchStarted = false
            }
        })

        super.setTransitionListener(object : TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float)  = Unit
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int)  = Unit

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                transitionListenerList.forEach { it.onTransitionChange(p0, p1, p2, p3) }
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                transitionListenerList.forEach { it.onTransitionCompleted(p0, p1) }
            }
        })
    }

    override fun setTransitionListener(listener: TransitionListener) {
        addTransitionListener(listener)
    }

    fun addTransitionListener1(listener: TransitionListener) {
        transitionListenerList += listener
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                touchStarted = false
                return super.onTouchEvent(event)
            }
        }
        if (!touchStarted) {
            viewToDetectTouch.getHitRect(viewRect)
            touchStarted = viewRect.contains(event.x.toInt(), event.y.toInt())
        }
        return touchStarted && super.onTouchEvent(event)
    }
}