package com.rully.inventoryapp.helper

import android.content.Context
import androidx.lifecycle.LiveData
import com.rully.inventoryapp.data.Inventory
import com.rully.inventoryapp.data.InventoryDao
import com.rully.inventoryapp.data.InventoryDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DatabaseHelper(context: Context) {

    private val inventoryDao: InventoryDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = InventoryDatabase.getDatabase(context)
        inventoryDao = db.inventoryDao()
    }

    fun getAll(): LiveData<List<Inventory>> = inventoryDao.getAllInventory()

    fun insert(inventory: Inventory) {
        executorService.execute { inventoryDao.insert(inventory) }
    }

    fun delete(inventory: Inventory) {
        executorService.execute { inventoryDao.delete(inventory) }
    }

    fun update(inventory: Inventory) {
        executorService.execute { inventoryDao.update(inventory) }
    }


}