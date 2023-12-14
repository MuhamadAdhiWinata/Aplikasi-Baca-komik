package com.adhi.komik.ui.screen.favorite

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
class FavoriteViewModel @Inject constructor(
    private val komikRepository: KomikRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Komik>>> = MutableStateFlow(UiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    fun getFavoriteKomik() = viewModelScope.launch {
        komikRepository.getFavoriteKomik()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateKomikPlace(id: Int, newState: Boolean) {
        komikRepository.updateKomikPlace(id, newState)
        getFavoriteKomik()
    }
}