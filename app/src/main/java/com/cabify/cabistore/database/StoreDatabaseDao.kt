package com.cabify.cabistore.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cabify.cabistore.model.Products

@Dao
interface StoreDatabaseDao {

  @Insert
  fun insertItem(item: SaleDetail)

  @Insert
  fun insertProduct(product: ArrayList<Products>)


  @Update
  fun update(sale: SaleDetail)

  @Query("SELECT * FROM sale_list_table ORDER BY saleId DESC")
  fun getList(): LiveData<List<SaleDetail>>

  @Query("SELECT sum(quantity * price) FROM sale_list_table ORDER BY saleId DESC")
  fun getTotal(): LiveData<Int>


  @Query("SELECT * FROM sale_list_table ORDER BY saleId DESC")
  fun getAllSales(): LiveData<List<SaleDetail>>


  @Query("SELECT * FROM products ORDER BY productId DESC")
  fun getAllProducts(): LiveData<List<Products>>



}