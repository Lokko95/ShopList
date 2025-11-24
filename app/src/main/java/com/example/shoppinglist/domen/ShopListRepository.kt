package com.example.shoppinglist.domen

interface ShopListRepository {

    fun addShopItem(shopItem : ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItem: ShopItem): ShopItem

    fun getShopList(): List<ShopItem>
}