package com.rully.inventoryapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(inventory: Inventory)

    @Update
    fun update(inventory: Inventory)

    @Delete
    fun delete(inventory: Inventory)

    @Query("SELECT * FROM inventory ORDER BY id ASC")
    fun getAllInventory(): LiveData<List<Inventory>>
}