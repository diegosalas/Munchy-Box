package com.cabify.cabistore.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sale_list_table")
data class SaleDetail(
  @PrimaryKey(autoGenerate = true) var saleId: Int = 0,

  @ColumnInfo(name = "productId") val productID: String = "TSHIRT",

  @ColumnInfo(name = "quantity") val quantity: Int = 1,

  @ColumnInfo(name = "price") val price: Int = 25,

  @ColumnInfo(name = "time") var time: Long = 0


)