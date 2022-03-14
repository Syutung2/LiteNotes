package cn.syutung.litenotes.empty

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "typelist")
data class TypeEmpty (
    var text: String
){
    @PrimaryKey(autoGenerate = true)
    var  id: Int = 0
}