package com.example.graphapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myGraph: LineGraph = findViewById(R.id.myGraph)
        val yList: List<String> = listOf("10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "%")
        val xList: List<String> = listOf("9:33 pm", "9:35pm", "9:37pm")
        myGraph.setYAxisLabel(yList)
        myGraph.setXAxisLabel(xList)

        var data: MutableList<Pair<Float, Float>> = mutableListOf()
        val random: Random = Random()

        for(i in 1..50){
            val p: Pair<Float, Float> = Pair(i.toFloat(), random.nextFloat()*100)
            data.add(p)
        }

        myGraph.setGraphData(data)
    }
}