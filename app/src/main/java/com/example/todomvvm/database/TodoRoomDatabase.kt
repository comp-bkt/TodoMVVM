package com.example.todomvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Todo::class], version = 1)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        // singleton to avoid multiple instances of the DB
        @Volatile
        private var INSTANCE: TodoRoomDatabase? = null
        fun getDatabase(context: Context): TodoRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(TodoRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        // create DB
                        INSTANCE = databaseBuilder(
                            context.applicationContext,
                            TodoRoomDatabase::class.java, "todo_database"
                        )
                            .addCallback(sRoomDatabaseCallback) // insert test data
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        /**
         * Override the onOpen method to populate the database with initial test data.
         * For this sample, we clear the database every time it is created or opened.
         *
         * If you want to populate the database only when the database is created for the 1st time,
         * override RoomDatabase.Callback()#onCreate
         */
        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                GlobalScope.launch(Dispatchers.IO) {
                    val mDao = INSTANCE!!.todoDao()
                    mDao.deleteAll()
                    val todo = Todo(1, "Wake up!", "either set 2 alarm clocks or none" )
                    mDao.insert(todo)
                    val todo1 = Todo(2, "Drink coffee!", "Use the big mugs")
                    mDao.insert(todo1)
                    val todo2 = Todo(3, "Ponder the duality of existence!", "and plant trees")
                    mDao.insert(todo2)
                    val todo3 = Todo(4, "make someone laugh", "read a comic")
                    mDao.insert(todo3)
                }
            }
        }
    }
}