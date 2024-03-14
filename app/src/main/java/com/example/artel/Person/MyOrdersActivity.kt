package com.example.artel.Person

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.artel.MainFragments.PersonActivity
import com.example.artel.R

class MyOrdersActivity : Fragment(R.layout.activity_myorders) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val baton2: Button = view.findViewById(R.id.baton)
                baton2.setOnClickListener {
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.flFragment, PersonActivity())
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

        val myDrawableId = R.drawable.olen2
        val myDrawableId2 = R.drawable.med2


        val olen2:ImageView = view.findViewById(R.id.zakaz2)
        val zakaz11:ImageView = view.findViewById(R.id.zakaz1)



        Glide.with(requireContext())
            .load(myDrawableId)
            .override(100, 100)
            .centerCrop()
            .into(olen2)

        Glide.with(requireContext())
            .load(myDrawableId2)
            .override(100, 100)
            .centerCrop()
            .into(zakaz11)

        super.onViewCreated(view, savedInstanceState)
    }

}