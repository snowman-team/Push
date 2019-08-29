package com.xueqiu.push

import androidx.annotation.StringDef

class RomInfo {

    @RomType
    val type: String

    init {
        type = initRomType()
    }

    @RomType
    private fun initRomType(): String {
        val manufacturer = RomHelper.getManufacturer() ?: return TYPE_UNKNOWN
        val brand = RomHelper.getBrand() ?: return TYPE_UNKNOWN

        RomHelper.romGoogle.forEach {
            if (brand.contains(it) || manufacturer.contains(it)) {
                return TYPE_GOOGLE
            }
        }
        RomHelper.romHuawei.forEach {
            if (brand.contains(it) || manufacturer.contains(it)) {
                return TYPE_HUAWEI
            }
        }
        RomHelper.romXiaomi.forEach {
            if (brand.contains(it) || manufacturer.contains(it)) {
                return TYPE_XIAOMI
            }
        }
        return TYPE_UNKNOWN
    }

    companion object {
        const val TYPE_GOOGLE = "google"
        const val TYPE_HUAWEI = "huawei"
        const val TYPE_XIAOMI = "xiaomi"
        const val TYPE_UNKNOWN = "unknown"
    }

    @StringDef(TYPE_GOOGLE, TYPE_HUAWEI, TYPE_XIAOMI, TYPE_UNKNOWN)
    @Retention(AnnotationRetention.SOURCE)
    annotation class RomType

}