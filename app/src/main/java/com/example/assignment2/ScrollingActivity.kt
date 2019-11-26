package com.example.assignment2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assignment2.data.AppDatabase
import com.example.assignment2.data.Item
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.assignment2.adapter.ItemAdapter
import com.example.assignment2.data.AppDatabase.Companion.getInstance
import com.example.assignment2.dialog.DetailsDialog
import com.example.assignment2.dialog.ItemDialog


import kotlinx.android.synthetic.main.activity_scrolling.*


class ScrollingActivity : AppCompatActivity(), ItemDialog.ItemHandler {

    companion object {
        const val KEY_ITEM = "KEY_ITEM"
        const val KEY_STARTED = "KEY_STARTED"
        const val TAG_ITEM_DIALOG = "TAG_ITEM_DIALOG"
        const val TAG_ITEM_EDIT = "TAG_TODO_EDIT"
        const val TAG_ITEM_VIEW = "TAG_ITEM_VIEW"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        setSupportActionBar(toolbar)

        initRecyclerView()

        fab.setOnClickListener {
            showAddItemDialog()
        }

        fabDeleteAll.setOnClickListener {
            itemAdapter.deleteAllItems()
        }


    }


    lateinit var itemAdapter: ItemAdapter


    fun queryAllItems() {

        Thread {

        }.start()
    }


    fun saveItem(item: Item) {

        Thread {
            AppDatabase.getInstance(this@ScrollingActivity).itemDao().addItem(item)


            runOnUiThread {
                itemAdapter.addItem(item)
            }


        }.start()

    }


    private fun initRecyclerView() {
        Thread {
            var items = getInstance(this@ScrollingActivity).itemDao().getAllItems()


            runOnUiThread {
                itemAdapter = ItemAdapter(this, items)
                recyclerTodo.adapter = itemAdapter

                var itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                recyclerTodo.addItemDecoration(itemDecorator)

            }
        }.start()


    }

    fun showAddItemDialog() {
        ItemDialog().show(supportFragmentManager, TAG_ITEM_DIALOG)
    }


    override fun itemCreated(item: Item) {
        saveItem(item)
    }

    private var editIndex: Int = -1

    fun showEditItemDialog(itemToEdit: Item, idx: Int) {
        editIndex = idx

        val editDialog = ItemDialog()
        val bundle = Bundle()

        bundle.putSerializable(KEY_ITEM, itemToEdit)


        editDialog.arguments = bundle

        editDialog.show(supportFragmentManager, TAG_ITEM_VIEW)

    }


    override fun itemUpdated(item: Item) {
        Thread {
            getInstance(this@ScrollingActivity).itemDao().updateItem(item)

            runOnUiThread {
                itemAdapter.updateItemOnPosition(item, editIndex)
            }
        }.start()
    }

    fun showViewItemDialog(item: Item) {
        val bundle = Bundle()
        val viewDialog = DetailsDialog()
        bundle.putSerializable(KEY_ITEM, item)
        viewDialog.arguments = bundle
        viewDialog.show(supportFragmentManager, TAG_ITEM_VIEW)
    }


}