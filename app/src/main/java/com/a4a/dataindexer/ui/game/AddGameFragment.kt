package com.a4a.dataindexer.ui.game

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import androidx.lifecycle.ViewModelProviders
import com.a4a.dataindexer.R
import com.a4a.dataindexer.data.Game
import com.a4a.dataindexer.utilities.InjectorUtils
import kotlinx.android.synthetic.main.fragment_add_game.view.*
import kotlinx.android.synthetic.main.fragment_add_game.*


class AddGameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_add_game, container, false)

        view.button_save_game.setOnClickListener { saveGame() }
        view.button_cancel_game.setOnClickListener { cancelGame() }

        return view
        //initialize UI
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        (activity as AppCompatActivity).title = "Add Game"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                saveGame()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun saveGame() {
        val title: String = edit_text_title.text.toString()
        var genre: String = edit_text_genre.text.toString()
        val size: Double = edit_text_size.text.toString().toDouble()
        if (title.trim().isEmpty()) {
            Toast.makeText(activity, "Please insert a title!", Toast.LENGTH_SHORT).show()
            return
        }
        if (genre.trim().isEmpty()) genre = "other"

        // Get the GameViewModelFactory with all of it's dependencies constructed
        val factory = InjectorUtils.provideGameViewModelFactory(activity as Context)
        // Use ViewModelProviders class to create / get already created GameViewModel for this view (activity)
        val viewModel = ViewModelProviders.of(this, factory)
            .get(GameViewModel::class.java)

        val game: Game = Game(name = title, genre = genre, size = size, rate = 0.0)
        viewModel.insert(game)

        cancelGame()
    }

    private fun cancelGame() {
        activity!!.supportFragmentManager.transaction {
            replace(R.id.fragment_holder, ListGameFragment())
        }
    }
}
