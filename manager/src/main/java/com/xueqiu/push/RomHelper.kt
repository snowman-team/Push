package com.xueqiu.push

import android.os.Build
import java.util.*

class RomHelper {

    companion object {

        val romGoogle = arrayOf("google")
        val romHuawei = arrayOf("huawei")
        val romXiaomi = arrayOf("xiaomi")

        @JvmStatic
        fun isXiaomi(): Boolean = checkRom(romXiaomi)

        @JvmStatic
        fun isHuawei(): Boolean = checkRom(romHuawei)

        @JvmStatic
        fun isGoogle(): Boolean = checkRom(romGoogle)

        @JvmStatic
        fun getManufacturer(): String? {
            val manufacturer = Build.MANUFACTURER ?: return null
            return if (manufacturer.isNotEmpty()) {
                manufacturer.toLowerCase(Locale.getDefault())
            } else {
                null
            }
        }

        @JvmStatic
        fun getBrand(): String? {
            val brand = Build.BRAND ?: return null
            return if (brand.isNotEmpty()) {
                brand.toLowerCase(Locale.getDefault())
            } else {
                null
            }
        }

        @JvmStatic
        private fun checkRom(names: Array<String>): Boolean {
            val manufacturer = getManufacturer() ?: return false
            val brand = getBrand() ?: return false
            names.forEach {
                if (brand.contains(it) || manufacturer.contains(it)) {
                    return true
                }
            }
            return false
        }
    }


}