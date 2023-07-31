package com.example.orthodoxy_in_ukraine.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.orthodoxy_in_ukraine.data.db.dao.IEventsDao
import com.example.orthodoxy_in_ukraine.data.db.model.EventEntity
import kotlinx.coroutines.CoroutineScope

@Database(
    version = 1,
    entities = [
        EventEntity::class
    ]
)
abstract class EventsDataBase : RoomDatabase() { // needs context

    abstract val IEventsDao: IEventsDao

    companion object {
        private const val DB_NAME = "Events"

        @Volatile
        private var instance: EventsDataBase? = null

        fun getDataBase(
            context: Context,
            scope: CoroutineScope
        ): EventsDataBase {
            return instance ?: synchronized(this) {

                val newDb = Room.databaseBuilder(
                    context.applicationContext,
                    EventsDataBase::class.java,
                    DB_NAME
                )
                    .createFromAsset("Events.db")
                    .build()


                instance = newDb
                newDb
            }


        }
    }
}