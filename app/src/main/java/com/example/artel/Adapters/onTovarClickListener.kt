package com.example.artel.Adapters

interface onTovarClickListener {

    fun onTovarClicked(position:Int)

}

class TovarClickListener : onTovarClickListener {
    override fun onTovarClicked(position: Int) {
        // Handle the item click here
    }
}