package com.example.todomvvm.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todomvvm.R
import com.example.todomvvm.database.Todo
import com.example.todomvvm.ui.todo.TodoListAdapter.TodoViewHolder

class TodoListAdapter internal constructor(context: TodoFragment) :
    RecyclerView.Adapter<TodoViewHolder>() {
    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoItemView: TextView

        init {
            todoItemView = itemView.findViewById(R.id.textView)
        }
    }

    private val mInflater: LayoutInflater
    private var mTodos // Cached copy of todos
            : List<Todo>? = null

    init {
        mInflater = LayoutInflater.from(context.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false)
        return TodoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        if (mTodos != null) {
            val current = mTodos!![position]
            holder.todoItemView.text = current.title
        } else {
            // Covers the case of data not being ready yet.
            holder.todoItemView.setText(R.string.no_todo)
        }
    }

    fun setTodos(todos: List<Todo>?) {
        mTodos = todos
        notifyDataSetChanged()
    }

    // getItemCount() is called many times, and when it is first called,
    // mTodos has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return if (mTodos != null) mTodos!!.size else 0
    }
}