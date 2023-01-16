package com.example.graphapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class LineGraph: View{
    constructor(context: Context): super(context)
    constructor(context: Context, attributes: AttributeSet): super(context, attributes)
    constructor(context: Context, attributes: AttributeSet, defStyleAttribute: Int): super(context, attributes, defStyleAttribute)
    constructor(context: Context, attributes: AttributeSet, defStyleAttribute: Int, defStyleResource: Int): super(context, attributes, defStyleAttribute, defStyleResource)

    private val path: Path = Path()
    private val paint: Paint = Paint()
    private val xAxisLabels: MutableList<String> = mutableListOf()
    private val yAxisLabels: MutableList<String> = mutableListOf()

    private val canvasXOrigin: Float = 0.0f
    private val canvasYOrigin: Float = 0.0f
    private val graphHeight: Float = resources.getDimension(R.dimen.y_axis_length).toFloat()
    private val graphWidth: Float = resources.getDimension(R.dimen.graph_padding)
    private val graphPadding: Float = resources.getDimension(R.dimen.graph_padding).toFloat()
    private val graphXOrigin: Float = resources.getDimension(R.dimen.horizontal_padding_graph).toFloat()
    private val graphYOrigin: Float = resources.getDimension(R.dimen.y_axis_length).toFloat()
    private var areXAxisLabelsSet: Boolean = false
    private var areYAxisLabelsSet: Boolean = false

    init {
        paint.color = Color.BLACK
        paint.strokeWidth = resources.getDimension(R.dimen.graph_line_width).toFloat()
        paint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawAxes(canvas, paint)
        if(areYAxisLabelsSet)
            drawYAxisLabel(canvas, paint);
    }

    private fun drawYAxisLabel(canvas: Canvas?, paint: Paint) {
        val countLabels: Int = yAxisLabels.size

    }

    private fun drawAxes(canvas: Canvas?, paint: Paint) {
        paint.color = resources.getColor(R.color.axes_color)
        paint.strokeWidth = resources.getDimension(R.dimen.graph_line_thickness)
        canvas?.drawLine(graphXOrigin, graphYOrigin, canvas.width.toFloat()-graphPadding, graphYOrigin, paint)
        canvas?.drawLine(graphXOrigin, canvasYOrigin, graphXOrigin, graphHeight, paint)
    }

    fun setYAxisLabel(list: List<String>) {
        yAxisLabels.addAll(0, list)
        areYAxisLabelsSet = true;
    }
}