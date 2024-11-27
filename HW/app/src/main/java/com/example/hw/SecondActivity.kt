package com.example.hw

import Player
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private val players = mutableListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MainActivity'den gelen oyuncuyu al ve listeye ekle
        val newPlayer = intent.getParcelableExtra<Player>("player")
        newPlayer?.let { players.add(it) }

        // Varsayılan oyuncuları ekle
        players.addAll(
            listOf(
                Player("Garen", 85, 90, "Demacia"),
                Player("Darius", 78, 82, "Noxus")
            )
        )

        // Oyuncuları ekranda göster
        updatePlayerList()

        // ThirdActivity'ye geçiş
        binding.buttonGo.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            intent.putParcelableArrayListExtra("players", ArrayList(players))
            startActivityForResult(intent, REQUEST_CODE)
        }

        // Geri dön butonu
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val updatedPlayers = data?.getParcelableArrayListExtra<Player>("updatedPlayers")
            updatedPlayers?.let {
                players.clear()
                players.addAll(it)
                updatePlayerList()
            }
        }
    }

    private fun updatePlayerList() {
        binding.RateResult.text = players.joinToString("\n") { player ->
            "Name: ${player.name}, Clan: ${player.clan}, Avg Rate: ${player.averageRate()}"
        }
    }

    companion object {
        const val REQUEST_CODE = 1
    }
}
