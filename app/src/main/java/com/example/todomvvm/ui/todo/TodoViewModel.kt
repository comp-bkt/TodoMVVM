package com.example.todomvvm.ui.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomvvm.database.Todo
import com.example.todomvvm.database.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: TodoRepository

    // Using LiveData and caching what getTodos returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel
    var todos: LiveData<List<Todo>>

    init {
        mRepository = TodoRepository(application)
        todos = mRepository.todos!!
    }

    fun insert(todo: Todo) {
        viewModelScope.launch {
            mRepository.insert(todo)
        }
    }
}