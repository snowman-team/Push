package com.xueqiu.push

data class PushEvent(@JvmField val key: String) {

    @JvmField
    var info: PushInfo? = null

    @JvmField
    var msg: String? = null

    @JvmField
    var arguments: MutableList<String>? = null
}