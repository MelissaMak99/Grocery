package com.example.navigation


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardview.view.*


class MyListAdapter(private val Exampleproduct: List<Proditems>): RecyclerView.Adapter<MyListAdapter.MyListviewHolder>(){
    //var clicked = "false"
    var lists = mutableListOf<Proditems>()

    init {
        this.lists = Exampleproduct as MutableList<Proditems>
    }

    /*fun del( position: Int){
        lists.removeAt(position)
        notifyItemRemoved(position)

        //notifyItemRemoved(position)
    }*/



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListviewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview,parent,false)
        return MyListviewHolder(itemView)
    }



    override fun getItemCount() = lists.size

    override fun onBindViewHolder(holder: MyListviewHolder, position: Int) {
        val currentItem = lists[position]
            holder.imageView.setImageBitmap(currentItem.image)
            holder.textview1.text = currentItem.name
            holder.textview2.text = currentItem.quantity
            holder.itemView.setOnClickListener(View.OnClickListener
            { view ->
                var clicked = "true"
                //val position = lists.get(holder.adapterPosition)
                var pose = position
                val context: Context = view.context
                val intent1 = Intent(context,ProductActivity::class.java)
                intent1.putExtra("position",pose)
                intent1.putExtra("itemcheck",clicked)
                context.startActivity(intent1)
            })

    }

    // class containing each element of the row layout
    inner class MyListviewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.imageview
        val textview1: TextView = itemView.textv1
        val textview2: TextView = itemView.textv2


    }




}
