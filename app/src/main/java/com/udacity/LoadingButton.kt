package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    //private lateinit var TextOfbutton="Download"
    private var value=0.0f
    private var width=0.0f
    private var sweepAngle=0.0f //attribute for circle
    private var TextofButton="Download" //default Text of button is download
    private var static_width_size_left:Int=145
    private var static_height_size_top:Int=40
    private var static_width_size_right:Int=80
    private var static_height_size_bottom:Int=40
    private var static_start_angle:Int=0
    //paint of default of button
    private var paintoFButton=Paint(Paint.ANTI_ALIAS_FLAG).apply{
        color=context.getColor(R.color.colorPrimary)
    }

    //paint of LoadingButton
    private var paintoFLoadingButton=Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color=context.getColor(R.color.colorPrimaryDark)
    }

    //paint of Button Text
    private val paintoFText=Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize=resources.getDimension(R.dimen.default_text_size)
        textAlign=Paint.Align.CENTER
        color=Color.WHITE
    }

    //paint of Circle
    private var paintCircle=Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color=context.getColor(R.color.colorAccent)
    }
    private var valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        // check button state
        TextofButton = context.getString(buttonState.TextOnbutton)
        if (buttonState == ButtonState.Clicked) {
            paintCircle.color = context.getColor(R.color.colorAccent)
            invalidate()
        } else if (buttonState == ButtonState.Completed) {
            valueAnimator.cancel()
            paintCircle.color = context.getColor(R.color.colorPrimary)
            value = 0f
            invalidate()
        } else if (buttonState == ButtonState.Loading) {
            valueAnimator =
                ValueAnimator.ofFloat(0.0f, measuredWidth.toFloat()).setDuration(5000).apply {
                    addUpdateListener { valueAnimator ->
                        value = valueAnimator.animatedValue as Float
                        sweepAngle = value / 16
                        width = value * 8
                        invalidate()
                    }
                }
            valueAnimator.start()
        }
    }


    init {
       buttonState = ButtonState.Clicked


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //---> 0f instead of 10f
        canvas?.drawRect(0f,0f,widthSize.toFloat(),heightSize.toFloat(),paintoFButton)
        canvas?.drawRect(0f,0f,width,heightSize.toFloat(),paintoFLoadingButton)

        val HeightofText:Float=paintoFText.descent()-paintoFText.ascent()
        canvas?.drawText(
            TextofButton,
            (widthSize/2).toFloat(),
            HeightofText,
            paintoFText
        )
       canvas?.drawArc(widthSize-static_width_size_left.toFloat(),
           heightSize/2-static_height_size_top.toFloat(),
            widthSize-static_width_size_right.toFloat(),
           heightSize/2+static_height_size_bottom.toFloat(),
            static_start_angle.toFloat(),
            width,
            true,
            paintCircle
            )

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }
 fun setState(state:ButtonState){
     buttonState=state
 }
}