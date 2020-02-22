package com.rohit.mmttestapp.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rohit.mmttestapp.daos.VariantDao
import com.rohit.mmttestapp.pojo.VariantDbData

@Database(entities = [VariantDbData::class], version = 1)
abstract class VariantDatabase : RoomDatabase() {

    abstract fun variantDao(): VariantDao


    companion object {
        private var instance: VariantDatabase? = null
        fun getInstance(context: Context): VariantDatabase? {
            if (instance == null) {
                synchronized(VariantDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VariantDatabase::class.java, "variants_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }

}
