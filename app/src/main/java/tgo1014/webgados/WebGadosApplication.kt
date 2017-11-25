package tgo1014.webgados

import android.app.Application
import android.arch.persistence.room.Room
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import tgo1014.webgados.model.database.AdDatabase


class WebGadosApplication : Application() {

    lateinit var adDatabase: AdDatabase

    override fun onCreate() {
        super.onCreate()
        adDatabase = Room.databaseBuilder(this, AdDatabase::class.java, "webGadosAdDb").build()
        offlinePicassoCache()
    }

    private fun offlinePicassoCache() {
        val built = Picasso.Builder(this)
                .downloader(OkHttpDownloader(this, Integer.MAX_VALUE.toLong()))
                .build()
//        built.setIndicatorsEnabled(true)
//        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)
    }
}