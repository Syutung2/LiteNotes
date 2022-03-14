package cn.syutung.litenotes.datebase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cn.syutung.litenotes.dao.TodoDao
import cn.syutung.litenotes.dao.TypeDao
import cn.syutung.litenotes.empty.TodosEmpty
import cn.syutung.litenotes.empty.TypeEmpty

@Database(entities = [TypeEmpty::class], version = 1, exportSchema = false)
abstract  class TypeDatebase :RoomDatabase() {
    abstract  val typeDao: TypeDao?

    companion object {
        private const val DB_NAME = "types.db"

        @Volatile
        private var instance: TypeDatebase? = null
        @Synchronized
        fun getInstance(context: Context): TypeDatebase? {
            if (instance == null) {
                instance = create(context)
            }
            return instance
        }

        private fun create(context: Context): TypeDatebase? {
            val a : TypeDatebase = Room.databaseBuilder(
                context,
                TypeDatebase::class.java,
                DB_NAME
            ).build()


            return a;
        }
    }
}