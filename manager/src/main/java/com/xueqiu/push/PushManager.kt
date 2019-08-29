package com.xueqiu.push

import android.content.Context

object PushManager {

    const val PLATFORM_FIREBASE = "firebase"
    const val PLATFORM_XIAOMI = "xiaomi"
    const val PLATFORM_HUAWEI = "huawei"

    const val EVENT_KEY_NOTIFICATION_CLICK = "notification_click"
    const val EVENT_KEY_NOTIFICATION_OPEN = "notification_open"
    const val EVENT_KEY_RECEIVE_MESSAGE = "receive_pass_through_message"
    const val EVENT_SET_USER = "set_user"
    const val EVENT_REMOVE_USER = "remove_user"
    const val EVENT_KEY_SET_ALIAS = "set_alias"
    const val EVENT_KEY_REMOVE_ALIAS = "remove_alias"
    const val EVENT_KEY_SUBSCRIBE_TOPIC = "subscribe_topic"
    const val EVENT_KEY_UNSUBSCRIBE_TOPIC = "unsubscribe_topic"

    val romInfo by lazy { RomInfo() }

    private var isDebug: Boolean = false
    private var mLogger: Logger? = null
    private var mCallback: PushCallback? = null
    private val mHandlers: MutableList<BasePushHandler> = ArrayList()

    fun init(context: Context, options: PushOptions) {
        isDebug = options.isDebug
        mLogger = options.logger
        mCallback = options.callback

        mHandlers.clear()
        mHandlers.addAll(options.pushHandlers)

        mHandlers.forEach {
            it.isDebug = isDebug
            it.logger = mLogger
            it.callback = mCallback
            it.initHandler(context)
        }
    }

    fun <T : BasePushHandler> getHandler(handlerID: String): T? {
        return mHandlers.find { it.getHandlerID() == handlerID }?.let { it as T }
    }

    fun requestToken(context: Context) {
        mHandlers.forEach {
            it.requestToken(context)
        }
    }

    fun getToken(handlerID: String): String? {
        return mHandlers.find { it.getHandlerID() == handlerID }?.token
    }

}