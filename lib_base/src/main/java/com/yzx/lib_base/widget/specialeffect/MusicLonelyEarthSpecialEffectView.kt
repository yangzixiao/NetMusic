package com.yzx.lib_base.widget.specialeffect

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


/**
 * 播放页面音乐特效
 */
class MusicLonelyEarthSpecialEffectView(
    context: Context,
    attributeSet: AttributeSet?,
) :
    View(context, attributeSet) {

    private companion object {
        const val TAG = "yzx"
    }

    private var centerX = 0F
    private var centerY = 0F
    private var maxRadius = 0F

    private var mPaint: Paint = Paint()
    private val animator = ValueAnimator.ofFloat(0F, 1F)

    private val circles = mutableListOf<LonelyEarthCircleBean>()
    private val random = Random()
    private val pathMeasure = PathMeasure()
    private var positionArray = floatArrayOf(0f, 0f)
    private var instance = 50
    private var canvasRotate = 0

    init {
        Log.e(TAG, "init: ")
        initPaint()
    }

    private fun initPaint() {
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.WHITE
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = 2f

        animator.duration = 5000
        animator.repeatCount = -1

        animator.addUpdateListener {
            circles.forEachIndexed { index, lonelyEarthCircleBean ->
                val radius = lonelyEarthCircleBean.startRadius
                if (radius > maxRadius) {
                    lonelyEarthCircleBean.startRadius = 0F
                }

                lonelyEarthCircleBean.startRadius += 2
                if (lonelyEarthCircleBean.maxRadius != 0F) {
                    lonelyEarthCircleBean.apply {
                        val i = ((maxRadius - startRadius) / maxRadius * 255).toInt() + 50
                        alpha = if (i > 255) 255 else i
                    }
                }
            }
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val measuredWidth = measuredWidth
        setMeasuredDimension(measuredWidth, measuredWidth)

        centerX = (measuredWidth / 2).toFloat()
        centerY = centerX
        maxRadius = centerX

        Log.e(TAG, "onMeasure: ")
    }


    private fun providerCircles() {
        for (index in 0..10) {
            circles.add(providerCircleBean(index))
        }
    }

    private fun providerCircleBean(index: Int): LonelyEarthCircleBean {
        val lonelyEarthCircleBean = LonelyEarthCircleBean()
        lonelyEarthCircleBean.startRadius = -index * 50F
        lonelyEarthCircleBean.maxRadius = maxRadius
        lonelyEarthCircleBean.startAngel = random.nextInt(360)
        lonelyEarthCircleBean.littleCircleRadius = random.nextInt(10) + 3
        return lonelyEarthCircleBean
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvasRotate += 1
        canvas.rotate(canvasRotate.toFloat(), centerX, centerY)

        if (!circles.isNullOrEmpty()) {
            circles.forEach {
                if (it.startRadius > 0) {
                    mPaint.alpha = it.alpha
                    mPaint.style = Paint.Style.STROKE
                    canvas.drawCircle(centerX, centerY, it.startRadius, mPaint)
                    mPaint.style = Paint.Style.FILL

                    val radian = Math.toRadians(it.startAngel.toDouble())
                    val x = centerX + cos(radian) * it.startRadius
                    val y = centerY + sin(radian) * it.startRadius
                    canvas.drawCircle(
                        x.toFloat(), y.toFloat(),
                        it.littleCircleRadius.toFloat(),
                        mPaint
                    )
                }
            }
        }
    }

    fun setStartAngle() {

    }

    fun setWaveColor(color: Int) {
        mPaint.color = color
    }

    fun start() {
        providerCircles()
        animator.start()
    }

    fun pause() {
        if (animator.isRunning) {
            animator.pause()
        }
    }

    fun resume() {
        if (circles.isNullOrEmpty()) {
            providerCircles()
        }
        if (animator.isPaused) {
            animator.resume()
        }
    }
}