package com.example.dados

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dados.Configuracao
import com.example.dados.SettingsActivity.Constantes.CONFIGURACOES_ARQUIVO
import com.example.dados.SettingsActivity.Constantes.NUMERO_DADOS_ATRIBUTO
import com.example.dados.SettingsActivity.Constantes.NUMERO_FACES_ATRIBUTO
import com.example.dados.SettingsActivity.Constantes.VALOR_NAO_ENCONTRADO
import com.example.dados.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var activitySettingsBinding: ActivitySettingsBinding

    private object Constantes {
        val CONFIGURACOES_ARQUIVO = "configuracoes"
        val NUMERO_DADOS_ATRIBUTO = "numeroDados"
        val NUMERO_FACES_ATRIBUTO = "numeroFaces"
        val VALOR_NAO_ENCONTRADO = -1
    }
    private lateinit var configuracoesSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(activitySettingsBinding.root)

        // Iniciando ou abrindo o arquivo de configurações
        configuracoesSharedPreferences = getSharedPreferences(CONFIGURACOES_ARQUIVO, MODE_PRIVATE)
        carregaConfiguracoes()

        activitySettingsBinding.salvarBt.setOnClickListener{
            val numeroDados: Int = (activitySettingsBinding.numeroDadosSp.selectedView as TextView).text.toString().toInt()
            val numeroFaces: Int = activitySettingsBinding.numeroFacesEt.text.toString().toInt()
            val configuracao = Configuracao(numeroDados, numeroFaces)
            val retornoIntent = Intent()
            retornoIntent.putExtra(Intent.EXTRA_USER, configuracao )
            setResult(RESULT_OK, retornoIntent)

            //Salvando as configurações
            salvarConfiguracoes(numeroDados, numeroFaces)

            finish()
        }
    }

    private fun carregaConfiguracoes() {
        val numeroDados: Int = configuracoesSharedPreferences.getInt(NUMERO_DADOS_ATRIBUTO, VALOR_NAO_ENCONTRADO)
        val numeroFaces: Int = configuracoesSharedPreferences.getInt(NUMERO_FACES_ATRIBUTO, VALOR_NAO_ENCONTRADO)
        if (numeroDados != VALOR_NAO_ENCONTRADO){
            activitySettingsBinding.numeroDadosSp.setSelection(numeroDados -1)
        }
        if (numeroFaces != -1){
            activitySettingsBinding.numeroFacesEt.setText(numeroFaces.toString())
        }
    }

    private fun salvarConfiguracoes(numeroDados: Int, numeroFaces: Int) {
        val editorSharedPreferences = configuracoesSharedPreferences.edit()
        editorSharedPreferences.putInt(NUMERO_DADOS_ATRIBUTO, numeroDados)
        editorSharedPreferences.putInt(NUMERO_FACES_ATRIBUTO, numeroFaces)
        editorSharedPreferences.commit()
    }
}