package com.example.customlistview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(context: Context, personList: MutableList<Product>):
    ArrayAdapter<Product>(context, R.layout.list_item, personList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val product = getItem(position)
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent,false)
        }
        val imageViewIV = view?.findViewById<ImageView>(R.id.imageViewIV)
        val nameTV = view?.findViewById<TextView>(R.id.nameTV)
        val costTV = view?.findViewById<TextView>(R.id.costTV)

        imageViewIV?.setImageBitmap(product?.image)
        nameTV?.text = product?.name
        costTV?.text = product?.cost
        return view!!
    }
}