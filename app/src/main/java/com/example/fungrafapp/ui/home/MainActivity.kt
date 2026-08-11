package com.example.fungrafapp.ui.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fungrafapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi();
        initListeners()
    }

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
                etFuncionPressEnter()
                true // Avisamos que ya procesamos esta pulsacion
            }
            else{
                false // Retornamos false para que el sistema maneje otras teclas de forma normal
            }
        }
    }

    private fun etFuncionPressEnter() {
        Toast.makeText(
            this,
            "Debes especificar una función matemática.",
            Toast.LENGTH_SHORT
        ).show()
        binding.etFuncion.requestFocus();
    }

    private fun initUi() {
        binding.btnGraficar.isEnabled = false;
        binding.etFuncion.requestFocus()
    }
}