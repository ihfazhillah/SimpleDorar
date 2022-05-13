package com.ihfazh.dorar

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class Highlight(
    val start: Int,
    val end: Int
)

data class HadithItem(
    val rawText: String,
    val rawi: String,
    val mohaddith: String,
    val mashdar: String,
    val shafha: String,
    val hokm: String,
    val highlights: List<Highlight> = listOf()
)

class Dorar {
    private val MAIN_URL = "//dorar.net/dorar_api.json"


    suspend fun search(q: String, page : Int = 1): List<HadithItem>{
        return suspendCoroutine {
            val qs = mapOf(
                "skey" to q,
                "page" to page.toString()
            )

            val response = getResponse(qs)
            val html = getHTML(response)
            it.resume(parseHtml(html))
        }
    }

    private fun buildQueryString(queryString: Map<String, String>): String =
        queryString.entries.joinToString("&") { (key, value) ->
            "$key=$value"
        }.trim()

    private fun buildURI(queryString: Map<String, String>): URL =
        URI("https", null, MAIN_URL, buildQueryString(queryString), null)
            .toURL()

    private fun getResponse(queryString: Map<String, String>): String {
        return buildString {
            val uri = buildURI(queryString)
            val connection = uri.openConnection().apply {
                readTimeout = 60_000 // 1 minutes
            }
            val reader = BufferedReader(
                InputStreamReader(connection.getInputStream())
            )

            var temp: String? = reader.readLine()
            while(true){
                append(temp)
                append("\n")
                temp = reader.readLine()
                if (temp == null) break

                reader.close()
            }
        }
    }

    private fun getHTML(response: String): String {
        val parser = JSONParser()
        val obj = parser.parse(response) as JSONObject
        val ahadith = obj["ahadith"] ?: return ""
        return (ahadith as JSONObject).getOrDefault("result", "") as String
    }

    fun parseHtml(html: String): List<HadithItem>{
        val doc = Jsoup.parseBodyFragment(html)

        val hadith = doc.select(".hadith")
        val hadithInfo = doc.select(".hadith-info")


        val hadiths = hadith.map{
            val rawText = it.text().replace(Regex("\\d+ - "), "")

            // TODO: try to fix search keys agar semua kedetek
            val highlights = it.select(".search-keys").map{ el ->
                val index = rawText.indexOf(el.text())
                val end = index + el.text().length

                listOf(index, end)
            }

            mapOf(
                "rawText" to rawText,
                "highlights" to highlights
            )
        }

        val mapSubtitle = mapOf(
            "خلاصة حكم المحدث" to "hokm",
            "الراوي" to "rawi",
            "المصدر" to "mashdar",
            "المحدث" to "mohaddith",
            "الصفحة أو الرقم" to "shafha"
        )

        val infos = hadithInfo.map {  info ->
            val infoMap = mutableMapOf<String, String>()

            val subtitles = info.select(".info-subtitle")
            subtitles.forEach { subtitle ->
                val title = subtitle.text().replace(":", "").trim()

                val nextSibling = subtitle.nextSibling() as TextNode
                val value = if (nextSibling.text().length > 1){
                    nextSibling.text().trim().replace("\\n", "")
                } else {
                    info.select("span:not(.info-subtitle)").text()
                }

                infoMap[mapSubtitle[title]!!] = value
            }

            infoMap.toMap()
        }

        return hadiths.zip(infos).map { (hadith, info) ->
            HadithItem(
                hadith["rawText"] as String,
                info["rawi"]!!,
                info["mohaddith"]!!,
                info["mashdar"]!!,
                info["shafha"]!!,
                info["hokm"]!!,
                (hadith["highlights"] as List<List<Int>>).map{
                    Highlight(it[0], it[1])
                }
            )
        }
    }
}

