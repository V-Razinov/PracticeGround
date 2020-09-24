package ru.practiceground.other.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

class ExpandableHeightGridView : GridView {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet, style: Int) : super(ctx, attrs, style)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
            MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
        layoutParams.height = measuredHeight
    }
}