package com.cabify.cabistore.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sale_list_table")
data class SaleDetail(



  @PrimaryKey(autoGenerate = false)  val code: String = "DEFAULT",

  @ColumnInfo val name: String = "",

  @ColumnInfo val price: Int = 0,

  @ColumnInfo var quantity: Int = 0



)