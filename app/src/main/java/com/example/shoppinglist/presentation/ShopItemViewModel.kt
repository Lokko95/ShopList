package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domen.AddShopItemUseCase
import com.example.shoppinglist.domen.EditShopItemUseCase
import com.example.shoppinglist.domen.GetShopItemUseCase
import com.example.shoppinglist.domen.ShopItem

class ShopItemViewModel: ViewModel() {

    val repository = ShopListRepositoryImpl

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldeCloseScreen = MutableLiveData<Unit>()
    val shouldeCloseScreen: LiveData<Unit>
        get() = _shouldeCloseScreen

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    val addShopItemUseCase = AddShopItemUseCase(repository)
    val editShopItemUseCase = EditShopItemUseCase(repository)
    val getShopItemUseCase = GetShopItemUseCase(repository)

    fun getShopItem(shopItemId: Int){
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun addShopItem(inputString: String?, inputCount: String?){
        val name = parseName(inputString)
        val count = parseCount(inputCount)
        val fieldValidate = validateInput(name,count)
        if (fieldValidate){
            val shopItem = ShopItem(name,count,true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(inputString: String?, inputCount: String?, ){
        val name = parseName(inputString)
        val count = parseCount(inputCount)
        val fieldValidate = validateInput(name,count)
        if (fieldValidate){
            _shopItem.value?.let{
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }
    }

    fun parseName(inputString: String?): String{
        return inputString?.trim() ?: ""
    }

    fun parseCount(inputCount: String?): Int{
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }

    }

    fun validateInput(name: String, count: Int): Boolean{
        var result = true
        if (name.isBlank()){
            _errorInputName.value = false
            result =false
        }
        if (count <= 0){
            _errorInputCount.value = false
            result = false
        }
        return result
    }

    fun resetErrorInputName(){
        _errorInputName.value = false
    }

    fun resetErrorInputCount(){
        _errorInputCount.value = false
    }

    fun finishWork(){
        _shouldeCloseScreen.value = Unit
    }

}