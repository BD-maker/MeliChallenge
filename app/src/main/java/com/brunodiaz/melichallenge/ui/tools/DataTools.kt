package com.brunodiaz.melichallenge.ui.tools

/*
    Funciones para dar formato a los strings recibidos por la llamada API
 */

object DataTools {

    fun setResultsText(results: Int): String {
        if (results < 2000) return results.toString() + " Resultados" else return "+2.000 Resultados"
    }

    fun setPriceText(price: Double): String {
        if (price > 1000)
            return "$ %.3f".format(price / 1000)
        else
            return "$ %.0f".format(price)
    }

    fun setShipmentText(freeshipping: Boolean): String {
        if (freeshipping) return "Envio Gratis" else return ""
    }

    fun setConditionText(condition: String): String {
        when (condition) {
            "new" -> return "Nuevo"
            "used" -> return "Usado"
            else -> return "No especificado"
        }
    }

    fun setSoldText(sold: Int): String = "  |   $sold Vendidos"

    fun setAvailableItemsText(availableItems: Int): String {
        if (availableItems > 1)
            return "Stock Disponible: $availableItems"
        else
            return "Unico Disponible"
    }


}