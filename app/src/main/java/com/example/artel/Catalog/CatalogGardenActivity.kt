package com.example.artel.Catalog


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.artel.MainFragments.CatalogActivity
import com.example.artel.R

class CatalogGardenActivity : Fragment(R.layout.activity_catalog_garden) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonGardenB: Button = view.findViewById(R.id.buttonBGarden)
        buttonGardenB.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        super.onViewCreated(view, savedInstanceState)
    }


}