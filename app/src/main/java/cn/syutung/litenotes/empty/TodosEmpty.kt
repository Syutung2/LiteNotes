package cn.syutung.litenotes.empty

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "todolist",
)
data class TodosEmpty(
    var text: String,
    var date: Calendar,
    var typeId:Int,
    var cycleType:Int,
    var cycleTime:Int,
){

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

}