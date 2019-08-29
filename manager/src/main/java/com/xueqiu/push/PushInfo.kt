package com.xueqiu.push

import android.os.Bundle

class PushInfo {

    @JvmField
    var id: String? = null

    @JvmField
    var title: String? = null

    @JvmField
    var content: String? = null

    @JvmField
    var desc: String? = null

    @JvmField
    var alias: String? = null

    @JvmField
    var topic: String? = null

    @JvmField
    var category: String? = null

    @JvmField
    var isPassThrough: Boolean = false

    @JvmField
    var extra: Bundle? = null

}