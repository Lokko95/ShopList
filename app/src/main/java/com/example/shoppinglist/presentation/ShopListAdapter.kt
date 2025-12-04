package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domen.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewholder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShopItemLongClickListner : ((shopItem: ShopItem) -> Unit)? = null
    var onShopItemClickListner : ((shopItem: ShopItem) -> Unit)? = null
    var onShopItemSwipeListner : ((shopItem: ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopItemViewholder {
        val layout = when (viewType) {
            VIEW_ACTIVE -> R.layout.item_shop_enabled
            VIEW_NO_ACTIVE -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknow view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false)
        return ShopItemViewholder(view)
    }

    override fun onBindViewHolder(
        holder: ShopItemViewholder,
        position: Int
    ) {
        val shopItem = shopList[position]
        holder.tvText.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListner?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListner?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return if (shopItem.enable){
            VIEW_ACTIVE
        }else{
            VIEW_NO_ACTIVE
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopItemViewholder(view: View): RecyclerView.ViewHolder(view){
        val tvText = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    companion object{
         val VIEW_ACTIVE = 1;
         val VIEW_NO_ACTIVE = 0;
         val MAX_VIEW_SIZE = 15;
    }


}