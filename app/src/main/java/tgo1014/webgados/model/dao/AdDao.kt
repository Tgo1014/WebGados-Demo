package tgo1014.webgados.model.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import tgo1014.webgados.model.objects.Ad

@Dao
interface AdDao {
    @Query("SELECT * FROM ad")
    fun getAll(): List<Ad>

    @Query("SELECT * FROM ad WHERE extendedId = :id")
    fun getById(id: Int): Ad

    @Insert
    fun insert(ad: Ad)

    @Delete
    fun deleteAd(ad: Ad)
}