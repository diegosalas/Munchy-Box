package com.cabify.cabistore.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cabify.cabistore.database.SaleDetail as SaleDetail

@Dao
interface StoreDatabaseDao {

  @Insert
  fun insertItem(item: SaleDetail)

  @Query("UPDATE sale_list_table SET quantity = :quantity WHERE code =:code")
  fun updateQuantityStr( code:String,quantity: Int)

  @Update
  fun updateQuantity(item: SaleDetail)

  @Insert
  fun insertProduct(product: ArrayList<Products>)


  @Query("DELETE FROM sale_list_table WHERE code =:code")
  fun deleteItem( code:String)

  @Update
  fun update(sale: SaleDetail)

  @Query("SELECT * FROM sale_list_table ORDER BY code DESC")
  fun getList(): LiveData<List<SaleDetail>>

  @Query("SELECT sum(price * quantity) FROM sale_list_table")
  fun getTotal(): LiveData<Int>


  @Query("SELECT * FROM sale_list_table ORDER BY code DESC")
  fun getAllSales(): LiveData<List<SaleDetail>>


  @Query("SELECT * FROM products ORDER BY productId DESC")
  fun getAllProducts(): LiveData<List<Products>>

  @Query("SELECT quantity FROM sale_list_table WHERE code = :key")
  fun get(key: String): Int



}