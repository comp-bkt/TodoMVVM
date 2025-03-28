package com.example.todomvvm.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Room uses this DAO where you map a Java method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */
@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table")
    fun getAll() : LiveData<List<Todo>>

    // conflict resolution strategy - REPLACE will change the item if matched
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()
}