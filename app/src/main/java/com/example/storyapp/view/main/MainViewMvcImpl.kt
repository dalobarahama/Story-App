package com.example.storyapp.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.storyapp.R
import com.example.storyapp.view.addstory.AddStoryFragment
import com.example.storyapp.view.storyinmap.StoryInMapFragment
import com.example.storyapp.view.storylist.StoryListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainViewMvcImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) : MainViewMvc {

    interface Listener {
        fun loadFragment(fragment: Fragment, container: Int)
        fun onPositiveDialogPressed()
    }

    private var listeners = HashSet<Listener>()

    private val rootView = layoutInflater.inflate(R.layout.activity_main, parent, false)
    private val container = R.id.fragment_container

    init {
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

    override fun getRootView(): View {
        return rootView
    }

    override fun <T : View?> findViewById(id: Int): T {
        return getRootView().findViewById(id)
    }

    override fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    override fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
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

    private fun getListener(): Listener? {
        var listener: Listener? = null
        for (item in listeners) {
            listener = item
        }
        return listener
    }
}