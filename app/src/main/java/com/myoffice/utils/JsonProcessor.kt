package com.myoffice.utils

import android.content.res.Resources
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter

class JsonProcessor {

    fun processAdsJSON(resources: Resources, id: Int): String {
        val writer = StringWriter()
        try {
            resources.openRawResource(id).use { resourceReader ->
                val reader = BufferedReader(InputStreamReader(resourceReader, "UTF-8"))
                var line: String? = reader.readLine()
                while (line != null) {
                    writer.write(line)
                    line = reader.readLine()
                }
            }
        } catch (e: Exception) {
            //
        }
        return writer.toString()
    }

    fun <T> constructUsingGson(type: Class<T>, jsonString: String): T {
        val gson = GsonBuilder().create()
        return gson.fromJson(jsonString, type)
    }
}