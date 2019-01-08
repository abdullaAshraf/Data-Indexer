package com.a4a.dataindexer.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.transaction
import com.a4a.dataindexer.R
import com.a4a.dataindexer.ui.disk.ListDiskFragment
import com.a4a.dataindexer.ui.game.ListGameFragment
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.theartofdev.edmodo.cropper.CropImage
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.AccountHeaderBuilder


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        initializeUi()
        buildDrawer()
    }

    private fun initializeUi() {
        supportFragmentManager.transaction { replace(R.id.fragment_holder, ListGameFragment()) }
    }

    private fun buildDrawer() {

        // Create the AccountHeader
        val headerResult = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                ProfileDrawerItem().withName("Abdulla Ashraf").withEmail("aabdulla.ashraf@gmail.com").withIcon(
                    resources.getDrawable(
                        R.drawable.profile
                    )
                )
            )
            .withOnAccountHeaderListener { view, profile, currentProfile -> false }
            .build()

        val simpleHeader = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.header)
            .withOnAccountHeaderListener { view, profile, currentProfile -> false }
            .build()

        //if you want to update the items at a later time it is recommended to keep it in a variable
        val item1 = SecondaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_disk).withIcon(FontAwesome.Icon.faw_archive)
        val item2 = SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_game).withIcon(FontAwesome.Icon.faw_gamepad)
        val item3 = SecondaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_movie).withIcon(FontAwesome.Icon.faw_video)
        val item4 = SecondaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_song).withIcon(FontAwesome.Icon.faw_music)
        val item5 = SecondaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_book).withIcon(FontAwesome.Icon.faw_book)
        val item6 = SecondaryDrawerItem().withIdentifier(6).withName(R.string.drawer_item_course).withIcon(FontAwesome.Icon.faw_graduation_cap)
        val item7 = SecondaryDrawerItem().withIdentifier(7).withName(R.string.drawer_item_program).withIcon(FontAwesome.Icon.faw_download)
        val item8 = SecondaryDrawerItem().withIdentifier(8).withName(R.string.drawer_item_other).withIcon(FontAwesome.Icon.faw_star)

        //create the drawer and remember the `Drawer` result object
        val result = DrawerBuilder()
            .withActivity(this)
            .withAccountHeader(simpleHeader)
            .withTranslucentStatusBar(false)
            .addDrawerItems(
                item1,
                DividerDrawerItem(),
                item2,
                item3,
                item4,
                item5,
                item6,
                item7,
                item8
            )
            .withOnDrawerItemClickListener { view, position, drawerItem ->
                when (drawerItem.identifier) {
                    2.toLong() -> supportFragmentManager.transaction {
                        replace(
                            R.id.fragment_holder,
                            ListGameFragment()
                        )
                    }
                    1.toLong() -> supportFragmentManager.transaction {
                        replace(
                            R.id.fragment_holder,
                            ListDiskFragment()
                        )
                    }
                    else -> Toast.makeText(this,"This feature will be added soon",Toast.LENGTH_SHORT).show()
                }
                true
            }
            .build()
        result.setSelection(2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val requestCode = requestCode and 0x0000ffff
        //RESULT FROM CROPING ACTIVITY
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_holder)
            fragment!!.onActivityResult(requestCode, resultCode, data)
        }
    }
}
