package dev.volks.applocadora.data

import dev.volks.applocadora.R

// LISTA DOS BANNERS PARA SER COLOCADO NOS FILMES
class Banners {
    companion object {
        fun get(key: String): Int {
            val mapOfBanners = mapOf(
                "alice.png" to R.drawable.alice,
                "interestellar.png" to R.drawable.interestellar,
                "trquedemestre.png" to R.drawable.trquedemestre,
                "ratatouille.png" to R.drawable.ratatouille,
                "avengers.png" to R.drawable.avengers

                )
            return mapOfBanners[key] ?: R.drawable.nopic
        }
    }
}