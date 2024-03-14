package com.example.artel.MainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.artel.Catalog.*
import com.example.artel.R

class CatalogActivity : Fragment(R.layout.activity_catalog) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.activity_catalog, container,false)



        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val buttonProduct: Button = view.findViewById(R.id.product)
        val buttonBird: Button = view.findViewById(R.id.bird)
        val buttonKolhoz: Button = view.findViewById(R.id.kolhoz)
        val buttonHandmade: Button = view.findViewById(R.id.handmade)
        val buttonGarden: Button = view.findViewById(R.id.garden)
        val buttonPresent: Button = view.findViewById(R.id.present)



        buttonProduct.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogProductActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonBird.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogBirdActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonKolhoz.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogKolhozActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonHandmade.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogHandmadeActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonGarden.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogGardenActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonPresent.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogPresentActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }



        /*val buttonBirdB: Button = view.findViewById(R.id.buttonBBird)
        val buttonKolhozB: Button = view.findViewById(R.id.buttonBKolhoz)
        val buttonHandmadeB: Button = view.findViewById(R.id.buttonBHandmade)
        val buttonGardenB: Button = view.findViewById(R.id.buttonBGarden)
        val buttonPresentB: Button = view.findViewById(R.id.buttonBPresent)*/



        /*buttonBirdB.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonKolhozB.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonHandmadeB.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonGardenB.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment, CatalogActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        buttonPresentB.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.flFragment,CatalogActivity())
            transaction.addToBackStack(null)
            transaction.commit()
        }*/

        super.onViewCreated(view, savedInstanceState)
    }

}
