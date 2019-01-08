package com.a4a.dataindexer.ui.disk

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.lifecycle.ViewModelProviders
import com.a4a.dataindexer.R
import com.a4a.dataindexer.data.Disk
import com.a4a.dataindexer.utilities.InjectorUtils
import kotlinx.android.synthetic.main.fragment_add_disk.*
import kotlinx.android.synthetic.main.fragment_add_disk.view.*
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import androidx.core.content.ContextCompat.getSystemService


class AddEditDiskFragment : Fragment() {
    private var editDisk: Disk? = null
    private lateinit var viewModel: DiskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_add_disk, container, false)


        // Get the GameViewModelFactory with all of it's dependencies constructed
        val factory = InjectorUtils.provideDiskViewModelFactory(activity as Context)
        // Use ViewModelProviders class to create / get already created GameViewModel for this view (activity)
        viewModel = ViewModelProviders.of(this, factory)
            .get(DiskViewModel::class.java)


        view.button_save_disk.setOnClickListener { saveDisk() }
        view.button_cancel_disk.setOnClickListener { cancelDisk() }

        if (editDisk != null) {
            view.edit_text_title.setText(editDisk!!.name)
            view.edit_text_desc.setText(editDisk!!.desc)
            view.edit_text_size.setText(editDisk!!.size.toString())
        }
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        if (editDisk == null)
            (activity as AppCompatActivity).title = "Add Disk"
        else
            (activity as AppCompatActivity).title = "Edit Disk"
    }


    fun setFields(disk: Disk) {
        editDisk = disk
    }

    private fun saveDisk() {
        val title: String = edit_text_title.text.toString()
        var desc: String = edit_text_desc.text.toString()
        var size: String = edit_text_size.text.toString()
        if (title.trim().isEmpty()) {
            Toast.makeText(activity, "Please insertGame a title!", Toast.LENGTH_SHORT).show()
            return
        }
        if (desc.trim().isEmpty()) desc = "None"
        if (size.trim().isEmpty()) size = "0.0"

        if (editDisk == null) {
            val disk = Disk(
                name = title,
                size = size.toDouble(),
                desc = desc
            )
            viewModel.insert(disk)
        } else {
            val disk = Disk(
                id = editDisk!!.id,
                name = title,
                size = size.toDouble(),
                desc = desc
            )
            viewModel.update(disk)
        }

        cancelDisk()
    }

    private fun cancelDisk() {
        val inputManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity!!.currentFocus.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        activity!!.supportFragmentManager.transaction {
            replace(R.id.fragment_holder, ListDiskFragment())
        }
    }
}
