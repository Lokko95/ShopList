package com.example.shoppinglist.domen

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItem(shopItem: ShopItem): ShopItem{
        return shopListRepository.getShopItem(shopItem)

    }
}