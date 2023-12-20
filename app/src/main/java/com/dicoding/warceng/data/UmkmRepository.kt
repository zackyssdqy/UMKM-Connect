package com.dicoding.warceng.data

import com.dicoding.warceng.model.UmkmSource
import com.dicoding.warceng.model.Umkm

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class UmkmRepository {

    private val umkm = mutableListOf<Umkm>()

    init {
        if (umkm.isEmpty()) {
            UmkmSource.dataUmkm.forEach {
                umkm.add(it)
            }
        }
    }

    fun getAllUmkm(): Flow<List<Umkm>> {
        return flowOf(umkm)
    }

    fun getAllUmkmByCategory(category: String): Flow<List<Umkm>>{
        return getAllUmkm().map { menu->
            menu.filter { menu ->
                menu.type == category
            }
        }
    }

    fun getUmkmById(menuId: Long): Umkm {
        return umkm.first {
            it.id == menuId
        }
    }

    fun getFavoriteUmkm(): Flow<List<Umkm>>{
        return flowOf(umkm.filter { it.isFavorite })
    }

    fun updateUmkm(id: Long, newValue: Boolean): Flow<Boolean> {
        val index = umkm.indexOfFirst { it.id == id }
        val result = if (index >= 0) {
            val heroDota = umkm[index]
            umkm[index] = heroDota.copy(isFavorite = newValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: UmkmRepository? = null

        fun getInstance(): UmkmRepository =
            instance ?: synchronized(this) {
                UmkmRepository().apply {
                    instance = this
                }
            }
    }
}