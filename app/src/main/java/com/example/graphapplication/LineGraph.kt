package com.example.graphapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


class LineGraph: View{
    constructor(context: Context): super(context)
    constructor(context: Context, attributes: AttributeSet): super(context, attributes)
    constructor(context: Context, attributes: AttributeSet, defStyleAttribute: Int): super(context, attributes, defStyleAttribute)
    constructor(context: Context, attributes: AttributeSet, defStyleAttribute: Int, defStyleResource: Int): super(context, attributes, defStyleAttribute, defStyleResource)

    private val path: Path = Path()
    private val paint: Paint = Paint()
    private val xAxisLabels: MutableList<String> = mutableListOf()
    private val yAxisLabels: MutableList<String> = mutableListOf()
    private lateinit var lineGraphData: LineGraphData

    private val canvasXOrigin: Float = 0.0f
    private val canvasYOrigin: Float = 0.0f
    private var canvasWidth: Float = 0f
    private var canvasHeight: Float = 0f
    private val graphHeight: Float = resources.getDimension(R.dimen.y_axis_length).toFloat()
    private var graphWidth: Float = 0f
    private val yAxisLabelRightMargin = resources.getDimension(R.dimen.y_axis_label_right_margin).toFloat()
    private val xAxisLabelTopMargin = resources.getDimension(R.dimen.x_axis_label_top_margin).toFloat()
    private val graphPadding: Float = resources.getDimension(R.dimen.graph_padding).toFloat()
    private val graphXOrigin: Float = resources.getDimension(R.dimen.horizontal_padding_graph).toFloat()
    private val graphYOrigin: Float = resources.getDimension(R.dimen.y_axis_length).toFloat()
    private var areXAxisLabelsSet: Boolean = false
    private var areYAxisLabelsSet: Boolean = false
    private var areDataPointsSet: Boolean = false
    private var isPathSet: Boolean = false

    init {
        paint.color = Color.BLACK
        paint.strokeWidth = resources.getDimension(R.dimen.graph_line_width).toFloat()
        paint.style = Paint.Style.STROKE
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawAxes(canvas, paint)
        if(areYAxisLabelsSet)
            drawYAxisLabel(canvas, paint)
        if(areXAxisLabelsSet)
            drawXAxisLabel(canvas, paint)
        if(areDataPointsSet)
            plotGraphData(canvas, paint, path)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        canvasWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        canvasHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()
    }

    private fun drawYAxisLabel(canvas: Canvas?, paint: Paint) {
        val bounds: Rect = Rect()
        val countLabels: Int = yAxisLabels.size + 1
        val yUnit: Float = graphHeight / countLabels
        var yPosition = graphYOrigin - yUnit

        paint.color = resources.getColor(R.color.axes_labels)
        paint.strokeWidth = 0.4f
        paint.textAlign = Paint.Align.RIGHT

        for(string in yAxisLabels) {
            paint.getTextBounds(string, 0, string.length, bounds)
            canvas?.drawText(string, 0, string.length, graphPadding-bounds.left-yAxisLabelRightMargin, yPosition, paint)
            yPosition -= yUnit
        }
    }

    private fun drawXAxisLabel(canvas: Canvas?, paint: Paint) {
        val bounds: Rect = Rect()
        val countLabels: Int = xAxisLabels.size - 1
        val xUnit: Float = (graphWidth) / countLabels
        var xPosition = graphPadding

        paint.color = resources.getColor(R.color.axes_labels)
        paint.strokeWidth = 0.5f

        for(string in xAxisLabels) {
            paint.getTextBounds(string, 0, string.length, bounds)
            canvas?.drawText(string, 0, string.length, xPosition, graphYOrigin + xAxisLabelTopMargin, paint)
            xPosition += xUnit
        }
    }

    private fun drawAxes(canvas: Canvas?, paint: Paint) {
        paint.color = resources.getColor(R.color.axes_color)
        paint.strokeWidth = resources.getDimension(R.dimen.graph_line_thickness)
        graphWidth = canvasWidth - 2*graphPadding
        canvas?.drawLine(graphXOrigin, graphYOrigin, graphXOrigin + graphWidth, graphYOrigin, paint)
        canvas?.drawLine(graphXOrigin, canvasYOrigin, graphXOrigin, graphHeight, paint)
    }

    fun setYAxisLabel(list: List<String>) {
        yAxisLabels.addAll(0, list)
        areYAxisLabelsSet = true
    }

    fun setGraphData(data: List<Pair<Float, Float>>) {
        lineGraphData = LineGraphData(data)
        areDataPointsSet = true
    }

    private fun plotGraphData(canvas: Canvas?, paint: Paint, path: Path) {
        path.reset()

        paint.color = Color.RED
        paint.strokeWidth = 4f

        val adjustedLineGraphData: MutableList<Pair<Float, Float>> = mutableListOf()

        for(data in lineGraphData.data) {
            val xPosition: Float = lineGraphData.normalizeTime(graphWidth, data.first) + graphXOrigin
            val yPosition: Float = graphYOrigin - (data.second * (graphHeight / 100))
            Log.d("Points", "$xPosition, $yPosition")
            canvas?.drawCircle(xPosition, yPosition, 4f, paint)
            adjustedLineGraphData.add(Pair(xPosition, yPosition))
        }

        val countPoints: Int = adjustedLineGraphData.size
        var i: Int = 1

        path.moveTo(adjustedLineGraphData[i-1].first, adjustedLineGraphData[i-1].second)

        while(i < countPoints) {
            val controlX = (adjustedLineGraphData[i-1].first + adjustedLineGraphData[i].first) / 2
            val controlY = adjustedLineGraphData[i-1].second
            val pointY = adjustedLineGraphData[i].second

            path.cubicTo(controlX, controlY, controlX, pointY, adjustedLineGraphData[i].first, adjustedLineGraphData[i].second)
            i++
        }

        paint.color = Color.BLUE
        canvas?.drawPath(path, paint)
    }

    fun setXAxisLabel(list: List<String>) {
        xAxisLabels.addAll(0, list)
        areXAxisLabelsSet = true
    }
}