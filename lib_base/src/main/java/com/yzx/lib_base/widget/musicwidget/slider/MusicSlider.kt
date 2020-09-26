package com.yzx.lib_base.widget.musicwidget.slider

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import com.google.android.material.slider.Slider
import com.yzx.lib_base.R
import com.yzx.lib_base.ext.dp

class MusicSlider(context: Context, attributeSet: AttributeSet?) : Slider(context, attributeSet) {

    companion object {
        private const val TAG = "MusicSlider"
    }

    enum class MUSIC_STATE {
        LOADING, SUCCESS
    }

    private val mPaint = Paint()
    private val mTrackPaint = Paint()
    private var widgetWidth = 0
    private var centerY = 0
    private var loadingBitmap: Bitmap? = null
    private var animator: ValueAnimator? = null
    private var bitmapWidth: Int = 0
    private var bitmapHeight: Int = 0
    private var rotateValue: Float = 0F


    var maxColor = Color.parseColor("#33ff0000")
    var cacheColor = Color.parseColor("#66ff0000")
    var progressColor = Color.parseColor("#ffff0000")
    var loadingResource: Int = R.drawable.ccd
    var successCircleRadius: Float = 3f.dp
    var loadingCircleRadius: Float = 10f.dp
    var cache = 90F
    private var state: MUSIC_STATE = MUSIC_STATE.SUCCESS

    private var isOnPressedThumb = false

    init {


        parseAttrSet(context, attributeSet)

        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.RED

        mTrackPaint.style = Paint.Style.STROKE
        mTrackPaint.strokeWidth = trackHeight.toFloat()
        mTrackPaint.strokeCap = Paint.Cap.ROUND
        mTrackPaint.isAntiAlias = true


        val transparent = ColorStateList.valueOf(Color.TRANSPARENT)
        haloTintList = transparent
        trackTintList = transparent
        thumbElevation = 0f
    }

    private fun parseAttrSet(context: Context, attributeSet: AttributeSet?) {
        val typeArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.MusicSlider)

        maxColor = typeArray.getColor(R.styleable.MusicSlider_ms_maxColor, maxColor)
        cacheColor = typeArray.getColor(R.styleable.MusicSlider_ms_cacheColor, cacheColor)
        progressColor = typeArray.getColor(R.styleable.MusicSlider_ms_progressColor, progressColor)
        successCircleRadius =
            typeArray.getDimension(R.styleable.MusicSlider_ms_successCircleRadius, 3f.dp)
        loadingCircleRadius =
            typeArray.getDimension(R.styleable.MusicSlider_ms_loadingCircleRadius, 10f.dp)
        cache = typeArray.getFloat(R.styleable.MusicSlider_ms_cache, 0F)
        loadingResource =
            typeArray.getResourceId(R.styleable.MusicSlider_ms_loading_drawable, R.drawable.ccd)
        typeArray.recycle()
    }

    /**
     * 加载loadingBitmap
     */
    private fun initLoadingBitmap() {
        loadingBitmap = BitmapFactory.decodeResource(resources, loadingResource)
        val bitmapSize = thumbRadius * 2
        loadingBitmap = Bitmap.createScaledBitmap(loadingBitmap!!, bitmapSize, bitmapSize, true)

        bitmapWidth = loadingBitmap!!.width
        bitmapHeight = loadingBitmap!!.height
    }


    override fun onDraw(canvas: Canvas) {

        val sliderPercent = (value - valueFrom) / (valueTo - valueFrom)
        val valueX =
            trackSidePadding + trackWidth * sliderPercent

        val startX = trackSidePadding.toFloat()
        val stopX = startX + trackWidth

        //drawMax
        mTrackPaint.color = maxColor
        canvas.drawLine(startX, centerY.toFloat(), stopX, centerY.toFloat(), mTrackPaint)

        val cachePercent = (cache - valueFrom) / (valueTo - valueFrom)
        val cacheX =
            trackSidePadding + trackWidth * cachePercent
        //drawCache
        mTrackPaint.color = cacheColor
        canvas.drawLine(startX, centerY.toFloat(), cacheX, centerY.toFloat(), mTrackPaint)

//        drawProgress
        mTrackPaint.color = progressColor
        canvas.drawLine(startX, centerY.toFloat(), valueX, centerY.toFloat(), mTrackPaint)

        //reset thumbRadius
        thumbRadius =
            if (isOnPressedThumb || state == MUSIC_STATE.LOADING) loadingCircleRadius.toInt() else successCircleRadius.toInt()

        super.onDraw(canvas)

        //drawLoadingBitmap
        if (state == MUSIC_STATE.LOADING) {
            if (loadingBitmap == null) {
                initLoadingBitmap()
            }
            canvas.rotate(rotateValue, valueX, centerY.toFloat())
            canvas.drawBitmap(
                loadingBitmap!!,
                (valueX - bitmapWidth / 2),
                (centerY - bitmapHeight / 2).toFloat(),
                mPaint
            )
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.getActionMasked()) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> isOnPressedThumb = true

            MotionEvent.ACTION_UP -> isOnPressedThumb = false
            else -> {
            }
        }


        return super.onTouchEvent(event)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widgetWidth = w
        centerY = h / 2
    }


    fun setState(newState: MUSIC_STATE) {
        if (newState==state){
            return
        }
        state = newState
        if (newState == MUSIC_STATE.LOADING) {
            startAnim()
        } else {
            stopAnim()
        }
        invalidate()
    }

    private fun initAnim() {
        animator = ValueAnimator.ofFloat(0F, 360F)
        animator!!.apply {
            duration = 1000
            interpolator = LinearInterpolator()
            repeatCount = -1
            addUpdateListener {
                rotateValue = it.animatedValue as Float
                invalidate()
            }
        }
    }

    private fun startAnim() {

        if (animator == null) {
            initAnim()
        }
        if (!animator!!.isStarted) {
            animator!!.start()
        }

        if (animator!!.isPaused) {
            animator!!.resume()
        }
    }


    private fun stopAnim() {
        if (animator != null && animator!!.isRunning) {
            animator?.pause()
        }
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (animator != null) {
            animator?.cancel()
        }
    }
}