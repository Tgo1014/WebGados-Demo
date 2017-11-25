package tgo1014.webgados.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import tgo1014.webgados.model.dao.AdDao
import tgo1014.webgados.model.objects.Ad

@Database(entities = arrayOf(Ad::class), version = 1)
abstract class AdDatabase : RoomDatabase() {

    abstract fun adDao(): AdDao
}