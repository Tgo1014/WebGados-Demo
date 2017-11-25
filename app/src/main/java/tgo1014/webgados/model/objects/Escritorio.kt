package tgo1014.webgados.model.objects

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Escritorio(
        @PrimaryKey
        var _id: String = "",
        var __v: String = "",
        var active: String = "",
        var approved_at: String = "",
        var auditRef: String = "",
        var avatar_path: String = "",
        var capa_path: String = "",
        var city: String = "",
        var created_at: String = "",
        var descricao: String = "",
        var facebook_link: String = "",
        var instagram_link: String = "",
        var nome: String = "",
        var perfil: String = "",
        var seller: String = "",
        var site_link: String = "",
        var state: String = "",
        var type: String = "")
