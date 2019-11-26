package com.example.assignment2.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.assignment2.R
import com.example.assignment2.ScrollingActivity
import com.example.assignment2.data.Item
import kotlinx.android.synthetic.main.details_dialog.view.*

class DetailsDialog : DialogFragment() {



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {



        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Details")
        val rootView = requireActivity().layoutInflater.inflate(R.layout.details_dialog, null)
        var item: Item = (arguments?.getSerializable(ScrollingActivity.KEY_ITEM) as Item)



        builder.setView(rootView)


        rootView.tvName.text = item.name
        rootView.tvCategory.text = categoryMap(item.category)
        rootView.tvCost.text = item.estPrice
        rootView.tvDescription.text = item.description

        builder.setPositiveButton("Edit") { dialog, witch ->
        }

        return builder.create()

    }



    private fun categoryMap(category: Int): String {
        var cat: String = "Food"
        when (category) {
            0 -> cat = "Food"
            1 -> cat = "Drink"
            2 -> cat = "Other"

        }
        return cat
    }
}