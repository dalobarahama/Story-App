package com.example.storyapp.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.storyapp.R
import com.example.storyapp.view.addstory.AddStoryFragment
import com.example.storyapp.view.common.BaseViewMvcImpl
import com.example.storyapp.view.storyinmap.StoryInMapFragment
import com.example.storyapp.view.storylist.StoryListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainViewMvcImpl(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : MainViewMvc, BaseViewMvcImpl<MainViewMvc.Listener>() {

    private val container = R.id.fragment_container

    init {
        setRootView(layoutInflater.inflate(R.layout.activity_main, parent, false))

        val bnv = findViewById<BottomNavigationView>(R.id.bnv)
        bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu -> {
                    getListener()?.loadFragment(StoryListFragment(), container)
                    return@setOnItemSelectedListener true
                }

                R.id.add_menu -> {
                    getListener()?.loadFragment(AddStoryFragment(), container)
                    return@setOnItemSelectedListener true
                }

                R.id.story_in_map_menu -> {
                    getListener()?.loadFragment(StoryInMapFragment(), container)
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }
        }
    }

    override fun showAlertDialog() {
        AlertDialog.Builder(getRootView().context)
            .setMessage("Exit app?")
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Yes") { _, _ ->
                getListener()?.onPositiveDialogPressed()
            }
            .create().show()
    }

    override fun setDefaultFragment() {
        getListener()?.loadFragment(StoryListFragment(), container)
    }
}