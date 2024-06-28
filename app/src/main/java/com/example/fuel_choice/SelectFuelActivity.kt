package com.example.fuel_choice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fuel_choice.databinding.ActivitySelectFuelBinding

class SelectFuelActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectFuelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySelectFuelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listView = binding.lvSelectFuel

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem: String = listView.getItemAtPosition(position).toString()
            intent.putExtra("selectedFuel", selectedItem)
            setResult(RESULT_OK, intent)
            finish()


        }
    }

}
