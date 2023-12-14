package com.adhi.komik.data

import com.adhi.komik.model.Komik
import com.adhi.komik.model.KomikData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KomikRepositoryImpl @Inject constructor() : KomikRepository {

    private val dummyKomik = mutableListOf<Komik>()

    init {
        if (dummyKomik.isEmpty()) {
            dummyKomik.addAll(KomikData.dummyKomik)
        }
    }

    override fun getKomikPlaces() = flow {
        emit(dummyKomik)
    }

    override fun getKomikPlaceById(id: Int): Flow<Komik> {
        return flowOf(dummyKomik.first { it.id == id })
    }

    override fun getFavoriteKomik(): Flow<List<Komik>> {
        return flowOf(dummyKomik.filter { it.isFavorite })
    }

    override fun searchKomikPlaces(query: String) = flow {
        val data = dummyKomik.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    override fun updateKomikPlace(id: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyKomik.indexOfFirst { it.id == id }
        val result = if (index >= 0) {
            val komik = dummyKomik[index]
            dummyKomik[index] = komik.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }
}