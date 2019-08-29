package com.xueqiu.push.xiaomi

import android.content.Context
import com.xiaomi.mipush.sdk.MiPushClient
import com.xueqiu.push.BasePushHandler

class MiPushHandler(val appId: String, val appKey: String) : BasePushHandler() {

    companion object {
        const val HANDLER_ID_XIAOMI = "xiaomi"
    }

    override fun getHandlerID(): String = HANDLER_ID_XIAOMI

    override fun initHandler(context: Context) {
        MiPushClient.registerPush(context, appId, appKey)
        callback?.onCreated()
    }

    override fun requestToken(context: Context) {
        MiPushClient.getRegId(context)
    }

    fun setUser(context: Context, uid: String) {
        MiPushClient.setUserAccount(context, uid, null)
    }

    fun removeUser(context: Context, uid: String) {
        MiPushClient.unsetUserAccount(context, uid, null)
    }

    fun setAlias(context: Context, alias: String, category: String) {
        MiPushClient.setAlias(context, alias, category)
    }

    fun removeAlias(context: Context, alias: String, category: String) {
        MiPushClient.unsetAlias(context, alias, category)
    }

    fun subscribeTopic(context: Context, topic: String, category: String) {
        MiPushClient.subscribe(context, topic, category)
    }

    fun unsubscribeTopic(context: Context, topic: String, category: String) {
        MiPushClient.unsubscribe(context, topic, category)
    }

}