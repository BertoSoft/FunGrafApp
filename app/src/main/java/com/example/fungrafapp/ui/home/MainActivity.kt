package com.example.fungrafapp.ui.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.example.fungrafapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aqui empieza mi codigo
        initUi();
        initListeners()
    }

    //Funciones App
    private fun initListeners() {

        //etFuncion Captura tecla ENTER
        binding.etFuncion.setOnEditorActionListener { _, id, ev ->
            val teclaEnter = id == EditorInfo.IME_ACTION_DONE ||
                    id == EditorInfo.IME_ACTION_GO ||
                    id == EditorInfo.IME_ACTION_SEARCH
            val teclaEnterFisica = ev != null &&
                    ev.keyCode == KeyEvent.KEYCODE_ENTER &&
                    ev.action == KeyEvent.ACTION_DOWN

            if(teclaEnter || teclaEnterFisica){
                keyEnteretFuncion(binding.etFuncion.text.toString())
                true // Avisamos que ya procesamos esta pulsacion
            }
            else{
                false // Retornamos false para que el sistema maneje otras teclas de forma normal
            }
        }
    }

    private fun initUi() {
        binding.btnGraficar.isEnabled = false
        binding.etLimiteInferior.isEnabled = false
        binding.etLimiteSuperior.isEnabled = false
        binding.etFuncion.requestFocus()
    }

    // Funciones Privadas
    fun keyEnteretFuncion(strFuncion: String) {
        if(strFuncion.isEmpty()){
            Toast.makeText(
                this,
                "Debes de especificar una Funcion",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            // Procesamos la sintaxis de la ecuacion

            binding.etLimiteInferior.isEnabled = true
            binding.etLimiteSuperior.isEnabled = true
            binding.etLimiteInferior.requestFocus()
        }
    }
}