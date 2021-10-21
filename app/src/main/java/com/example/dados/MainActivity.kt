package com.example.dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dados.Configuracao
import com.example.dados.R
import com.example.dados.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var geradorRandomico: Random
    //Variável auxiliar
    private var auxConfig: Configuracao = Configuracao()

    private lateinit var settingsActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        geradorRandomico = Random(System.currentTimeMillis())

        activityMainBinding.jogarDadoBt.setOnClickListener {
            if (auxConfig.numeroFaces <= 6 && auxConfig.numeroFaces >= 1) {
                if (auxConfig.numeroDados == 2) { //Sorteio de 2 dados
                    val resultado: Int = geradorRandomico.nextInt(1..6)
                    //Tratando o resultado da segunda imagem utilizando a mesma lógica do resultado 1
                    val resultado2: Int = geradorRandomico.nextInt(1..6)

                    "A(s) face(s) sorteado(as) foi(ram) $resultado e $resultado2".also {
                        activityMainBinding.resultadoTv.text = it
                    }

                    val nomeImagem = "dice_${resultado}"
                    val nomeImagem2 = "dice_${resultado2}"

                    activityMainBinding.resultadoIv.setImageResource(
                        resources.getIdentifier(nomeImagem, "mipmap", packageName)
                    )
                    activityMainBinding.resultadoIv2.setImageResource(
                        resources.getIdentifier(nomeImagem2, "mipmap", packageName)
                    )
                    if (resultado != 0 && resultado2 != 0) {
                        activityMainBinding.resultadoIv.visibility = VISIBLE
                        activityMainBinding.resultadoIv2.visibility = VISIBLE
                    } else {
                        activityMainBinding.resultadoIv.visibility = GONE
                        activityMainBinding.resultadoIv2.visibility = GONE
                    }

                } else {//Caso a escolha for apenas 1 dado, o outro fica invisivel
                    activityMainBinding.resultadoIv.visibility = VISIBLE
                    activityMainBinding.resultadoIv2.visibility = GONE

                    val resultado: Int = geradorRandomico.nextInt(1..6)
                    "A face sorteada foi $resultado".also {
                        activityMainBinding.resultadoTv.text = it
                    }
                    val nomeImagem = "dice_${resultado}"
                    activityMainBinding.resultadoIv.setImageResource(
                        resources.getIdentifier(nomeImagem, "mipmap", packageName)
                    )
                }
            }else{
                if (auxConfig.numeroDados == 2) { //Sorteio de 2 dados
                    activityMainBinding.resultadoIv.visibility = VISIBLE
                    activityMainBinding.resultadoIv2.visibility = VISIBLE

                    val resultado: Int = geradorRandomico.nextInt(auxConfig.numeroFaces)
                    val resultado2: Int = geradorRandomico.nextInt(auxConfig.numeroFaces)

                    "A(s) face(s) sorteado(as) foi(ram) $resultado e $resultado2".also {
                        activityMainBinding.resultadoTv.text = it
                    }
                    val nomeImagem = "dice_${resultado}"
                    val nomeImagem2 = "dice_${resultado2}"

                    activityMainBinding.resultadoIv.setImageResource(
                        resources.getIdentifier(nomeImagem, "mipmap", packageName)
                    )

                    activityMainBinding.resultadoIv2.setImageResource(
                        resources.getIdentifier(nomeImagem2, "mipmap", packageName)
                    )
                }else{
                    activityMainBinding.resultadoIv.visibility = VISIBLE
                    activityMainBinding.resultadoIv2.visibility = VISIBLE

                    val resultado: Int = geradorRandomico.nextInt(auxConfig.numeroFaces)
                    "A face sorteada foi $resultado".also {
                        activityMainBinding.resultadoTv.text = it
                    }
                    val nomeImagem = "dice_${resultado}"
                    activityMainBinding.resultadoIv.setImageResource(
                        resources.getIdentifier(nomeImagem, "mipmap", packageName)
                    )
                }
            }
        }

        settingsActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK){
                //Modificações na View
                if(result.data != null){
                    val configuracao: Configuracao? = result.data?.getParcelableExtra<Configuracao>(Intent.EXTRA_USER)
                    if (configuracao != null) {
                        auxConfig = configuracao
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settingsMi){
            val settingsIntent = Intent (this, SettingsActivity::class.java)
            settingsActivityLauncher.launch(settingsIntent)
            return true
        }
        return false
    }
}
