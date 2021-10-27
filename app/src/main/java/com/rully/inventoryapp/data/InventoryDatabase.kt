package com.rully.inventoryapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Inventory::class], version = 1)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun inventoryDao() : InventoryDao

    companion object {
        @Volatile
        private var INSTANCE: InventoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) : InventoryDatabase {
            if (INSTANCE == null) {
                synchronized(InventoryDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, InventoryDatabase::class.java, "inventory_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as InventoryDatabase
        }
    }
}