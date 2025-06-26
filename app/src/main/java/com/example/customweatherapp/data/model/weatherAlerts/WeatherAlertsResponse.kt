import com.example.customweatherapp.data.model.weatherAlerts.Feature
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WeatherAlertsResponse(
    @SerialName("@context")
    var context: JsonElement? = null,
    var type: String = "",
    var features: List<Feature> = emptyList(),
    val title: String = "",
    val updated: String = ""
)