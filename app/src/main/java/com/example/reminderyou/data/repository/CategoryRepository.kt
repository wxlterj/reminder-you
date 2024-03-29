package com.example.reminderyou.data.repository

import com.example.reminderyou.data.local.dao.CategoryDao
import com.example.reminderyou.data.mapper.toCategory
import com.example.reminderyou.data.mapper.toCategoryEntity
import com.example.reminderyou.domain.model.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCategories(): Flow<List<Category>> {
        return  categoryDao.getCategories().mapLatest { categories ->
            categories.map { category -> category.toCategory() }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCategoryWithTasksById(categoryId: Int): Flow<Category> {
        return categoryDao.getCategoryWithTasksById(categoryId).mapLatest { it.toCategory() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCategoryById(categoryId: Int): Flow<Category> {
        return categoryDao.getCategoryById(categoryId).mapLatest { it.toCategory() }
    }

    suspend fun saveCategory(category: Category): Long {
        return categoryDao.saveCategory(category.toCategoryEntity())
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category.toCategoryEntity())
    }

}