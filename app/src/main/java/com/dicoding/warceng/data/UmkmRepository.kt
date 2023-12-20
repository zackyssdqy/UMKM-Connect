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