package com.dicoding.warceng.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.warceng.data.UmkmRepository
import com.dicoding.warceng.model.Umkm
import com.dicoding.warceng.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: UmkmRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Umkm>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Umkm>>
        get() = _uiState

    fun getUmkmById(umkmId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getUmkmById(umkmId))
        }
    }

}