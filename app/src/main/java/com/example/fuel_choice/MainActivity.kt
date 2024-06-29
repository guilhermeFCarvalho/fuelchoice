package com.example.fuel_choice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fuel_choice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var btnId: Int? = null
    private var bestFuelType: String? = null;
    private var fuelType1: String? = null;
    private var fuelType2: String? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null) {
            bestFuelType = savedInstanceState.getString("BEST_FUEL_TYPE")
            fuelType1 = savedInstanceState.getString("FUEL_TYPE_1")
            fuelType2 = savedInstanceState.getString("FUEL_TYPE_2")
            if (fuelType1 != null) {
                binding.inputConsumption1.hint = fuelType1
            }
            if (fuelType2 != null) {
                binding.inputConsumption2.hint = fuelType2
            }
            binding.tvResult.text = bestFuelType
        }

        binding.btnSearch1.setOnClickListener {
            val intent = Intent(this, SelectFuelActivity::class.java)
            btnId = binding.btnSearch1.id

            getFuelType.launch(intent)
        }
        binding.btnSearch2.setOnClickListener {

            btnId = binding.btnSearch2.id
            val intent = Intent(this, SelectFuelActivity::class.java)

            getFuelType.launch(intent)
        }
        binding.btnCalculate.setOnClickListener {
            val inputConsumption1: Double? =
                binding.etConsumption1?.text?.toString()?.toDoubleOrNull()
            val inputConsumption2: Double? =
                binding.etConsumption2?.text?.toString()?.toDoubleOrNull()
            val literValue1: Double? = binding.etLiter1?.text?.toString()?.toDoubleOrNull()
            val literValue2: Double? = binding.etLiter2?.text?.toString()?.toDoubleOrNull()
            if (inputConsumption1 != null && inputConsumption2 != null && literValue1 != null && literValue2 != null && fuelType1 != null && fuelType2 != null) {
                val result1 = literValue1 / inputConsumption1
                val result2 = literValue2 / inputConsumption2

                if (result1 < result2) {
                    binding.tvResult.text = fuelType1
                    bestFuelType = fuelType1

                } else {
                    binding.tvResult.text = fuelType2
                    bestFuelType = fuelType2
                }

            } else {
                Toast.makeText(this, handleMessage(), Toast.LENGTH_SHORT).show()
            }

        }
        binding.btnClear.setOnClickListener {
            clear()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("BEST_FUEL_TYPE", bestFuelType)
        outState.putString("FUEL_TYPE_1", fuelType1)
        outState.putString("FUEL_TYPE_2", fuelType2)
    }


    private fun handleMessage(): String {
        return if (fuelType1 == null || fuelType2 == null) getString(R.string.select_fuel) else getString(R.string.fill_all_fields)
    }

    private fun clear() {
        binding.etConsumption1.text?.clear()
        binding.etConsumption2.text?.clear()
        binding.etLiter1.text?.clear()
        binding.etLiter2.text?.clear()
        binding.tvResult.text = ""
        bestFuelType = null

    }

    private val getFuelType =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val selectedFuel = it.data!!.getStringExtra("selectedFuel")
                clear()
                when (btnId) {
                    binding.btnSearch1.id -> {
                        binding.inputConsumption1.hint = selectedFuel
                        fuelType1 = selectedFuel
                    }

                    binding.btnSearch2.id -> {
                        binding.inputConsumption2.hint = selectedFuel
                        fuelType2 = selectedFuel
                    }
                }
            }


        }
}