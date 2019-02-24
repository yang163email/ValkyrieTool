package com.yan.valkyrietool.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import com.yan.valkyrietool.R
import com.yan.valkyrietool.utils.CheckUtils
import com.yan.valkyrietool.utils.fx
import com.yan.valkyrietool.utils.fy
import org.jetbrains.anko.dip

/**
 *  @author : yan
 *  @date   : 2018/6/28 10:37
 *  @desc   : 抗性图
 */
class ResistanceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ResistanceBgView(context, attrs, defStyleAttr) {

    //前景线条paint
    private val fgLinePaint: Paint
    private val fgShapePaint: Paint

    /**
     * 抗性
     */
    private var fireResistance: Float = 0f
    private var waterResistance: Float = 0f
    private var woodResistance: Float = 0f
    private var lightResistance: Float = 0f
    private var shadowResistance: Float = 0f

    private var fgPoint1: Point = Point()
    private var fgPoint2: Point = Point()
    private var fgPoint3: Point = Point()
    private var fgPoint4: Point = Point()
    private var fgPoint5: Point = Point()

    private val fgPath = Path()

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ResistanceView)
        val fgStrokeWidth = ta.getDimensionPixelSize(R.styleable.ResistanceView_rvFgStrokeWidth, dip(2))
        val fgLineColor = ta.getColor(R.styleable.ResistanceView_rvFgLineColor, resources.getColor(R.color.c_f51919))
        val fgFillColor = ta.getColor(R.styleable.ResistanceView_rvFillColor, resources.getColor(R.color.c_50f51919))
        ta.recycle()

        fgLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        fgLinePaint.style = Paint.Style.STROKE
        fgLinePaint.color = fgLineColor
        fgLinePaint.strokeWidth = fgStrokeWidth.toFloat()

        fgShapePaint = Paint()
        fgShapePaint.color = fgFillColor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFgStrokeShape(canvas, fgPath, fgLinePaint)
        drawFgStrokeShape(canvas, fgPath, fgShapePaint)
    }

    private fun drawFgStrokeShape(canvas: Canvas, path: Path, fgLinePaint: Paint) {
        calcPosition(lightResistance, woodResistance, waterResistance, fireResistance, shadowResistance)
        path.apply {
            moveTo(fgPoint1.fx, fgPoint1.fy)
            lineTo(fgPoint2.fx, fgPoint2.fy)
            lineTo(fgPoint3.fx, fgPoint3.fy)
            lineTo(fgPoint4.fx, fgPoint4.fy)
            lineTo(fgPoint5.fx, fgPoint5.fy)
            close()
        }
        canvas.drawPath(path, fgLinePaint)
    }

    private fun calcPosition(lightResistance: Float, woodResistance: Float,
                             waterResistance: Float, fireResistance: Float,
                             shadowResistance: Float) {
        val centerX = centerPoint.x
        val centerY = centerPoint.y
        //光
        //如：3.0/4
        val f1 = lightResistance / level
        val x1 = centerX - (centerX - point11.x) * f1
        val y1 = centerY + (-centerY + point11.y) * f1
        fgPoint1.set(x1.toInt(), y1.toInt())

        //木
        val f2 = woodResistance / level
        val x2 = centerX + (-centerX + point21.x) * f2
        val y2 = centerY + (-centerY + point21.y) * f2
        fgPoint2.set(x2.toInt(), y2.toInt())

        //水
        val f3 = waterResistance / level
        val x3 = centerX + (-centerX + point31.x) * f3
        val y3 = centerY - (centerY - point31.y) * f3
        fgPoint3.set(x3.toInt(), y3.toInt())

        //火
        val f4 = fireResistance / level
        val x4 = centerX + (centerX - point41.x) * f4
        val y4 = centerY - (centerY - point41.y) * f4
        fgPoint4.set(x4.toInt(), y4.toInt())

        //暗
        val f5 = shadowResistance / level
        val x5 = centerX - (centerX - point51.x) * f5
        val y5 = centerY - (centerY - point51.y) * f5
        fgPoint5.set(x5.toInt(), y5.toInt())
    }

    /**
     * 传值,如：1.0_2.0_3.0_4.0_0.0
     */
    fun setData(resistanceData: String) {
        val split = resistanceData.split("_")
        if (split.size != 5) {
            throw IllegalStateException("传值格式不正确")
        }
        val flag = split.any {
            it.toFloat() > 4
        }
        if (flag) {
            throw IllegalStateException("不能超过4")
        }

        fireResistance = split[0].toFloat()
        waterResistance = split[1].toFloat()
        woodResistance = split[2].toFloat()
        lightResistance = split[3].toFloat()
        shadowResistance = split[4].toFloat()

        invalidate()
    }

    /**
     * 传值
     */
    fun setData(fireResistance: Float, waterResistance: Float,
                woodResistance: Float, lightResistance: Float, shadowResistance: Float) {

        CheckUtils.checkData(4f, "不能超过4", fireResistance, waterResistance, woodResistance, lightResistance, shadowResistance)

        this.fireResistance = fireResistance
        this.waterResistance = waterResistance
        this.woodResistance = woodResistance
        this.lightResistance = lightResistance
        this.shadowResistance = shadowResistance

        invalidate()
    }

}