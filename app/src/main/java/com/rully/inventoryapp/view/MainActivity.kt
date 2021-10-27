package com.rully.inventoryapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rully.inventoryapp.R
import com.rully.inventoryapp.adapter.InventoryAdapter
import com.rully.inventoryapp.databinding.ActivityMainBinding
import com.rully.inventoryapp.helper.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var adapter: InventoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val databaseHelper = DatabaseHelper(this)
        databaseHelper.getAll().observe(this, { listInventory ->
            if (listInventory != null) {
                adapter.setList(listInventory)
            }
        })

        adapter = InventoryAdapter()

        binding?.rvInventory?.layoutManager = LinearLayoutManager(this)
        binding?.rvInventory?.setHasFixedSize(true)
        binding?.rvInventory?.adapter = adapter

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fabAdd) {
                val intent = Intent(this@MainActivity, InventoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}