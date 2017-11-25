package tgo1014.webgados

import android.app.Application
import android.arch.persistence.room.Room
import tgo1014.webgados.model.database.AdDatabase

class WebGadosApplication : Application() {

    lateinit var adDatabase: AdDatabase

    override fun onCreate() {
        super.onCreate()
        adDatabase = Room.databaseBuilder(this, AdDatabase::class.java, "webGadosAdDb").build()
    }
}