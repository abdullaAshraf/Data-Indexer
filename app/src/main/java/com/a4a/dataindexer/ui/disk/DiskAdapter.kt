package com.a4a.dataindexer.ui.disk

import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a4a.dataindexer.R
import com.a4a.dataindexer.data.Disk
import kotlinx.android.synthetic.main.game_item.view.*
import kotlin.math.max
import kotlin.math.min

class DiskAdapter (viewModel: DiskViewModel , owner: LifecycleOwner): ListAdapter<Disk, DiskAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var listener: OnItemClickListener
    private var viewModel: DiskViewModel = viewModel
    private var owner: LifecycleOwner = owner


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.disk_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION)
                listener.onItemClick(getItem(position))
        }
        viewModel.getDiskSize(getItem(position).id).observe(owner, Observer { value ->
            val percent = min(100.0,value / getItem(position).size * 100)

            holder.itemView.progressBar.progress = percent.toInt()
            holder.itemView.txtProgress.text = (percent.toInt().toString() + "%")
        })
    }

    fun getDiskAt(position: Int): Disk {
        return getItem(position)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(disk: Disk) {
            itemView.text_view_name.text = disk.name
            itemView.text_view_size.text = disk.getSizeAsString()
            itemView.text_view_desc.text = disk.desc
        }
    }

    interface OnItemClickListener {
        fun onItemClick(disk: Disk)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}

class DiffCallback : DiffUtil.ItemCallback<Disk>() {
    override fun areItemsTheSame(oldItem: Disk, newItem: Disk): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Disk, newItem: Disk): Boolean {
        return oldItem == newItem
    }
}