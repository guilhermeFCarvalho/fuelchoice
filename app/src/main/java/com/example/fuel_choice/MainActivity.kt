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
            if (inputConsumption1 != null && inputConsumption2 != null && literValue1 != null && literValue2 != null &&  bestFuelType != null) {
                val result1 = literValue1 / inputConsumption1
                val result2 = literValue2 / inputConsumption2

                if (result1 < result2) {
                    binding.tvResult.text = bestFuelType

                } else {
                    binding.tvResult.text = bestFuelType
                }

            }else{
                Toast.makeText(this, handleMessage(), Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnClear.setOnClickListener {
            clear()
        }


    }

    private fun handleMessage(): String {
        return if (bestFuelType != null) "Preencha todos os campos!" else "Informe os tipos de combustível!"
    }





    private fun clear() {
        binding.etConsumption1.text?.clear()
        binding.etConsumption2.text?.clear()
        binding.etLiter1.text?.clear()
        binding.etLiter2.text?.clear()
        binding.tvResult.text = ""
    }

    private val getFuelType =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                bestFuelType = it.data!!.getStringExtra("selectedFuel")
                clear()
                when (btnId) {
                    binding.btnSearch1.id -> {
                        binding.inputConsumption1.hint = bestFuelType
                    }

                    binding.btnSearch2.id -> {
                        binding.inputConsumption2.hint = bestFuelType
                    }
                }
            }


        }
}