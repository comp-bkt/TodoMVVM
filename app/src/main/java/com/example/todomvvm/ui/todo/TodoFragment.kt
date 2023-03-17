package com.example.todomvvm.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todomvvm.R
import com.example.todomvvm.database.Todo

class TodoFragment : Fragment() {
    private var adapter: TodoListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//      return inflater.inflate(R.layout.main_fragment, container, false);
        val view: View
        view = inflater.inflate(R.layout.main_fragment, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = TodoListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // deprecated, mTodoViewModel = of(this).get(TodoViewModel.class);
        val mTodoViewModel = ViewModelProvider(this).get(
            TodoViewModel::class.java
        )

        // TODO: Use the ViewModel

        // Add an observer on the LiveData returned by getTodos.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mTodoViewModel.todos?.observe(viewLifecycleOwner
        ) { todos -> // Update the cached copy of the todos in the adapter.
            adapter!!.setTodos(todos)
        }
    }

    companion object {
        fun newInstance(): TodoFragment {
            return TodoFragment()
        }
    }
}