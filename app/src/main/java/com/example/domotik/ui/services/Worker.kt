import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.domotik.network.model.WeatherHistory
import java.time.LocalDate

class Worker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    val MainContext : Context = context

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        doRequest()
        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun doRequest() {
        val queue: RequestQueue = Volley.newRequestQueue(this.MainContext)

        val myReq: GsonRequest<WeatherHistory> = GsonRequest<WeatherHistory>(
            "https://www.montaccini.it/IEQ/API/get_raw_list_for_service.php?username=raspberrypiIEQ&password=123456789",
            WeatherHistory::class.java,
            null,
            createMyReqSuccessListener(),
            createMyReqErrorListener()
        )

        queue.add(myReq)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createMyReqSuccessListener(): Response.Listener<WeatherHistory> {
        return Response.Listener<WeatherHistory> { it ->
            analize(it)
        }
    }

    private fun createMyReqErrorListener(): Response.ErrorListener {
        return Response.ErrorListener { error ->
            // Do whatever you want to do with error.getMessage();
            Log.v("Errors", error.message.toString())
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun analize(data: WeatherHistory): String {
        var temperature: Float = 0.0f
        var co2: Float = 0.0f
        for (x in data.items) {
            temperature += x.main.temp
            co2 += x.main.co2
        }
        temperature /= 3
        co2 /= 3
        val currentDate = LocalDate.now()
        val month = currentDate.monthValue
        val isWinter = (month in 1..3) || (month == 11) || (month == 12)
        val isSummer = (month in 4..10)

        val temperatureInRange = when {
            isWinter -> temperature in 20.0..22.0
            isSummer -> temperature in 24.0..26.0
            else -> false  // Should not reach here, but good practice to handle all cases
        }

        val co2InRange = (co2 in 400.0..1000.0)

        val temperatureAdvice = when {
            isWinter && temperature < 20.0 -> "Accendi il riscaldamento."
            isWinter && temperature > 22.0 -> "Abbassa il riscaldamento."
            isSummer && temperature < 24.0 -> "Chiudi le finestre o accendi il riscaldamento."
            isSummer && temperature > 26.0 -> "Accendi il condizionatore o apri le finestre."
            else -> ""
        }

        val co2Advice = when {
            co2 < 400.0 -> "Ventila di meno, potrebbe essere necessario chiudere alcune finestre."
            co2 > 1000.0 -> "Apri una finestra per aumentare la ventilazione."
            else -> ""
        }

        val temperatureMessage =
            if (temperatureInRange) "La temperatura è nella media." else "La temperatura non è nella media. $temperatureAdvice"
        val co2Message =
            if (co2InRange) "Il livello di CO2 è nella media." else "Il livello di CO2 non è nella media. $co2Advice"

        Log.v("worker", "$temperatureMessage $co2Message")
        return "$temperatureMessage $co2Message"
    }

}
