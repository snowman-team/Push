package com.xueqiu.push.app

import android.content.Context
import com.xueqiu.push.BasePushHandler

class CustomHandler : BasePushHandler() {

    companion object{
        const val HANDLER_ID_CUSTOM = "custom"
    }

    override fun getHandlerID(): String = HANDLER_ID_CUSTOM

    override fun requestToken(context: Context) {
        // todo
    }

    override fun initHandler(context: Context) {
        // todo
    }

}