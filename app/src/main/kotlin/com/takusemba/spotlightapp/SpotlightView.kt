package com.takusemba.spotlightapp

import android.animation.ValueAnimator
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import android.widget.FrameLayout


/**
 * Created by takusemba on 2017/06/18.
 */

class SpotlightView : FrameLayout {

    private var spotPaint: Paint? = null
    private var backgroundPaint: Paint? = null
    private var anim: ValueAnimator? = null
    private var startX: Float? = null
    private var startY: Float? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setWillNotDraw(false)
        setLayerType(View.LAYER_TYPE_HARDWARE, null)

        spotPaint = Paint()
        spotPaint?.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        backgroundPaint = Paint()
        backgroundPaint?.color = ContextCompat.getColor(context, R.color.background)

        val view: View = View(context)
        val lp: LayoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        view.layoutParams = lp
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.background))


        val windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, // アプリケーションのTOPに配置
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // フォーカスを当てない(下の画面の操作がd系なくなるため)
                WindowManager.LayoutParams.FLAG_FULLSCREEN, // OverlapするViewを全画面表示
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSPARENT)
        windowManager.addView(view, params)
    }

    override fun onDraw(canvas: Canvas) {


//        canvas.drawRect(0f, 0f, Display.getDisplaySize(context).width.toFloat(), Display.getDisplaySize(context).height.toFloat(), backgroundPaint)
//        canvas.scale(2f, 2f)


        val radius = anim?.animatedValue as? Float
        if (radius != null && startX != null && startY != null) {
            canvas.drawCircle(startX as Float, startY as Float, radius, spotPaint)
        }
    }

    fun reveal(x: Float, y: Float) {
        startX = x
        startY = y
        anim = ValueAnimator.ofFloat(0f, 100f).apply {
            duration = 1000
            addUpdateListener { invalidate() }
            start()
        }
    }
}