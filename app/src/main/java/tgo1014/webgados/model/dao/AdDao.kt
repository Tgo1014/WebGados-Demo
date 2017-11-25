package tgo1014.webgados.model.dao

import android.arch.persistence.room.*
import tgo1014.webgados.model.objects.Ad

@Dao
interface AdDao {
    @Query("SELECT * FROM ad")
    fun getAll(): List<Ad>

    @Query("SELECT * FROM ad WHERE extendedId = :id")
    fun getById(id: Int): Ad

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ad: Ad)

    @Delete
    fun deleteAd(ad: Ad)
}