package com.example.hw

import Player
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw.databinding.ActivityThirdBinding
import com.example.hw.databinding.DialogCustomBinding
import com.google.android.material.snackbar.Snackbar

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var dialogBinding: DialogCustomBinding
    private val players = mutableListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SecondActivity'den gelen oyuncuları al
        players.addAll(intent.getParcelableArrayListExtra<Player>("players") ?: emptyList())

        // Oyuncuları ekranda göster
        updatePlayerList()

        // Yeni oyuncu eklemek için diyalog aç
        binding.Editbut.setOnClickListener {
            dialogBinding = DialogCustomBinding.inflate(layoutInflater)
            val dialog = Dialog(this).apply {
                setContentView(dialogBinding.root)
                setCancelable(true)
            }
            dialog.show()

            dialogBinding.buttonSubmit.setOnClickListener {
                val name = dialogBinding.inputName.text.toString()
                val firstRate = dialogBinding.input1stRate.text.toString().toIntOrNull()
                val secondRate = dialogBinding.input2ndRate.text.toString().toIntOrNull()
                val clan = dialogBinding.inputClan.text.toString()

                if (name.isNotBlank() && firstRate != null && secondRate != null && clan.isNotBlank()) {
                    val newPlayer = Player(name, firstRate, secondRate, clan)
                    players.add(newPlayer)
                    updatePlayerList()
                    dialog.dismiss()
                    alertDialog()
                } else {
                    Snackbar.make(binding.root, "Please fill all fields", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        // Ana menüye dön
        binding.dialogBut.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putParcelableArrayListExtra("updatedPlayers", ArrayList(players))
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun updatePlayerList() {
        binding.textViewPlayers.text = players.joinToString("\n") { player ->
            "Name: ${player.name}, Clan: ${player.clan}, Avg Rate: ${player.averageRate()}"
        }
    }

    private fun alertDialog(){
        val dialogAlert = AlertDialog.Builder(this)

        dialogAlert.setTitle("Edited")
        dialogAlert.setMessage("The user is edited")

        dialogAlert.setPositiveButton("Ok"){dialog, which -> }

        dialogAlert.create().show()
    }

}

