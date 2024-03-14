package com.example.artel.MainFragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.artel.Person.AboutActivity
import com.example.artel.Person.HelpActivity
import com.example.artel.Person.MyOrdersActivity
import com.example.artel.Person.ReviewActivity
import com.example.artel.R


class PersonActivity : Fragment(R.layout.activity_person) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val buttonReview: Button = view.findViewById(R.id.buttonReview)
        val buttonAbout: Button = view.findViewById(R.id.buttonAbout)
        val buttonHelp: Button = view.findViewById(R.id.buttonHelp)
        val buttonMyOrders: Button = view.findViewById(R.id.buttonMyOrders)



        buttonReview.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, ReviewActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonAbout.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, AboutActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonHelp.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, HelpActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonMyOrders.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, MyOrdersActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        super.onViewCreated(view, savedInstanceState)
    }



}