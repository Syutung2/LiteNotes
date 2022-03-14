package cn.syutung.litenotes.datebase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cn.syutung.litenotes.dao.TodoDao
import cn.syutung.litenotes.empty.Converters
import cn.syutung.litenotes.empty.TodosEmpty

@Database(entities = [TodosEmpty::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract  class TodoDatebase :RoomDatabase() {
    public  abstract val todoDao: TodoDao

    companion object {
        private const val DB_NAME = "todos.db"

        @Volatile
        private var instance: TodoDatebase? = null
        @Synchronized
        fun getInstance(context: Context): TodoDatebase? {
            if (instance == null) {
                instance = create(context)
            }
            return instance
        }

        private fun create(context: Context): TodoDatebase {
            return Room.databaseBuilder(
                context,
                TodoDatebase::class.java,
                DB_NAME
            ).build()
        }
    }
}