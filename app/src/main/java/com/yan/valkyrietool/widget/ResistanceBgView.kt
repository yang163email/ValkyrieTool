package com.yan.valkyrietool.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yan.valkyrietool.R
import com.yan.valkyrietool.utils.fx
import com.yan.valkyrietool.utils.fy
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

/**
 *  @author : yan
 *  @date   : 2018/6/27 16:59
 *  @desc   : 抗性*星背景view，目前控件的测量没有做，偷个懒
 */
open class ResistanceBgView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    protected val TAG = javaClass.simpleName

    //背景线条paint
    protected val bgLinePaint: Paint
    //文字paint
    protected val textPaint: Paint
    protected val path: Path

    /**
     * 最外圈5个点
     */
    protected val point11 = Point()
    protected val point21 = Point()
    protected val point31 = Point()
    protected val point41 = Point()
    protected val point51 = Point()

    /**
     * 依次往内
     */
    protected val point12 = Point()
    protected val point22 = Point()
    protected val point32 = Point()
    protected val point42 = Point()
    protected val point52 = Point()

    protected val point13 = Point()
    protected val point23 = Point()
    protected val point33 = Point()
    protected val point43 = Point()
    protected val point53 = Point()

    protected val point14 = Point()
    protected val point24 = Point()
    protected val point34 = Point()
    protected val point44 = Point()
    protected val point54 = Point()

    //五边形中心点
    protected val centerPoint = Point()

    //控件实际宽高
    protected var totalWidth: Int = 0
    protected var totalHeight: Int = 0

    //五边形外边长
    protected var largeWidth = 0

    //层级
    protected var level = 4

    //五边形偏移量，根据布局宽度来计算
    protected var pentagonOffset = 0.0

    protected val sin72 = sin(PI * 72 / 180)
    protected val sin36 = sin(PI * 36 / 180)
    protected val cos72 = cos(PI * 72 / 180)
    protected val tan54 = tan(PI * 54 / 180)
    protected val sin54 = sin(PI * 54 / 180)
    protected val cos18 = cos(PI * 18 / 180)
    protected val sin18 = sin(PI * 18 / 180)

    protected val textArray = arrayOf("光", "木", "水", "火", "暗")
    protected var textOffset = dip(4)

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ResistanceView)
        textOffset = ta.getDimensionPixelSize(R.styleable.ResistanceView_rbvTextOffset, dip(4))
        val textSize = ta.getDimensionPixelSize(R.styleable.ResistanceView_rbvTextSize, sp(14))
        val textColor = ta.getColor(R.styleable.ResistanceView_rbvTextColor, Color.BLACK)
        val bgStrokeWidth = ta.getDimensionPixelSize(R.styleable.ResistanceView_rbvBgStrokeWidth, dip(1))
        val bgLineColor = ta.getColor(R.styleable.ResistanceView_rbvBgLineColor, resources.getColor(R.color.c_dddddd))
        ta.recycle()

        bgLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        bgLinePaint.color = bgLineColor
        bgLinePaint.strokeWidth = bgStrokeWidth.toFloat()
        bgLinePaint.style = Paint.Style.STROKE

        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.textSize = textSize.toFloat()
        textPaint.color = textColor

        path = Path()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBg(canvas)
    }

    private fun drawBg(canvas: Canvas) {
        //最外圈
        drawPentagon(canvas, point11, point21, point31, point41, point51)
        drawPentagon(canvas, point12, point22, point32, point42, point52)
        drawPentagon(canvas, point13, point23, point33, point43, point53)
        drawPentagon(canvas, point14, point24, point34, point44, point54)
        drawLines(canvas)
        drawText(canvas, textArray)
    }

    private fun drawLines(canvas: Canvas) {
        drawLine(canvas, point11)
        drawLine(canvas, point21)
        drawLine(canvas, point31)
        drawLine(canvas, point41)
        drawLine(canvas, point51)
    }

    private fun drawLine(canvas: Canvas, startPoint: Point, endPoint2: Point = centerPoint, paint: Paint = bgLinePaint) {
        canvas.drawLine(startPoint.fx, startPoint.fy, endPoint2.fx, endPoint2.fy, paint)
    }

    private fun drawText(canvas: Canvas, textArray: Array<String>) {
        val offset = textOffset
        //光-木-水-火-暗，依次绘制
        val text1 = textArray[0]
        //暂时只计算一个文字的宽高
        val pair = calcText(text1)
        //绘制文字起点
        val x1 = point11.fx - pair.first
        val y1 = (point11.fy + pair.second + offset * sin54).toFloat()

        val text2 = textArray[1]
        val x2 = point21.fx
        val y2 = (point21.fy + pair.second + offset * sin54).toFloat()

        val text3 = textArray[2]
        val x3 = point31.fx + offset*cos18.toFloat()
        val y3 = point31.fy + pair.second/2 - offset*sin18.toFloat()

        val text4 = textArray[3]
        val x4 = point41.fx - pair.first/2
        val y4 = point41.fy - offset

        val text5 = textArray[4]
        val x5 = point51.fx - pair.first - offset*cos18.toFloat()
        val y5 = point51.fy + pair.second/2 - offset*sin18.toFloat()

        canvas.drawText(text1, x1, y1, textPaint)
        canvas.drawText(text2, x2, y2, textPaint)
        canvas.drawText(text3, x3, y3, textPaint)
        canvas.drawText(text4, x4, y4, textPaint)
        canvas.drawText(text5, x5, y5, textPaint)
    }

    private fun calcText(text: String, paint: Paint = textPaint): Pair<Int, Int> {
        val rect = Rect()
        paint.getTextBounds(text, 0, text.length, rect)
        val width = rect.width()
        val height = rect.height()
        return Pair(width, height)
    }

    /**
     * 绘制五边形
     */
    private fun drawPentagon(canvas: Canvas, point1: Point, point2: Point, point3: Point, point4: Point, point5: Point) {
        path.moveTo(point1.fx, point1.fy)
        path.lineTo(point2.fx, point2.fy)
        path.lineTo(point3.fx, point3.fy)
        path.lineTo(point4.fx, point4.fy)
        path.lineTo(point5.fx, point5.fy)
        path.close()
        canvas.drawPath(path, bgLinePaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        totalWidth = w
        totalHeight = h
        largeWidth = totalWidth / 3
        //偏移距离
        pentagonOffset = largeWidth / 2 * tan54 / level

        //以下为计算5个点并记录
        val startX1 = w / 3
        val startY1 = h * 4 / 5
        calc5Point(startX1, startY1, largeWidth, point11, point21, point31, point41, point51)

        val width2 = (largeWidth - pentagonOffset * 2 / tan54).toInt()
        val startX2 = calcStartX(startX1)
        val startY2 = calcStartY(startY1)
        calc5Point(startX2, startY2, width2, point12, point22, point32, point42, point52)

        val width3 = (width2 - pentagonOffset * 2 / tan54).toInt()
        val startX3 = calcStartX(startX2)
        val startY3 = calcStartY(startY2)
        calc5Point(startX3, startY3, width3, point13, point23, point33, point43, point53)

        val width4 = (width3 - pentagonOffset * 2 / tan54).toInt()
        val startX4 = calcStartX(startX3)
        val startY4 = calcStartY(startY3)
        calc5Point(startX4, startY4, width4, point14, point24, point34, point44, point54)

        //计算中心点
        calcCenterPoint(startX1, startY1, largeWidth, centerPoint)
    }

    private fun calcStartX(lastStartX: Int) = (lastStartX + pentagonOffset / tan54).toInt()
    private fun calcStartY(lastStartY: Int) = lastStartY - pentagonOffset.toInt()

    /**
     * 计算5个点的位置
     */
    private fun calc5Point(startX: Int, startY: Int, width: Int, point1: Point, point2: Point, point3: Point, point4: Point, point5: Point) {
        //确认5个点的位置
        //起始点
        point1.set(startX, startY)

        val x2 = startX + width
        val y2 = startY
        point2.set(x2, y2)

        val x3 = x2 + (cos72 * width).toInt()
        val y3 = y2 - (sin72 * width).toInt()
        point3.set(x3, y3)

        val x4 = totalWidth / 2
        val y4 = y3 - (sin36 * width).toInt()
        point4.set(x4, y4)

        val x5 = startX - (cos72 * width).toInt()
        val y5 = y3
        point5.set(x5, y5)
    }

    private fun calcCenterPoint(startX: Int, startY: Int, width: Int, point: Point) {
        val x = startX + width/2
        val y = startY - (width/2*tan54).toInt()
        point.set(x, y)
    }
}