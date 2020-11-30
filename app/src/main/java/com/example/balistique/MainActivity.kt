package com.mhugi.balistique

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import Balistique as B

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings: SharedPreferences = applicationContext.getSharedPreferences("values", 0)

        //Load saved values
        findViewById<EditText>(R.id.angle_input).setText(settings.getString("angle", ""))
        findViewById<EditText>(R.id.velocity_input).setText(settings.getString("velocity", ""))
        findViewById<EditText>(R.id.mass_input).setText(settings.getString("mass", ""))
        findViewById<EditText>(R.id.drag_input).setText(settings.getString("drag", ""))
        findViewById<EditText>(R.id.area_input).setText(settings.getString("area", ""))
        findViewById<EditText>(R.id.aird_input).setText(settings.getString("aird", ""))
    }

    override fun onStop() {
        super.onStop()
        val settings: SharedPreferences = applicationContext.getSharedPreferences("values", 0)
        val edit: SharedPreferences.Editor = settings.edit()

        //Save values for reload
        edit.putString("angle",    findViewById<EditText>(R.id.angle_input).text.toString())
        edit.putString("velocity", findViewById<EditText>(R.id.velocity_input).text.toString())
        edit.putString("mass",     findViewById<EditText>(R.id.mass_input).text.toString())
        edit.putString("drag",     findViewById<EditText>(R.id.drag_input).text.toString())
        edit.putString("area",     findViewById<EditText>(R.id.area_input).text.toString())
        edit.putString("aird",     findViewById<EditText>(R.id.aird_input).text.toString())

        edit.apply()
    }

    fun onCalculate (v: View) {
        //Convert input values to double
        val angle   = findViewById<EditText>(R.id.angle_input).text.toString().toDouble()
        val velocity= findViewById<EditText>(R.id.velocity_input).text.toString().toDouble()
        val mass    = findViewById<EditText>(R.id.mass_input).text.toString().toDouble()
        val dragc   = findViewById<EditText>(R.id.drag_input).text.toString().toDouble()
        val parea   = findViewById<EditText>(R.id.area_input).text.toString().toDouble()
        val aird    = findViewById<EditText>(R.id.aird_input).text.toString().toDouble()

        //Calculate output values
        val xVel       = B.getXVelocity(angle, velocity)
        val yVel       = B.getYVelocity(angle, velocity)
        val termVel    = B.getTerminalVelocity(aird, dragc, parea, mass)
        val peakPos    = B.getYMax(termVel, yVel)
        val timeToPeak = B.getTime(termVel, yVel)
        val flightTime = timeToPeak + B.getTime(termVel, termVel)
        val range      = B.getXPos(termVel, xVel, flightTime)

        //Display output values
        findViewById<TextView>(R.id.range_value).text  = String.format("%.2fm", range)
        findViewById<TextView>(R.id.flight_value).text = String.format("%.2fs", flightTime)
        findViewById<TextView>(R.id.peak_value).text   = String.format("%.2fm", peakPos)
        findViewById<TextView>(R.id.timep_value).text  = String.format("%.2fs", timeToPeak)
        findViewById<TextView>(R.id.tvel_value).text   = String.format("%.2f(m/s)", termVel)
    }
}