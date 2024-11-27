package com.example.hw

import Player
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Blink animasyonu
        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)
        binding.textView.startAnimation(blinkAnimation)

        // Spinner ayarları (Resim ve Metin)
        val clans = listOf(
            Clan("Select your clan", 0), // İlk öğe boş bir seçenek
            Clan("Demacia", R.drawable.demacia),
            Clan("Noxus", R.drawable.noxus)
        )

        // Spinner adapter ayarı
        val spinnerAdapter = ClanSpinnerAdapter(this, clans)
        binding.spinnerActivityLevel.adapter = spinnerAdapter

        // Spinner seçim dinleyici
        binding.spinnerActivityLevel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedView: android.view.View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> binding.imageView.setImageResource(android.R.color.transparent) // "Select" seçildiğinde resim temizlenir
                    1 -> binding.imageView.setImageResource(R.drawable.demacia) // Demacia resmi
                    2 -> binding.imageView.setImageResource(R.drawable.noxus) // Noxus resmi
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Eğer hiç bir şey seçilmediyse, bir şey yapmaya gerek yok
            }
        }

        // SeekBar ile şeffaflık ayarı
        binding.seekBarOpacity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Progress 0-100 arasında gelir, alpha ise 0-1 arasında olmalıdır
                binding.imageView.alpha = progress / 100f // 0-100 arasında değer, alpha değeri 0-1 arası
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Hesapla butonuna tıklanırsa
        binding.buttonCalculate.setOnClickListener {
            val name = binding.TeamName.text.toString()
            val firstRate = binding.FirstMatchRate.text.toString().toIntOrNull() ?: 0
            val secondRate = binding.SecondMatchRate.text.toString().toIntOrNull() ?: 0
            val clan = clans[binding.spinnerActivityLevel.selectedItemPosition].name

            // Gösterilecek toast mesajı
            Toast.makeText(this, "Calculate Active", Toast.LENGTH_LONG).show()

            if (name.isNotBlank() && clan != "Select your clan") {
                // Yeni oyuncu oluşturuluyor
                val player = Player(name, firstRate, secondRate, clan)
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("player", player)
                startActivity(intent)
            } else {
                // Hatalı giriş yapılmışsa, hata mesajı
                binding.TeamName.error = "Please fill all fields and select a clan"
            }
        }
    }
}
