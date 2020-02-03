
package com.cabify.cabistore.utils

import android.content.SharedPreferences

typealias PrefEditor = SharedPreferences.Editor

internal inline fun SharedPreferences.commit(crossinline exec: PrefEditor.() -> Unit) {
  val editor = this.edit()
  editor.exec()
  editor.apply()
}