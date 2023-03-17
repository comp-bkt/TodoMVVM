package com.example.todomvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todomvvm.ui.todo.TodoFragment

class TodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TodoFragment.Companion.newInstance())
                .commitNow()
        }
    }
}