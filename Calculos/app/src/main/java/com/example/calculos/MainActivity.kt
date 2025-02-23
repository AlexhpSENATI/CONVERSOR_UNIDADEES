package com.example.calculos

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerUnitType: Spinner
    private lateinit var spinnerFromUnit: Spinner
    private lateinit var spinnerToUnit: Spinner
    private lateinit var etInput: EditText
    private lateinit var btnConvert: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerUnitType = findViewById(R.id.spinnerUnitType)
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit)
        spinnerToUnit = findViewById(R.id.spinnerToUnit)
        etInput = findViewById(R.id.etInput)
        btnConvert = findViewById(R.id.btnConvert)
        tvResult = findViewById(R.id.tvResult)

        val unitTypeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.unit_types,
            android.R.layout.simple_spinner_item
        )
        unitTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerUnitType.adapter = unitTypeAdapter

        spinnerUnitType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateUnitSpinners(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnConvert.setOnClickListener {
            convertUnits()
        }
    }

    private fun updateUnitSpinners(unitTypePosition: Int) {
        val adapter = when (unitTypePosition) {
            0 -> ArrayAdapter.createFromResource(this, R.array.temperature_units, android.R.layout.simple_spinner_item)
            1 -> ArrayAdapter.createFromResource(this, R.array.currency_units, android.R.layout.simple_spinner_item)
            2 -> ArrayAdapter.createFromResource(this, R.array.length_units, android.R.layout.simple_spinner_item)
            else -> throw IllegalArgumentException("Tipo de unidad no válido")
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFromUnit.adapter = adapter
        spinnerToUnit.adapter = adapter
    }

    private fun convertUnits() {
        val inputValue = etInput.text.toString()
        if (inputValue.isEmpty()) {
            tvResult.text = getString(R.string.error_empty_input)
            return
        }

        val value = inputValue.toDouble()
        val fromUnit = spinnerFromUnit.selectedItem as String
        val toUnit = spinnerToUnit.selectedItem as String
        val result = when (spinnerUnitType.selectedItemPosition) {
            0 -> convertTemperature(value, fromUnit, toUnit)
            1 -> convertCurrency(value, fromUnit, toUnit)
            2 -> convertLength(value, fromUnit, toUnit)
            else -> throw IllegalArgumentException("Tipo de unidad no válido")
        }

        tvResult.text = String.format("%.2f %s = %.2f %s", value, fromUnit, result, toUnit)
    }

    private fun convertTemperature(value: Double, fromUnit: String, toUnit: String): Double {
        return when (fromUnit) {
            "Celsius" -> when (toUnit) {
                "Fahrenheit" -> (value * 9 / 5) + 32
                "Kelvin" -> value + 273.15
                else -> value
            }
            "Fahrenheit" -> when (toUnit) {
                "Celsius" -> (value - 32) * 5 / 9
                "Kelvin" -> (value - 32) * 5 / 9 + 273.15
                else -> value
            }
            "Kelvin" -> when (toUnit) {
                "Celsius" -> value - 273.15
                "Fahrenheit" -> (value - 273.15) * 9 / 5 + 32
                else -> value
            }
            else -> value
        }
    }

    private fun convertCurrency(value: Double, fromUnit: String, toUnit: String): Double {
        val usdToPen = 3.75
        val usdToBob = 6.96
        val penToBob = 1.86

        return when (fromUnit) {
            "USD" -> when (toUnit) {
                "PEN" -> value * usdToPen
                "BOB" -> value * usdToBob
                else -> value
            }
            "PEN" -> when (toUnit) {
                "USD" -> value / usdToPen
                "BOB" -> value * penToBob
                else -> value
            }
            "BOB" -> when (toUnit) {
                "USD" -> value / usdToBob
                "PEN" -> value / penToBob
                else -> value
            }
            else -> value
        }
    }
    private fun convertLength(value: Double, fromUnit: String, toUnit: String): Double {
        return when (fromUnit) {
            "Metros" -> when (toUnit) {
                "Pies" -> value * 3.28084
                "Pulgadas" -> value * 39.3701
                else -> value
            }
            "Pies" -> when (toUnit) {
                "Metros" -> value / 3.28084
                "Pulgadas" -> value * 12
                else -> value
            }
            "Pulgadas" -> when (toUnit) {
                "Metros" -> value / 39.3701
                "Pies" -> value / 12
                else -> value
            }
            else -> value
        }
    }
}