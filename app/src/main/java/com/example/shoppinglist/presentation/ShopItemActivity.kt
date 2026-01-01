package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domen.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var edName: EditText
    private lateinit var edCount: EditText
    private lateinit var buttonSave: Button

    private var screenMode = MODE_UNKNOW
    private var shopItemId = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_ite)
        parseIntent()
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        itemViews()
        addTextChangeListner()
        launchRightMode()
        observeViewModel()
    }

    fun launchRightMode(){
        when(screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    fun launchEditMode(){
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this){
            edName.setText(it.name)
            edCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editShopItem(edName.text?.toString(), edCount.text?.toString())
            val intent = MainActivity.newIntentShowItem(this)
            startActivity(intent)
        }
    }

    fun observeViewModel(){
        viewModel.errorInputName.observe(this){
            val message = if (it){
                Log.d("ShopItemActivity", "Не введены данные")
            }else{
                null
            }
        }
        viewModel.errorInputCount.observe(this){
            val message = if (it){
                Log.d("ShopItemActivity", "Не введены данные")
            }else{
                null
            }
        }
    }

    fun addTextChangeListner(){
        edName.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.resetErrorInputName()
            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }
        })
        edCount.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.resetErrorInputCount()
            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }
        })
    }

    fun launchAddMode(){
        buttonSave.setOnClickListener {
            viewModel.addShopItem(edName.text?.toString(), edCount.text?.toString())
            val intent = MainActivity.newIntentShowItem(this)
            startActivity(intent)
        }
    }

    private fun itemViews(){
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        edName = findViewById(R.id.et_name)
        edCount = findViewById(R.id.ed_count)
        buttonSave = findViewById(R.id.button_add_shop_item)
    }

    fun parseIntent(){
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Aaaaaaaaaaaaaaaaaaaa")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("AAAAAABBBBBBBBBBBBB")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT){
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("AAAAAAABBBBBBCCCCCCC")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }
    companion object{
        const val EXTRA_SCREEN_MODE = "extra_mode"
        const val MODE_ADD = "mode_add"
        const val MODE_EDIT = "mode_edit"
        const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        const val MODE_UNKNOW = ""

        fun newIntentAddItem(context: Context): Intent{
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent{
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}