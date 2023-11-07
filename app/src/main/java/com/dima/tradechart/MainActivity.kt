package com.dima.tradechart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dima.tradechart.databinding.ActivityMainBinding
import com.text.traderchart.chart.model.data
import com.text.traderchart.chart.series.LineSeries
import kotlin.properties.Delegates.notNull
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding by notNull()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.buttun1.setOnClickListener {
            binding.svChart.setSeries(LineSeries().apply {
                addAllPoint(data())
            })
        }

        binding.btnLeft.setOnClickListener {
            binding.svChart.moveToLeft()
        }
        binding.btnRight.setOnClickListener {}
    }
}
