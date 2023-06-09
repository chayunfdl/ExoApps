package com.chayun.jetpackcompose.exoapplication.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chayun.jetpackcompose.exoapplication.data.ExosRepository
import com.chayun.jetpackcompose.exoapplication.di.Exo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val exosRepository: ExosRepository) : ViewModel() {
    private val _groupedExos = MutableStateFlow(
        exosRepository.getExos()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupedExos: StateFlow<Map<Char, List<Exo>>> get() = _groupedExos

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedExos.value = exosRepository.searchMember(newQuery)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}