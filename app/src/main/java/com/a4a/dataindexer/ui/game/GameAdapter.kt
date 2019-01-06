package com.a4a.dataindexer.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a4a.dataindexer.R
import com.a4a.dataindexer.data.Game
import kotlinx.android.synthetic.main.game_item.view.*

class GameAdapter(private var gamesList: List<Game> = arrayListOf()): RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
         return gamesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(gamesList[position])
    }

    fun setList(games:List<Game>){
        gamesList = games
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView (game: Game) {
            itemView.text_view_name.text = game.name
            itemView.text_view_genre.text = game.genre
            itemView.text_view_size.text = game.getSizeasString()
        }
    }
}