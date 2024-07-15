import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.domotik.R
import com.example.domotik.network.model.WeatherHistory
import com.example.domotik.ui.Weather.IndoorWeatherActivity
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
            co2 < 400.0 -> "Livello di CO2 inferiore a 400 ppm, indica che l'ambiente è ben ventilato e c'è un ricambio d'aria frequente. Nessuna operazione consigliata"
            co2 > 1000.0 -> "Livello di Co2 elevato, apri una finestra per aumentare la ventilazione."
            else -> ""
        }

        val temperatureMessage =
            if (temperatureInRange) "La temperatura è di $temperature° ed è nella media." else "La temperatura non è nella media. $temperatureAdvice"
        val co2Message =
            if (co2InRange) "Il livello di CO2 è di $co2 ed è nella media." else "Il livello di CO2 è di $co2 Ppm. $co2Advice"

        Log.v("worker", "$temperatureMessage $co2Message")
        sendNotification("Controllo Indoor", "$temperatureMessage $co2Message")
        return "$temperatureMessage $co2Message"
    }

    private fun sendNotification(title: String, message: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "MY_CHANNEL_ID"

        // Crea l'intent che avvia l'Activity quando si clicca sulla notifica
        val mainIntent = Intent(applicationContext, IndoorWeatherActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val mainPendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Crea l'intent per l'azione del pulsante
        val actionIntent = Intent(applicationContext, IndoorWeatherActivity::class.java)
        val actionPendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 1, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "My Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.mipmap.ic_launcher) // Usa l'icona di notifica
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(mainPendingIntent) // Imposta il PendingIntent principale
            .setAutoCancel(true) // Cancella la notifica quando viene cliccata
            .setStyle(NotificationCompat.BigTextStyle().bigText(message)) // Notifica espandibile
            .addAction(R.drawable.baseline_build_circle_24, "Apri", actionPendingIntent) // Aggiungi il pulsante dell'azione
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }

}
