package com.example.navigation

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.collections.ArrayList


val list = mutableListOf<Proditems>()
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    val displaylist = ArrayList<Proditems>()
    private var listState: Parcelable? = null
    private var list1: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        val test = intent.getStringExtra("name")


        if (test != null) {
            val exampleList = createalist()

            displaylist += exampleList
            displaylist.sortBy { it.name }
            recyclerview.adapter = MyListAdapter(displaylist)
            recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerview.setHasFixedSize(true)
        }

        list1?.layoutManager?.onRestoreInstanceState(listState)


        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //Toast.makeText(this@ListActivity, "on Move", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Toast.makeText(this@ListActivity, "on Swiped ", Toast.LENGTH_SHORT).show()
                //Remove swiped item from list and notify the RecyclerView
                val position = viewHolder.adapterPosition
                displaylist.removeAt(position)
                list.removeAt(position)
                recyclerview.adapter!!.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview)




        //attaching the toolbar to the layout
        toolbar = findViewById(R.id.toolbar)
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        //tv_current_time.text = Date().toString()

    }


    private fun createalist(): List<Proditems> {
        val place= intent.getIntExtra("popo", 0)
        val sel = intent.getStringExtra("itemcheck")


       var drawable = intent.getParcelableExtra("photo") as Bitmap?
        if(drawable == null) {
            //drawable = R.color.colorPrimary as Bitmap?
            drawable = Bitmap.createBitmap(50, 50,Bitmap.Config.ARGB_8888)
            drawable.eraseColor(Color.GREEN)

        }
        //val intent = intent
        val name = intent.getStringExtra("name")
        val quantity = intent.getStringExtra("Quantity")
        val prod = name.toString()
        val item = Proditems(drawable, prod, quantity + "x")
        if(sel == "true"){
            //list.add(place, item)
            list[place] = item
        }
        else {
            list += item
        }
        return list
    }


    // create action when a button is pressed
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        // action with ID action_refresh was selected
        if (id == R.id.nav_addproduct) {
            val intent = Intent(this, ProductActivity::class.java)
            this.startActivity(intent)
            //Toast.makeText(this, "app product clicked", Toast.LENGTH_SHORT).show()
            // start new activity
            return true
        }
        if (id == R.id.nav_shop) {
            val intent = Intent(this, ShoppingList::class.java)
            this.startActivity(intent)
            //Toast.makeText(this, "shopping list clicked", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbares, menu)
        val searchItem = menu.findItem(R.id.search)
        //val searchItem =  menu!!.findItem(R.id.search_button)

        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0!!.isNotEmpty()) {
                        displaylist.clear()
                        val search = p0.toLowerCase(Locale.getDefault())
                        list.forEach {
                            if (it.name.toLowerCase(Locale.getDefault()).contains(search)) {
                                displaylist.add(it)
                            }
                        }
                        recyclerview.adapter!!.notifyDataSetChanged()
                    } else {
                        displaylist.clear()
                        list.sortBy { it.name }
                        displaylist.addAll(list)
                        recyclerview.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

}

