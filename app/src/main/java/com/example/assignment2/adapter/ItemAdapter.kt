package com.example.assignment2.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.ScrollingActivity
import com.example.assignment2.R
import com.example.assignment2.data.AppDatabase
import com.example.assignment2.data.Item
import kotlinx.android.synthetic.main.list_row.view.*

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder> {


    var itemList = mutableListOf<Item>()
    var context: Context

    constructor(context: Context, items: List<Item>) {

        this.context = context

        itemList.addAll(items)



    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cbItem = itemView.cbItem
        var tvTitle = itemView.tvTitle
        var btnDelete = itemView.btnDelete
        var tvCost = itemView.tvCost
        var tvDescription = itemView.tvDescription
        var imageView = itemView.imageView
        var btnEdit = itemView.btnEdit
        var btnDetails = itemView.btnDetails
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var todoRow = LayoutInflater.from(context).inflate(
            R.layout.list_row, parent, false
        )

        return ViewHolder(todoRow)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = itemList[holder.adapterPosition]

        holder.cbItem.text = context.getString(R.string.Purchased)

        holder.cbItem.isChecked = item.status

        holder.tvTitle.text = item.name
        holder.tvCost.text = item.estPrice
        holder.tvDescription.text = item.description


        handleCategory(item.category, holder)

        holder.btnDelete.setOnClickListener {
            deleteItem(holder.adapterPosition)
        }

        holder.cbItem.setOnClickListener {

            item.status = holder.cbItem.isChecked
            updateItem(item)
        }

        holder.btnEdit.setOnClickListener {
            (context as ScrollingActivity).showEditItemDialog(
                item, holder.adapterPosition
            )
        }

        holder.btnDetails.setOnClickListener {
            (context as ScrollingActivity).showViewItemDialog(item)

        }


    }

    private fun handleCategory(mapping: Int, holder: ViewHolder) {
        when (mapping) {
            0 -> holder.imageView.setImageResource(R.drawable.food_icon)

            1 -> holder.imageView.setImageResource(R.drawable.drink_icon)

            2 -> holder.imageView.setImageResource(R.drawable.misc_icon)


        }
    }

    fun updateItem(item: Item) {
        Thread {
            AppDatabase.getInstance(context).itemDao().updateItem(item)
        }.start()
    }

    fun deleteItem(index: Int) {
        Thread {
            AppDatabase.getInstance(context).itemDao().deleteItem(itemList[index])

            (context as ScrollingActivity).runOnUiThread {
                itemList.removeAt(index)
                notifyItemRemoved(index)
            }
        }.start()


    }

    fun addItem(item: Item) {
        itemList.add(item)
        notifyItemInserted(itemList.lastIndex)
    }


    fun deleteAllItems() {
        Thread {
            AppDatabase.getInstance(context).itemDao().deleteAllItems()
            (context as ScrollingActivity).runOnUiThread {
                itemList.clear()
                notifyDataSetChanged()
            }
        }.start()
    }


    fun updateItemOnPosition(item: Item, index: Int) {
        itemList[index] = item
        notifyItemChanged(index)
    }


}

