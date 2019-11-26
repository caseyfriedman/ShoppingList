package com.example.assignment2.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.assignment2.R
import com.example.assignment2.ScrollingActivity
import com.example.assignment2.data.Item
import kotlinx.android.synthetic.main.new_item_dialog.view.*

class ItemDialog : DialogFragment(), AdapterView.OnItemSelectedListener {


    interface ItemHandler {
        fun itemCreated(item: Item)
        fun itemUpdated(item: Item)
    }

    private lateinit var itemHandler: ItemHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ItemHandler) {
            itemHandler = context
        } else {
            throw RuntimeException(
                "This activity does not implement the item handler interface"
            )
        }
    }

    private lateinit var etItemTitle: EditText
    private lateinit var etItemCost: EditText
    private lateinit var etItemDescription: EditText
    private var spinner: Int = -1

    var isEditMode = false

    var wasChecked = false


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.new_item))

        val rootView = requireActivity().layoutInflater.inflate(R.layout.new_item_dialog, null)


        etItemTitle = rootView.etTitle
        etItemCost = rootView.etCost
        etItemDescription = rootView.etDescription
        builder.setView(rootView)

        isEditMode = ((arguments != null && arguments!!.containsKey(ScrollingActivity.KEY_ITEM)))

        if (isEditMode) {
            builder.setTitle(getString(R.string.Edit_item))
            var item: Item = (arguments?.getSerializable(ScrollingActivity.KEY_ITEM) as Item)

            etItemDescription.setText(item.description)
            etItemTitle.setText(item.name)
            etItemCost.setText(item.estPrice)
            wasChecked = item.status
        }



        builder.setPositiveButton(getString(R.string.add)) { dialog, witch ->
        }


        val spinner: Spinner = rootView.findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            context as ScrollingActivity,
            R.array.spinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = adapter
        }

        return builder.create()


    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etItemTitle.text.isNotEmpty() && etItemCost.text.isNotEmpty()
                && etItemDescription.text.isNotEmpty()
            ) {
                if (isEditMode) {
                    handleItemEdit()
                } else {
                    handleItemCreate()
                }
                (dialog as AlertDialog).dismiss()
            } else {
                etItemTitle.error = "This field can not be empty"
            }


        }
    }

    private fun handleItemCreate() {
        itemHandler.itemCreated(
            Item(
                null,
                spinner,
                etItemTitle.text.toString(),
                etItemDescription.text.toString(),
                etItemCost.text.toString(),
                wasChecked

            )
        )
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var item = parent?.getItemAtPosition(position).toString()

        when (item) {
            "Food" -> spinner = 0
            "Drink" -> spinner = 1
            "Other" -> spinner = 2
        }


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun handleItemEdit() {
        val itemToEdit = arguments?.getSerializable(ScrollingActivity.KEY_ITEM) as Item

        itemToEdit.estPrice = etItemCost.text.toString()
        itemToEdit.name = etItemTitle.text.toString()
        itemToEdit.description = etItemDescription.text.toString()
        itemToEdit.category = spinner
        itemToEdit.status = wasChecked

        itemHandler.itemUpdated(itemToEdit)


    }

}
