package com.example.todomvvm.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Todo::class], version = 1)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    /**
     * Populate the database in the background with initial test data
     */
    private class PopulateDbAsync internal constructor(db: TodoRoomDatabase?) :
        AsyncTask<Void?, Void?, Void?>() {
        private val mDao: TodoDao

        init {
            mDao = db!!.todoDao()
        }

        protected override fun doInBackground(vararg params: Void?): Void? {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll()
            val todo = Todo("Wake up!")
            todo.detail = "either set 2 alarm clocks or none"
            mDao.insert(todo)
            val todo1 = Todo("Drink coffee!")
            todo.detail = "Use the liter mugs"
            mDao.insert(todo1)
            val todo2 = Todo("Ponder the duality of existence!")
            todo.detail = "and plant trees"
            mDao.insert(todo2)
            val todo3 = Todo("make someone laugh")
            todo.detail = "read a comic"
            mDao.insert(todo3)
            return null
        }
    }

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
                //                  super.onCreate(db);

                // If you want to keep the data through app restarts,
                // comment out the following line.
                PopulateDbAsync(INSTANCE).execute()
            }
        }
    }
}