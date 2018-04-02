package at.chaoticbits.api

import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.entity.BufferedHttpEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.telegram.telegrambots.logging.BotLogger

import java.io.IOException


/**
 * Http Client Helper
 */
object Api {

    private const val LOG_TAG: String = "Api"
    private val client: CloseableHttpClient = HttpClientBuilder.create().setSSLHostnameVerifier(NoopHostnameVerifier()).build()


    /**
     * Request a resource from the given url
     * @param url url
     * @return [Response] containing the body and status code
     */
    fun fetch(url: String): Response? {

        try {

            client.execute(HttpGet(url)).use { response: CloseableHttpResponse ->
                return Response(
                        response.statusLine.statusCode,
                        EntityUtils.toString(BufferedHttpEntity(response.entity), "UTF-8"))

            }
        } catch (e: IOException) {
            BotLogger.error(LOG_TAG, "Error fetching " + url + "\n" + e.message)
            return null
        }
    }
}
