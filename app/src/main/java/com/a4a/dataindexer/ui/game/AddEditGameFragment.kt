package com.a4a.dataindexer.ui.game

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.a4a.dataindexer.R
import com.a4a.dataindexer.data.Game
import com.a4a.dataindexer.data.GameAPI
import com.a4a.dataindexer.data.GameWrapper
import com.a4a.dataindexer.utilities.GlideApp
import com.a4a.dataindexer.utilities.InjectorUtils
import com.android.volley.Response
import com.bumptech.glide.load.model.GlideUrl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_add_game.*
import kotlinx.android.synthetic.main.fragment_add_game.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddEditGameFragment : Fragment() {
    private val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    private var editGame: Game? = null
    private lateinit var listItems: List<String>
    private var checkedItems = ArrayList<Boolean>(0)
    private var userItems = ArrayList<Int>(0)
    private lateinit var viewModel: GameViewModel
    private var disksIds = ArrayList<Long>(0)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_add_game, container, false)

        listItems = activity!!.resources.getStringArray(R.array.genre_item).toList()
        for (i in 0..listItems.size)
            checkedItems.add(false)

        // Get the GameViewModelFactory with all of it's dependencies constructed
        val factory = InjectorUtils.provideGameViewModelFactory(activity as Context)
        // Use ViewModelProviders class to create / get already created GameViewModel for this view (activity)
        viewModel = ViewModelProviders.of(this, factory)
            .get(GameViewModel::class.java)

        /*
           val presenter = SimplePresenter(context!!,this)
           val policy = SimplePolicy ()
           val callback = SimpleCallBack()

           Autocomplete.on<String>(view.edit_text_title)
               .with(callback)
               .with(policy)
               .with(presenter)
               .build()
        */


        view.edit_text_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(query: Editable?) {
                if (query.isNullOrEmpty() or (query!!.length < 3))
                    return
                viewModel.searchByName(query.toString(), Response.Listener { response ->
                    Log.d("MyTag", "Response is: $response")
                    val turnsType = object : TypeToken<List<GameWrapper>>() {}.type
                    val gamesWrappers = Gson().fromJson<List<GameWrapper>>(response, turnsType)
                    var titlesList = ArrayList<String>(0)
                    for (gamew in gamesWrappers) {
                        Log.d("MyTag", "name is: " + gamew.game.name)
                        titlesList.add(gamew.game.name)
                    }
                    val titleAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_item, titlesList)
                    view.edit_text_title.setAdapter(titleAdapter)
                    view.edit_text_title.showDropDown()
                })

            }

            override fun beforeTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        view.edit_text_title.setOnItemClickListener { adapterView, _, i, l ->
            val selected = adapterView.getItemAtPosition(i) as String
            viewModel.getByName(selected, Response.Listener { response ->
                val turnsType = object : TypeToken<List<GameAPI>>() {}.type
                val gamesArr = Gson().fromJson<List<GameAPI>>(response, turnsType)
                val game = gamesArr[0]
                view.edit_text_desc.setText(game.desc)
                view.edit_text_date.setText(game.getDateAsString())
                view.edit_text_genre.text = game.getGenreAsString()
                for (item in game.genre) {
                    val pos = listItems.indexOf(GameAPI.map[item])
                    checkedItems[pos] = true
                    userItems.add(pos)
                }
                view.edit_text_rate.setText(game.rate.toString())
                Log.d("MyTag", "cover id is: " + game.cover)
                if (game.cover > 0)
                    viewModel.getCover(game.cover, Response.Listener { response ->
                        Log.d("MyTag", "Response is: $response")
                        val turnsType = object : TypeToken<List<HashMap<String, String>>>() {}.type
                        val asMap = Gson().fromJson<List<HashMap<String, String>>>(response, turnsType)[0]
                        asMap["url"] = asMap["url"]!!.replace("t_thumb", "t_cover_big")
                        Log.d("MyTag", "url is: " + asMap["url"])
                        val glideUrl = GlideUrl("https:" + asMap["url"])
                        GlideApp.with(this)
                            .load(glideUrl)
                            .placeholder(R.drawable.no_cover)
                            .error(R.drawable.no_cover)
                            .fitCenter()
                            .into(view.add_game_cover)
                    })

            })
        }


        view.button_save_game.setOnClickListener { saveGame() }
        view.button_cancel_game.setOnClickListener { cancelGame() }
        view.edit_text_date.setText(format.format(Calendar.getInstance().time))

        view.add_game_cover.setOnClickListener { pickImage() }

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewModel.getDisks().observe(this, Observer { disks ->
            adapter.clear()
            var diskNames = ArrayList<String>(0)

            //set default option
            diskNames.add("Wish List")
            disksIds.add(0)

            for (disk in disks) {
                diskNames.add(disk.name)
                disksIds.add(disk.id)
            }
            adapter.addAll(ArrayList(diskNames) as MutableCollection<out CharSequence>)
        })
        view.edit_text_disk.adapter = adapter
        view.edit_text_disk.setSelection(0)


        view.edit_text_date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                activity,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day -> view.edit_text_date.setText((day.toString() + "/" + (month + 1) + "/" + year)) },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        view.button_genre_dialog.setOnClickListener {
            val mBuilder: AlertDialog.Builder = AlertDialog.Builder(activity as Context)
            mBuilder.setTitle("Game Genres Available")
            mBuilder.setMultiChoiceItems(
                listItems.toTypedArray(),
                checkedItems.toBooleanArray()
            ) { dialogInterface, position, isChecked ->
                if (isChecked) {
                    if (!userItems.contains(position))
                        userItems.add(position)
                    else
                        userItems.remove(position)
                }
            }
            mBuilder.setCancelable(false)
            mBuilder.setPositiveButton("Done") { dialogInterface, i ->
                var genres = ""
                checkedItems.fill(false)
                for ((pos, item) in userItems.withIndex()) {
                    genres += listItems[item] + if (pos == userItems.size - 1) "" else ", "
                    checkedItems[item] = true
                }
                edit_text_genre.text = genres
            }
            mBuilder.setNegativeButton("Dismiss") { dialogInterface, i ->
                dialogInterface.dismiss()
            }

            mBuilder.setNeutralButton("Clear All") { dialogInterface, i ->
                checkedItems.fill(false)
                userItems.clear()
                edit_text_genre.text = ""
            }

            val mDialog: AlertDialog = mBuilder.create()
            mDialog.show()
        }

        view.edit_size_unit.setSelection(1)

        if (editGame != null) {
            view.edit_text_title.setText(editGame!!.name)
            view.edit_text_desc.setText(editGame!!.desc)
            view.edit_text_date.setText(editGame!!.getDateAsString())
            view.edit_text_genre.text = editGame!!.getGenreAsString()
            for (item in editGame!!.genre) {
                val pos = listItems.indexOf(item)
                checkedItems[pos] = true
                userItems.add(pos)
            }
            view.edit_text_size.setText(editGame!!.size.toString())
            view.edit_text_disk.setSelection(disksIds.indexOf(editGame!!.disk))
            view.edit_text_rate.setText(editGame!!.rate.toString())
            view.add_game_cover.setImageDrawable(editGame!!.byteArrayToImage())
        }

        return view
    }


    //PICK IMAGE METHOD
    private fun pickImage() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(136, 182)
            .setMultiTouchEnabled(true)
            .start(context!!, this)
    }

    fun imageToByteArray(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        if (editGame == null)
            (activity as AppCompatActivity).title = "Add Game"
        else
            (activity as AppCompatActivity).title = "Edit Game"
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

    fun setFields(game: Game) {
        editGame = game
    }

    private fun saveGame() {
        val title: String = edit_text_title.text.toString()
        var desc: String = edit_text_desc.text.toString()
        var sizes: String = edit_text_size.text.toString()
        var rate: String = edit_text_rate.text.toString()
        var disk: Long = disksIds[edit_text_disk.selectedItemPosition]
        val date: String = edit_text_date.text.toString()
        if (title.trim().isEmpty()) {
            Toast.makeText(activity, "Please insertGame a title!", Toast.LENGTH_SHORT).show()
            return
        }
        if (desc.trim().isEmpty()) desc = "None"
        if (sizes.trim().isEmpty()) sizes = "0.0"
        if (rate.trim().isEmpty()) rate = "0"


        var size = sizes.toDouble()
        if(edit_size_unit.selectedItemPosition == 0)
            size /= 1024
        if(edit_size_unit.selectedItemPosition == 2)
            size *= 1024

        val genres: ArrayList<String> = ArrayList(0)
        for (item in userItems)
            genres.add(listItems[item])

        val day = format.parse(date)

        if (editGame == null) {
            val game = Game(
                name = title,
                genre = genres,
                size = size,
                rate = rate.toDouble(),
                cover = imageToByteArray(add_game_cover),
                date = day,
                disk = disk,
                desc = desc
            )
            viewModel.insert(game)
        } else {
            val game = Game(
                id = editGame!!.id,
                name = title,
                genre = genres,
                size = size,
                rate = rate.toDouble(),
                cover = imageToByteArray(add_game_cover),
                date = day,
                disk = disk,
                desc = desc
            )
            viewModel.update(game)
        }

        cancelGame()
    }

    private fun cancelGame() {
        val inputManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        activity!!.supportFragmentManager.transaction {
            replace(R.id.fragment_holder, ListGameFragment())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //RESULT FROM CROPING ACTIVITY
        if ((requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) and (CropImage.getActivityResult(data) != null)) {
            val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                try {
                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, result.uri)

                    add_game_cover.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
