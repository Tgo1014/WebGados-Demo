package tgo1014.webgados.model.objects

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Ad(
        @PrimaryKey var id: String = "",
        @SerializedName("extendedId") var extendedId: String = "",
        var adType: String = "",
        var age_max: String = "",
        var age_min: String = "",
        var approved_at: String = "",
        var auditAt: String = "",
        var auditRef: String = "",
        var average_weight: String = "",
        var category_name: String = "",
        var classificacao: String = "",
        var created_at: String = "",
        var created_at_unix: String = "",
        var description: String = "",
        var expiration_date: String = "",
        var expire_at: String = "",
        @Embedded(prefix = "farm_") var farm: Farm = Farm(),
        var fase: String = "",
        var favorite: String = "",
        var featured: String = "",
        var gender: String = "",
        var habilidades: Array<String> = arrayOf(),
        var head_count: String = "",
        var kind_name: String = "",
        var list_partner_ad: String? = "",
        var location: Array<String> = arrayOf(),
        var meio_sangue: String = "",
        var modified_at: String = "",
        var photos_url: Array<String> = arrayOf(),
        var price: String = "",
        var price_type: String = "",
        var racas: Array<String> = arrayOf(),
        var renewals: Array<String> = arrayOf(),
        var seller_celular: String = "",
        var seller_id: String = "",
        var seller_name: String = "",
        var seller_rating: String = "",
        var seller_avatar: String = "",
        var sku: String = "",
        var slug: String = "",
        var status: String = "",
        var subcategory_name: String = "",
        var title: String = "",
        var title_search: String = "",
        var treinamentos: Array<String> = arrayOf(),
        var unit_value: String = "",
        var updates: Array<String> = arrayOf(),
        var video_url: String = "",
        var views_count: String = "",
        var weight_type: String = "",
        var years: String = "",
        var months: String = "") {

    companion object {
        val AD_ID_EXTRA = "AdIdExtra"
    }
}