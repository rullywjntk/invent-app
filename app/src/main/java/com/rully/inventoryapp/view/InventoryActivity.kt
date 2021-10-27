package com.rully.inventoryapp.view

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.rully.inventoryapp.R
import com.rully.inventoryapp.data.Inventory
import com.rully.inventoryapp.databinding.ActivityInventoryBinding
import com.rully.inventoryapp.helper.DatabaseHelper

class InventoryActivity : AppCompatActivity() {

    private var isEdit = false
    private var inventory: Inventory? = null
    private var _activityInventoryBinding: ActivityInventoryBinding? = null
    private val binding get() = _activityInventoryBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityInventoryBinding = ActivityInventoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        databaseHelper = DatabaseHelper(this)

        inventory = intent.getParcelableExtra(EXTRA_INVENTORY)
        if (inventory != null) {
            isEdit = true
        } else {
            inventory = Inventory()
        }

        val actionBarTitle: String
        val btnTitle: String
        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.change)
            if (inventory != null) {
                inventory?.let { inventory ->
                    binding?.etName?.setText(inventory.name)
                    binding?.etColor?.setText(inventory.color)
                    binding?.etTotal?.setText(inventory.total.toString())
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.btnSubmit?.text = btnTitle

        binding?.btnSubmit?.setOnClickListener {
            val name = binding?.etName?.text.toString().trim()
            val color = binding?.etColor?.text.toString().trim()
            val total = binding?.etTotal?.text.toString().trim()
            when {
                name.isEmpty() -> {
                    binding?.etName?.error = FIELD_REQUIRED
                }
                color.isEmpty() -> {
                    binding?.etColor?.error = FIELD_REQUIRED
                }
                total.isEmpty() -> {
                    binding?.etTotal?.error = FIELD_REQUIRED
                }
                else -> {
                    inventory.let { inventory ->
                        inventory?.name = name
                        inventory?.color = color
                        inventory?.total = total.toInt()
                    }
                    if (isEdit) {
                        databaseHelper.update(inventory as Inventory)
                        showToast(getString(R.string.changed))
                    } else {
                        databaseHelper.insert(inventory as Inventory)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionDelete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.msg_cancel)
        } else {
            dialogMessage = getString(R.string.msg_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    databaseHelper.delete(inventory as Inventory)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _activityInventoryBinding = null
    }

    companion object {
        const val EXTRA_INVENTORY = "extra_inventory"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20

        const val FIELD_REQUIRED = "Field is required"

    }
}