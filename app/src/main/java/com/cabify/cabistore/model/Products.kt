package com.cabify.cabistore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "products")
data class Products(
  @PrimaryKey(autoGenerate = true) var productId: Long = 0L,
  @ColumnInfo(name = "code") val code: String,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "price") val price: Int,
  @ColumnInfo(name = "quantity") var quantity: String

)