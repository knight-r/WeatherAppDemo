package com.example.weatherappdemo.activities

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappdemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        mainBinding.rbCityName.isChecked = true
        mainBinding.rbCityName.setOnClickListener{
            mainBinding.etSearch.apply {
                isEnabled = true
                isFocusable = true
                inputType = InputType.TYPE_CLASS_TEXT
                setText("")
                filters += InputFilter.LengthFilter(100)
            }
        }
        mainBinding.rbPincode.setOnClickListener{
            mainBinding.etSearch.apply {
                isEnabled = true
                isFocusable = true
                inputType = InputType.TYPE_CLASS_NUMBER
                setText("")
                filters += InputFilter.LengthFilter(6)
            }
        }
        mainBinding.searchButton.setOnClickListener {
            val intent = Intent(this, ShowWeatherPage::class.java)
            val stringName = mainBinding.etSearch.text
                    val bundle = Bundle()
            if(mainBinding.rbCityName.id == mainBinding.radioGroup.checkedRadioButtonId){
                bundle.putString("input_data", stringName.toString())
                bundle.putInt("input_type",1)
                intent.putExtras(bundle)
                startActivity(intent)
            } else{
                bundle.putString("input_data", stringName.toString())
                bundle.putInt("input_type",2)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }
    }

}


