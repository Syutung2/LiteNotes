package cn.syutung.litenotes.dao

import androidx.room.*
import cn.syutung.litenotes.empty.TodosEmpty
import java.sql.Date
import java.util.*

@Dao
interface TodoDao {
    @Insert
    fun insert(vararg todo: TodosEmpty)

    @Delete
    fun delete(todo: TodosEmpty)

    @Query("SELECT * FROM todolist order by date asc")
    fun getAll(): List<TodosEmpty>

    @Update
    fun update(vararg todo: TodosEmpty)

    @Query("SELECT * FROM todolist where date > :date")
    fun getbyDateAbrove(
        date:Calendar
    ): List<TodosEmpty>

    //typeId
    @Query("SELECT * FROM todolist where typeId = :typeId")
    fun getbyTypeId(
        typeId:Int
    ): List<TodosEmpty>

    @Query("SELECT * FROM todolist where id = :id")
    fun getbyId(
        id:Int
    ): TodosEmpty

}