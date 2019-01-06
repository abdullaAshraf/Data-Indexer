package com.a4a.dataindexer.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.a4a.dataindexer.R
import com.a4a.dataindexer.data.Game
import kotlinx.android.synthetic.main.game_item.view.*

class GameAdapter :ListAdapter<Game,GameAdapter.ViewHolder>(DiffCallback()){
    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION)
                listener.onItemClick(getItem(position))
        }
    }

    fun getGameAt(position: Int): Game {
        return getItem(position)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(game: Game) {
            itemView.text_view_name.text = game.name
            itemView.text_view_genre.text = game.genre
            itemView.text_view_size.text = game.getSizeAsString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(game: Game)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}

class DiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}