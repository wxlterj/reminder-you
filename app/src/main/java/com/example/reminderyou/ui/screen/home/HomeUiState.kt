package com.example.reminderyou.ui.screen.home

import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.domain.model.Task
import com.example.reminderyou.domain.model.TaskWithCategory

sealed class HomeUiState {
    data class Success(
        val showTaskDetails: Boolean = false,
        val showAddCategory: Boolean = false,
        val tasks: List<TaskWithCategory> = emptyList(),
        val categories: List<Category> = emptyList(),
        val currentTask: TaskWithCategory = TaskWithCategory()
    ) : HomeUiState()
    data object Loading : HomeUiState()
    data object Error : HomeUiState()
}