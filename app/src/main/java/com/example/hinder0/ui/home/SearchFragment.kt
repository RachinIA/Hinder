package com.example.hinder0.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.hinder0.Filters
import com.example.hinder0.MapsActivity
import com.example.hinder0.R


class SearchFragment : Fragment(), View.OnClickListener {

    private lateinit var filtersbutton : Button
    private lateinit var mapsbutton : Button
    private lateinit var alladvbutton : Button
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        filtersbutton = root.findViewById(R.id.button)
        filtersbutton.setOnClickListener(this)
        alladvbutton = root.findViewById(R.id.alladv_button)
        alladvbutton.setOnClickListener(this)
        mapsbutton = root.findViewById(R.id.button2)
        mapsbutton.setOnClickListener(this)
        return root
    }

    override fun onClick(view: View) {
        if (view === mapsbutton) {
            // Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
            startActivity(Intent(activity, MapsActivity::class.java))
        }
        if (view === filtersbutton) {
            // Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
            startActivity(Intent(activity, Filters::class.java))
        }
        if (view === alladvbutton) {
            // Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
            startActivity(Intent(activity, Alladv::class.java))
        }
    }
}
