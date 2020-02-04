package com.cabify.cabistore.api

import com.cabify.cabistore.database.SaleDetail
import com.squareup.moshi.ToJson

class ProductAdapter  {

  @ToJson
  fun toJson(products: List<SaleDetail>): List<String> {
    return products.map { products -> products.code}
  }


}