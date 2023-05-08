package se.ju.mobile.mowerapp.utils

import android.os.AsyncTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ApiManager() {
    suspend fun makeHttpGetRequest(url: String): String = withContext(Dispatchers.IO) {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuffer()

            var inputLine = bufferedReader.readLine()
            while (inputLine != null) {
                response.append(inputLine)
                inputLine = bufferedReader.readLine()
            }

            bufferedReader.close()
            inputStream.close()
            connection.disconnect()

            response.toString()
        } else {
            throw RuntimeException("HTTP GET Request Failed with Error code : " + responseCode)
        }
    }
}
