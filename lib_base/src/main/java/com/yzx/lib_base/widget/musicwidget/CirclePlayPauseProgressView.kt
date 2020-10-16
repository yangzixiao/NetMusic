package com.yzx.lib_base.widget.musicwidget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.yzx.lib_base.R
import com.yzx.lib_core.ext.dp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CirclePlayPauseProgressView(context: Context, attributeSet: AttributeSet?) :
    View(context, attributeSet) {

    private var max = 100f
    private var progress = 0f
    private var maxColor = 0x000000
    private var progressColor = 0xff0000
    private var playColor = 0xff0000
    private var pauseColor = 0x000000
    private var circleX = 0f
    private var circleY = 0f
    private var radius = 0f
    private var mPaint: Paint = Paint()
    private val playPausePath = Path()

    private var isPlaying = false

    private val circleWidth = 1f.dp

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = circleWidth
        parseAttrSet(context, attributeSet)
    }

    private fun parseAttrSet(context: Context, attributeSet: AttributeSet?) {
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attributeSet, R.styleable.CirclePlayPauseProgressView)
        obtainStyledAttributes.apply {
            max = getFloat(R.styleable.CirclePlayPauseProgressView_cppp_max, 100f)
            progress = getFloat(R.styleable.CirclePlayPauseProgressView_cppp_progress, 30f)
            maxColor = getColor(R.styleable.CirclePlayPauseProgressView_cppp_max_color, Color.BLACK)
            progressColor = getColor(R.styleable.CirclePlayPauseProgressView_cppp_progress_color, Color.RED)
            playColor = getColor(R.styleable.CirclePlayPauseProgressView_cppp_play_color, Color.RED)
            pauseColor = getColor(R.styleable.CirclePlayPauseProgressView_cppp_pause_color, Color.BLACK)
            recycle()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //        drawPlayPauseIcon
        playPausePath.reset()
        if (isPlaying) {
            mPaint.color = playColor
            val (x, y) = getXYByAngel(60.0, radius / 2)
            val (x1, y1) = getXYByAngel(300.0, radius / 2)
            playPausePath.moveTo(x, y)
            playPausePath.lineTo(x1, y1)
            canvas.drawPath(playPausePath, mPaint)

            playPausePath.reset()
            val (x2, y2) = getXYByAngel(240.0, radius / 2)
            val (x3, y3) = getXYByAngel(120.0, radius / 2)
            playPausePath.moveTo(x2, y2)
            playPausePath.lineTo(x3, y3)
            canvas.drawPath(playPausePath, mPaint)
        } else {
            mPaint.color = pauseColor
            val (x, y) = getXYByAngel(0.0, radius / 2)
            val (x1, y1) = getXYByAngel(120.0, radius / 2)
            val (x2, y2) = getXYByAngel(240.0, radius / 2)
            playPausePath.moveTo(x, y)
            playPausePath.lineTo(x1, y1)
            playPausePath.lineTo(x2, y2)
            playPausePath.close()
            canvas.drawPath(playPausePath, mPaint)
        }

        mPaint.style = Paint.Style.STROKE
        mPaint.color = maxColor
        canvas.drawCircle(circleX, circleY, radius, mPaint)

        mPaint.color = progressColor
        canvas.rotate(-90f, circleX, circleY)
        val sweepAngle = 360f * (progress / max)
        canvas.drawArc(circleX - radius, circleY - radius, circleX + radius, circleY + radius, 0f, sweepAngle, false, mPaint)
    }

    private fun getXYByAngel(angle: Double, radius: Float): Pair<Float, Float> {
        val radian = Math.toRadians(angle)
        val x = circleX + cos(radian) * radius
        val y = circleY + sin(radian) * radius
        return Pair(x.toFloat(), y.toFloat())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        circleX = (w / 2).toFloat()
        circleY = (h / 2).toFloat()
        radius = min(circleX, circleY) * 0.6f

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(heightSize, heightSize)
    }

    fun setMaxAndProgress(newMax: Float, newProgress: Float) {
        if (newMax > 0 && newMax >= newProgress) {
            max = newMax
            progress = newProgress
            invalidate()
        }
    }

    fun setPlayingState(newPlayingState: Boolean) {
        if (newPlayingState == isPlaying) {
            return
        }
        isPlaying = newPlayingState
        invalidate()
    }
}