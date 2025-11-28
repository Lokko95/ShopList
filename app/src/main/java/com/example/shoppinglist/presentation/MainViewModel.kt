package com.example.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domen.DeleteShopItemUseCase
import com.example.shoppinglist.domen.EditShopItemUseCase
import com.example.shoppinglist.domen.GetShopItemUseCase
import com.example.shoppinglist.domen.GetShopListUseCase
import com.example.shoppinglist.domen.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopListRepository = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
        shopList
    }

    fun editShopList(shopItem: ShopItem){
        val newItem = shopItem.copy(enable = !shopItem.enable)
        editShopListRepository.editShopItem(newItem)
        shopList
    }
}