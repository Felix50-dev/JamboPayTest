package com.example.jambopaytest.presentation.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jambopaytest.databinding.FragmentChartBinding
import com.example.jambopaytest.presentation.viewmodel.UserViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : Fragment() {

    private lateinit var binding: FragmentChartBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChartBinding.inflate(inflater, container, false)
        viewModel.ageDataLiveData.observe(viewLifecycleOwner) { ageData ->
            displayChart(ageData)
        }

        return binding.root

    }

    private fun displayChart(ageData: Map<Int, Int>) {
        val entries = ArrayList<BarEntry>()
        ageData.forEach { (age, count) ->
            entries.add(BarEntry(age.toFloat(), count.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "Age Distribution")
        barDataSet.color = Color.BLUE
        barDataSet.valueTextColor = Color.BLACK

        val barData = BarData(barDataSet)
        binding.barChartMatchingResults.data = barData
        binding.barChartMatchingResults.description.text = "Age of user"

        // Configure X-Axis
        val xAxis = binding.barChartMatchingResults.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f

        // Configure Y-Axis
        val yAxisLeft = binding.barChartMatchingResults.axisLeft
        yAxisLeft.labelCount = 5

        val yAxisRight = binding.barChartMatchingResults.axisRight
        yAxisRight.isEnabled = false
        binding.barChartMatchingResults.invalidate()
    }
}