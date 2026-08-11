package com.example.fungrafapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fungrafapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
        initListeners()
    }

    private fun initUi() {
        binding.btnGraficar.isEnabled = false
        binding.etLimiteInferior.isEnabled = false
        binding.etLimiteSuperior.isEnabled = false
        binding.etFuncion.requestFocus()
    }

    private fun initListeners() {
        // Se pulsa tecla ENTER
        binding.etFuncion.configurarTeclaEnter {
            etFuncionKeyEnter(binding.etFuncion.text.toString().trim())
        }

        binding.etLimiteInferior.configurarTeclaEnter {
            etLimiteInferiorKeyEnter()
        }

        binding.etLimiteSuperior.configurarTeclaEnter {
            etLimiteSuperiorKeyEnter()
        }
    }

    //######################################################
    // Lógica de Negocio / Validaciones
    //######################################################

    private fun etFuncionKeyEnter(strFuncion: String) {
        if (strFuncion.isEmpty()) {
            mostrarMensaje("Debes de especificar una funcion")
            return
        }

        binding.etLimiteInferior.isEnabled = true
        binding.etLimiteSuperior.isEnabled = true
        binding.etLimiteInferior.requestFocus()
    }

    private fun etLimiteInferiorKeyEnter() {
        val str = binding.etLimiteInferior.text.toString()
        if (str.isEmpty()) {
            mostrarMensaje("Debes de especificar un límite inferior")
            return
        }
        binding.etLimiteSuperior.requestFocus()
    }

    private fun etLimiteSuperiorKeyEnter() {
        val strFuncion = binding.etFuncion.text.toString().trim()
        val dLimiteInferior = binding.etLimiteInferior.text.toString().toDoubleOrNull()
        val dLimiteSuperior = binding.etLimiteSuperior.text.toString().toDoubleOrNull()

        // MEJORA: Validaciones en cascada limpias usando cláusulas de guarda
        if (strFuncion.isEmpty()) {
            mostrarErrorEditText(binding.etFuncion, "Debes de especificar una funcion")
            return
        }
        if (dLimiteInferior == null) {
            mostrarErrorEditText(binding.etLimiteInferior, "Debes de especificar un límite inferior")
            return
        }
        if (dLimiteSuperior == null) {
            mostrarErrorEditText(binding.etLimiteSuperior, "Debes de especificar un límite superior")
            return
        }
        if (dLimiteInferior >= dLimiteSuperior) {
            mostrarErrorEditText(binding.etLimiteInferior, "El límite inferior tiene que ser menor que el límite superior")
            return
        }

        // --- TODO CORRECTO ---
        ocultarTeclado()
        if(binding.btnGraficar.isEnabled){
            binding.btnGraficar.performClick()
        }
        else{
            binding.btnGraficar.isEnabled = true
        }
    }

    //######################################################
    // Funciones Auxiliares (Helpers)
    //######################################################

    // MEJORA: Función de extensión para eliminar código duplicado de los listeners
    private fun EditText.configurarTeclaEnter(onEnterPressed: () -> Unit) {
        this.setOnEditorActionListener { _, actionId, event ->
            val esAccionIme = actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_GO ||
                    actionId == EditorInfo.IME_ACTION_SEARCH

            val esEnterFisico = event != null &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER &&
                    event.action == KeyEvent.ACTION_DOWN

            if (esAccionIme || esEnterFisico) {
                onEnterPressed()
                true
            } else {
                false
            }
        }
    }

    // MEJORA: Centraliza la gestión de errores visuales y el foco
    private fun mostrarErrorEditText(editText: EditText, mensaje: String) {
        mostrarMensaje(mensaje)
        editText.requestFocus()
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun ocultarTeclado() {
        currentFocus?.let { vista ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(vista.windowToken, 0)
        }
    }
}
