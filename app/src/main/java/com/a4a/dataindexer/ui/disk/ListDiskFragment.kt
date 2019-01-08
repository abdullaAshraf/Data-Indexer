package com.a4a.dataindexer.ui.disk

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a4a.dataindexer.R
import com.a4a.dataindexer.data.Disk
import com.a4a.dataindexer.utilities.InjectorUtils
import kotlinx.android.synthetic.main.fragment_list_disk.view.*


class ListDiskFragment : Fragment() {
    lateinit var adapter: DiskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_list_disk, container, false)


        // Get the GameViewModelFactory with all of it's dependencies constructed
        val factory = InjectorUtils.provideDiskViewModelFactory(context!!)
        // Use ViewModelProviders class to create / get already created GameViewModel for this view (activity)
        val viewModel = ViewModelProviders.of(this, factory)
            .get(DiskViewModel::class.java)

        adapter = DiskAdapter(viewModel,this)

        view.recycler_view.layoutManager = LinearLayoutManager(activity)
        view.recycler_view.setHasFixedSize(true)
        view.recycler_view.adapter = adapter

        // Observing LiveData from the QuotesViewModel which in turn observes
        // LiveData from the repository, which observes LiveData from the DAO ☺
        viewModel.getDisks().observe(this, Observer { disks ->
            adapter.submitList(disks)
        })

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.delete(adapter.getDiskAt(viewHolder.adapterPosition))
                Toast.makeText(activity,"Game deleted",Toast.LENGTH_SHORT).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(view.recycler_view)

        adapter.setOnItemClickListener(object : DiskAdapter.OnItemClickListener{
            override fun onItemClick(disk: Disk) {
                val fragment = AddEditDiskFragment()
                fragment.setFields(disk)
                activity!!.supportFragmentManager.transaction{replace(R.id.fragment_holder,fragment)}
            }
        })

        view.button_add_disk.setOnClickListener{ activity!!.supportFragmentManager.transaction{replace(R.id.fragment_holder,
            AddEditDiskFragment()
        )} }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //(activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        (activity as AppCompatActivity).title = "All Disks"
    }
}
