package com.example.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ShoppingListViewModel : ViewModel(){
    private  val itemsLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()

}

