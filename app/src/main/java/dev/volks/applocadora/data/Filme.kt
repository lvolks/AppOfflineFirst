package dev.volks.applocadora.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// OBJETO FILME INDICANDO MODELO DO FILME A SER ADICIONADO
@Entity(tableName = "filmes")
data class Filme(
    @PrimaryKey (autoGenerate = true)
    var id : Int,
    var docId : String,
    var nome : String,
    var descricao : String,
    var preco : Double,
    var nota : String,
    var foto : String
) {
    constructor(): this(0, "",  "", "", 0.0, "", "nopic.png")
}