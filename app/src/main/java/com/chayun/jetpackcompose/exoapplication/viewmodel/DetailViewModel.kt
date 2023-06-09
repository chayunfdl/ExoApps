package com.chayun.jetpackcompose.exoapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.chayun.jetpackcompose.exoapplication.data.ExosRepository
import com.chayun.jetpackcompose.exoapplication.di.Exo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel (
    private val exoRepository: ExosRepository) : ViewModel() {
    fun getExoData(idBiass: String): StateFlow<Exo> = MutableStateFlow(
        exoRepository.getExoById(idBiass)
    ).asStateFlow()
}