package com.nullpt.sockettest

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GestureDetectorCompat
import com.nullpt.sockets.log

class MyParentView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), GestureDetector.OnGestureListener {

    private val gestureDetector = GestureDetectorCompat(context, this)
    private var inProgress = false

    private var scrollView: View? = null
    private var clickView: View? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (childCount > 0) {
            scrollView = getChildAt(0)
        }
        if (childCount > 1) {
            clickView = getChildAt(1)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        //super.dispatchTouchEvent(ev)

        if (inProgress) {
            log { "1" }
            scrollView?.dispatchTouchEvent(ev)
        } else {
            log { "2" }
            clickView?.dispatchTouchEvent(ev)
        }
        return gestureDetector.onTouchEvent(ev)
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        inProgress = false
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        inProgress = false
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        inProgress = true
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        inProgress = true
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        inProgress = false
    }


}