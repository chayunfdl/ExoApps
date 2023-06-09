package com.chayun.jetpackcompose.exoapplication.data

import com.chayun.jetpackcompose.exoapplication.di.Exo
import com.chayun.jetpackcompose.exoapplication.di.ExosData

class ExosRepository {
    fun getExos(): List<Exo> {
    return ExosData.exos
}

    fun searchMember(query: String): List<Exo> {
        return ExosData.exos.filter { exo ->
            exo.name.contains(query, ignoreCase = true)
        }
    }

    fun getExoById(id: String): Exo {
        return ExosData.exos.find {
            it.id == id
        } as Exo
    }
}