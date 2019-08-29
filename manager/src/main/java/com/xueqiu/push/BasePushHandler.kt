package com.xueqiu.push

import android.content.Context

abstract class BasePushHandler {

    var isDebug: Boolean = false

    var callback: PushCallback? = null

    var logger: Logger? = null

    var token: String? = null
        private set

    open fun onToken(platform: String, token: String) {
        this.token = token
        callback?.onToken(platform, token)
    }

    abstract fun getHandlerID(): String

    abstract fun requestToken(context: Context)

    abstract fun initHandler(context: Context)

}