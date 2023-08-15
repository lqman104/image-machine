package com.luqman.imagemachine.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.imagemachine.data.repository.DataSource
import com.luqman.imagemachine.data.repository.model.Machine
import com.luqman.imagemachine.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase,
    private val dataSource: DataSource
) : ViewModel() {

    private val _machine = MutableStateFlow<List<Machine>>(listOf())
    val response = _machine.asStateFlow()

    fun search(query: String) {
        if (useCase(query).successful) {
            viewModelScope.launch {
                val data = dataSource.fetch()
                _machine.value = data
            }
        }
    }
}