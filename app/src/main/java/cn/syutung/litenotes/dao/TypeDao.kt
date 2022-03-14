package cn.syutung.litenotes.dao

import androidx.room.*
import cn.syutung.litenotes.empty.TodosEmpty
import cn.syutung.litenotes.empty.TypeEmpty
import java.sql.Date

@Dao
interface TypeDao {
    @Insert
    fun insert(vararg type: TypeEmpty)
    @Insert
    fun insertTypeEmptysAndFriends(user: TypeEmpty, friends: List<TypeEmpty>)

    @Delete
    fun delete( type: TypeEmpty)

    @Query("SELECT * FROM typelist")
    fun getAll(): List<TypeEmpty>
    @Query("SELECT * FROM typelist where id = :id")
    fun getbyId(
        id : Int
    ) : TypeEmpty

    @Update
    fun update(vararg  type: TypeEmpty)


}