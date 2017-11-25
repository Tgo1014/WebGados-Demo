package tgo1014.webgados.model.objects

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Farm(@PrimaryKey
                var _id: String = "",
                var city_id: String = "",
                var street: String = "",
                var name: String = "",
                var longitude: String = "",
                var latitude: String = "",
                var state: String = "",
                var city: String = "")