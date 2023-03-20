package com.example.todomvvm.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class TodoRepository(application: Application) {
    private val mTodoDao: TodoDao?

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val todos: LiveData<List<Todo>>?

    // Note that in order to unit test the TodoRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    init {
        val db: TodoRoomDatabase? = TodoRoomDatabase.Companion.getDatabase(application)
        mTodoDao = db?.todoDao()
        todos = mTodoDao?.getAll()
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    suspend fun insert(todo: Todo?) {
        mTodoDao!!.insert(todo)
    }

    fun getAll(): LiveData<List<Todo>>? {
        return mTodoDao?.getAll()
    }


}