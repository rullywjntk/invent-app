package com.rully.inventoryapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rully.inventoryapp.data.Inventory
import com.rully.inventoryapp.databinding.ListItemBinding
import com.rully.inventoryapp.view.InventoryActivity

class InventoryAdapter : RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {
    private val listInventory = ArrayList<Inventory>()

    fun setList(listInventory: List<Inventory>) {
        this.listInventory.clear()
        this.listInventory.addAll(listInventory)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(inventory: Inventory) {
            with(binding) {
                tvName.text = "Nama Barang : ${inventory.name}"
                tvColor.text = "Warna : ${inventory.color}"
                tvTotal.text = "Jumlah : ${inventory.total}"
                cvInventory.setOnClickListener {
                    val intent = Intent(it.context, InventoryActivity::class.java)
                    intent.putExtra(InventoryActivity.EXTRA_INVENTORY, inventory)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listInventory[position])
    }

    override fun getItemCount(): Int {
        return listInventory.size
    }


}