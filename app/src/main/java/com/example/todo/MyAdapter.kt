package com.example.todo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(private val context: Context,
                private val dataSource: MutableList<ListItem>
) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }


    override fun getItem(position: Int): Any {
        return dataSource[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = inflater.inflate(R.layout.item_view, parent, false)

        val rowTitle = rowView.findViewById(R.id.RowTitle) as TextView

        val rowTime = rowView.findViewById(R.id.RowTime) as TextView

        val rowImage = rowView.findViewById(R.id.RowImg) as ImageView


        val row = getItem(position) as ListItem
        rowView.setBackgroundColor(context.getColor(R.color.colorPrimary))

        when (row.priority) {
            1 -> rowView.setBackgroundColor(context.getColor(R.color.prio1))
            2 -> rowView.setBackgroundColor(context.getColor(R.color.prio2))
            3 -> rowView.setBackgroundColor(context.getColor(R.color.prio3))
            else -> rowView.setBackgroundColor(context.getColor(R.color.colorPrimaryDark))
        }

       rowTitle.text = row.title
       rowTime.text = row.time
        rowImage.setImageResource(row.img)




        //Picasso.with(context).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView)

return rowView
    }
}


