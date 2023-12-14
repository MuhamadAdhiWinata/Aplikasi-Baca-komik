package com.adhi.komik.data

import com.adhi.komik.model.Komik
import kotlinx.coroutines.flow.Flow

interface KomikRepository {
    fun getKomikPlaces(): Flow<List<Komik>>

    fun getKomikPlaceById(id: Int): Flow<Komik>

    fun getFavoriteKomik(): Flow<List<Komik>>

    fun searchKomikPlaces(query: String): Flow<List<Komik>>

    fun updateKomikPlace(id: Int, newState: Boolean): Flow<Boolean>
}