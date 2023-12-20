package com.dicoding.warceng.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.warceng.data.UmkmRepository
import com.dicoding.warceng.model.Umkm
import com.dicoding.warceng.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: UmkmRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Umkm>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Umkm>>>
        get() = _uiState

    fun getFavoriteUmkm() {
        viewModelScope.launch {
            repository.getFavoriteUmkm()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}