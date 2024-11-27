import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val name: String,
    val firstRate: Int,
    val secondRate: Int,
    val clan: String
) : Parcelable {
    // Ortalama hesaplama fonksiyonu
    fun averageRate(): Double {
        return (firstRate + secondRate) / 2.0
    }
}
