package com.cabify.cabistore.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cabify.cabistore.database.SaleDetail as SaleDetail

@Dao
interface StoreDatabaseDao {

  @Insert
  fun insertItem(item: SaleDetail)

  @Insert
  fun insertProduct(item: SaleDetail)

  @Query("UPDATE sale_list_table SET quantity = :quantity WHERE code =:code")
  fun updateQuantityStr( code:String,quantity: Int)

  @Query("UPDATE sale_list_table SET price = :price WHERE code =:code")
  fun updatePrice(code: String, price: Double)

  @Query("UPDATE sale_list_table SET quantity = :quantity WHERE code =:code")
  fun updateProductQty( code:String, quantity: Int)

  @Update
  fun updateQuantity(item: SaleDetail)

  @Insert
  fun insertProduct(product: ArrayList<SaleDetail>)


  @Query("DELETE FROM sale_list_table WHERE code =:code")
  fun deleteItem( code:String)

  @Update
  fun update(sale: SaleDetail)

  @Query("SELECT * FROM sale_list_table ORDER BY code DESC")
  fun getList(): LiveData<List<SaleDetail>>

  @Query("SELECT sum(price * quantity) FROM sale_list_table")
  fun getTotal(): LiveData<Double>


  @Query("SELECT * FROM sale_list_table WHERE quantity > 0 ORDER BY code DESC ")
  fun getAllSales(): LiveData<List<SaleDetail>>


  @Query("SELECT * FROM sale_list_table ORDER BY code DESC")
  fun getAllProducts(): LiveData<List<SaleDetail>>

  @Query("SELECT count(:key) FROM sale_list_table WHERE code = :key")
  fun get(key: String): Int

  @Query("SELECT count(:code) FROM sale_list_table WHERE code = :code")
  fun getOneProduct(code: String): Int



}