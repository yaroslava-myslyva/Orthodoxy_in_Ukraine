package com.example.orthodoxy_in_ukraine.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.orthodoxy_in_ukraine.presentation.calendar.Holyday

@Entity(tableName = "Events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id: Int? = null,
    @ColumnInfo(name = "Name") val name: String?,
    @ColumnInfo(name = "Date") val date: Int?,
    @ColumnInfo(name = "Month") val month: Int?,
    @ColumnInfo(name = "Size of holyday") val sizeOfHolyday: Int?,
    @ColumnInfo(name = "Is big holyday") val isBigHolyday: Int?,
    @ColumnInfo(name = "Description") val description: String?
){
    fun mapToHolyday() :Holyday = Holyday(
        name = name ?: "",
        date = date ?: 0,
        month = month ?: 0,
        sizeOfHolyday = sizeOfHolyday ?: 0,
        isBigHolyday = isBigHolyday ?: 0,
        description = description ?: ""
    )
}

