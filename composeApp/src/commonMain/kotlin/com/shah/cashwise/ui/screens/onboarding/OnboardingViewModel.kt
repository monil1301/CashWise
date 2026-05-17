package com.shah.cashwise.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnboardingViewModel(
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun updateTotalPages(totalPages: Int) {
        val safeTotal = totalPages.coerceAtLeast(0)
        _state.update { current ->
            val safePage = when {
                safeTotal == 0 -> 0
                current.currentPage > safeTotal - 1 -> safeTotal - 1
                else -> current.currentPage
            }
            current.copy(
                currentPage = safePage,
                totalPages = safeTotal,
            )
        }
    }

    fun onAction(action: OnboardingAction): Boolean {
        return when (action) {
            OnboardingAction.Next -> handleNext()
            OnboardingAction.Skip -> true
            is OnboardingAction.PageChanged -> {
                setCurrentPage(action.pageIndex)
                false
            }
        }
    }

    private fun handleNext(): Boolean {
        val current = _state.value
        if (current.totalPages == 0) return false

        if (current.isLastPage) return true

        _state.update { state ->
            state.copy(currentPage = state.currentPage + 1)
        }
        return false
    }

    private fun setCurrentPage(pageIndex: Int) {
        val totalPages = _state.value.totalPages
        if (totalPages == 0) return

        val safeIndex = pageIndex.coerceIn(0, totalPages - 1)
        _state.update { state ->
            if (state.currentPage == safeIndex) state else state.copy(currentPage = safeIndex)
        }
    }
}
