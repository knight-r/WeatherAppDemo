package com.example.weatherappdemo.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherappdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val intent = Intent(this, ShowWeatherPage::class.java)
            val cityName:String = binding.etSearchCity.text.toString()

            val bundle = Bundle()
            bundle.putString("city_name", cityName)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }


}


