package com.example.shoppingList.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.shoppingList.domain.ShopItem
import com.example.shoppingList.presentation.ShopApplication
import javax.inject.Inject

class ShopListProvider : ContentProvider() {

    private val component by lazy {
        (context as ShopApplication).component
    }

    @Inject
    lateinit var shopListDao: ShopListDao

    @Inject
    lateinit var shopListMapper: ShopListMapper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.shoppingList", "shop_items/", GET_SHOP_ITEMS_QUERY)
        addURI("com.example.shoppingList", "shop_items/#", GET_SHOP_ITEM_BY_ID_QUERY)
        addURI("com.example.shoppingList", "shop_items/*", GET_SHOP_ITEM_BY_NAME_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return when (uriMatcher.match(p0)) {
            GET_SHOP_ITEMS_QUERY -> {
                shopListDao.getShopListCursor()
            }
            else ->
                null
        }
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        when (uriMatcher.match(p0)) {
            GET_SHOP_ITEMS_QUERY -> {
                if (p1 == null) return null
                val id = p1.getAsInteger("id")
                val name = p1.getAsString("name")
                val count = p1.getAsInteger("count")
                val enabled = p1.getAsBoolean("enabled")
                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )
                shopListDao.addShopItemSync(shopListMapper.mapEntityToDbModel(shopItem))
            }
        }
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        when (uriMatcher.match(p0)) {
            GET_SHOP_ITEMS_QUERY -> {
                val id = p2?.get(0)?.toInt() ?: -1
                return shopListDao.deleteShopItemSync(id)
            }
        }
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object {

        private const val GET_SHOP_ITEMS_QUERY = 100
        private const val GET_SHOP_ITEM_BY_ID_QUERY = 1
        private const val GET_SHOP_ITEM_BY_NAME_QUERY = 10
    }
}