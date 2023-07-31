package com.example.orthodoxy_in_ukraine.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.orthodoxy_in_ukraine.data.db.model.EventEntity

@Dao
interface IEventsDao {

    /**
     * Method for getting all exents
     */
    @Query("SELECT * FROM Events")
    fun fetchEvents(): List<EventEntity>
}