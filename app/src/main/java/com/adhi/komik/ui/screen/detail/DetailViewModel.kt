package com.adhi.komik.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adhi.komik.data.KomikRepository
import com.adhi.komik.model.Komik
import com.adhi.komik.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val komikRepository: KomikRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Komik>> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getKomikPlaceById(id: Int) = viewModelScope.launch {
        komikRepository.getKomikPlaceById(id)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateKomikPlace(id: Int, newState: Boolean) = viewModelScope.launch {
        komikRepository.updateKomikPlace(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getKomikPlaceById(id)
            }
    }
}