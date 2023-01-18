package com.example.graphapplication

import java.lang.Float.max
import java.lang.Float.min

class LineGraphData(list: List<Pair<Float, Float>>) {
    var data: MutableList<Pair<Float, Float>> = mutableListOf()
    var minTime: Float = Float.MAX_VALUE
    var maxTime: Float = Float.MIN_VALUE
    var timeDifference: Float = 0f

    init {
        data.addAll(0, list)
        for(value in data) {
            minTime = min(minTime, value.first)
            maxTime = max(maxTime, value.second)
        }
        timeDifference = (maxTime-minTime)
    }

    fun normalizeTime(graphWidth: Float, time: Float): Float {
        return (graphWidth / timeDifference)*(time - minTime)
    }
}