package com.dicoding.warceng.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.warceng.data.UmkmRepository
import com.dicoding.warceng.model.Umkm
import com.dicoding.warceng.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UmkmRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Umkm>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Umkm>>>
        get() = _uiState

    fun getAllUmkm() {
        viewModelScope.launch {
            repository.getAllUmkm()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { umkm ->
                    _uiState.value = UiState.Success(umkm)
                }
        }
    }

}