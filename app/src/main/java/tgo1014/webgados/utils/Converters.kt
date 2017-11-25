package tgo1014.webgados.utils

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String): Array<String> {
        val listType = object : TypeToken<Array<String>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArray(list: Array<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}