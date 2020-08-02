package com.example.navigation

import android.R.id.edit
import android.content.Context
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.List.of


var itemlist = arrayListOf<String>()


class ShoppingList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val list = getList()
        if(list != null){
            itemlist = list
        }


        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemlist)


        // Adding the items to the list when the add button is pressed
        add.setOnClickListener {

            itemlist.add(editText.text.toString())
            listView.adapter = adapter
            adapter.notifyDataSetChanged()
            // This is because every time when you add the item the input space or the eidt text space will be cleared
            editText.text.clear()
        }
        // Clearing all the items in the list when the clear button is pressed
        clear.setOnClickListener {

            itemlist.clear()
            adapter.notifyDataSetChanged()
        }


        // Selecting and Deleting the items from the list when the delete button is pressed
        delete.setOnClickListener {
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item)) {
                    adapter.remove(itemlist.get(item))
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()
        }



    }

    /* override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("state", itemlist)
    }*/

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
        // Save UI state changes to the savedInstanceState bundle, this bundle will be passed to onCreate if the process is killed or restarted.
        // save user current state values
        savedInstanceState.putStringArrayList("state", itemlist)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState)
        // Restore UI state from the savedInstanceState, This bundle has also been passed to onCreate.
        // Restore state members from saved instance
        val list = savedInstanceState.getStringArrayList("state")

    }


    override fun onPause() {
        // save the instance variables
        val sharedPreference = getSharedPreferences("SHOPPING_LIST", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        val gson = Gson()
        val json = gson.toJson(itemlist)//converting list to Json
        editor.clear()
        editor.putString("LIST", json)
        editor.commit()
        super.onPause()
    }



    override fun onResume() {
        super.onResume()
        val list = getList()
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun getList(): ArrayList<String> {
        val sharedPreference =  getSharedPreferences("SHOPPING_LIST",Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreference.getString("LIST", emptyList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>() {}.type//converting the json to list
        return gson.fromJson(json, type)//returning the list
    }
}