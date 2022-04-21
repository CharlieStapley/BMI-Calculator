package com.example.bmicalculator

import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var resultText = findViewById<EditText>(R.id.result_text)
        resultText.isFocusable = false
        resultText.isFocusableInTouchMode = false
        resultText.inputType = InputType.TYPE_NULL

        var infoButton = findViewById<ImageButton> (R.id.infoButton);
        infoButton.setVisibility(View.GONE)
        infoButton.setOnClickListener {
            Toast.makeText(applicationContext, "The BMI calculated does not take into account muscle mass.", Toast.LENGTH_LONG).show()
        }

        var calculateButton = findViewById<Button> (R.id.bmi_calculate_button);
        calculateButton.setOnClickListener {
            val weight = findViewById<EditText>(R.id.weight_text)
            val height = findViewById<EditText>(R.id.height_text)
            var errorFlagged = false
            var heightText = -1.0
            try{
                heightText = height.text.toString().toDouble()

            } catch(ex:Exception) {
                errorFlagged = true
                Toast.makeText(applicationContext, "Error; Height must be entered", Toast.LENGTH_SHORT).show()
            }
            if (heightText == 0.0 || heightText == null){
                errorFlagged = true
                Toast.makeText(applicationContext, "Error; Height must not be 0", Toast.LENGTH_SHORT).show()
            }
            var weightText = -1.0
            try{
                weightText = weight.text.toString().toDouble()
            } catch(ex:Exception) {
                errorFlagged = true
                Toast.makeText(applicationContext, "Error; Weight must be entered", Toast.LENGTH_SHORT).show()
            }
            if (weightText == 0.0 || weightText == null){
                errorFlagged = true
                Toast.makeText(applicationContext, "Error; Weight must not be 0", Toast.LENGTH_SHORT).show()
            }
            if(!errorFlagged){
                val bmi = calculateBMI(weightText, heightText)
                resultText.setText(bmi.toString())
                var infoButton = findViewById<ImageButton> (R.id.infoButton);
                infoButton.setVisibility(View.VISIBLE)
            }
        }

        val videoView = findViewById<VideoView>(R.id.video_view)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.bmi720p
        val uri: Uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)
        videoView.start()
    }

    private fun calculateBMI(width: Double, height: Double ): String{
        val bmi = (width / (height * height)).roundToInt()
        var bmi_result = "result not calculated"
        if(bmi < 18.5){
            bmi_result = "Underweight"
        }
        else if (18.5 <= bmi && bmi < 25) {
            bmi_result = "Healthy"
        }
        else if (25 <= bmi && bmi < 30){
            bmi_result = "Overweight"
        }
        else if (30 <= bmi){
            bmi_result = "Obese"
        }
        else{
            bmi_result = "result error"
        }
        return "BMI: $bmi Result: $bmi_result"
    }

}