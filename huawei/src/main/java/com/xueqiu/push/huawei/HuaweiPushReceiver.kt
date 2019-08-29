package com.xueqiu.push.huawei

import android.content.Context
import android.os.Bundle
import androidx.annotation.Keep
import com.huawei.hms.support.api.push.PushReceiver
import com.xueqiu.push.PushEvent
import com.xueqiu.push.PushInfo
import com.xueqiu.push.PushManager

@Keep
class HuaweiPushReceiver : PushReceiver() {

    private val mHandler by lazy { PushManager.getHandler<HuaweiPushHandler>(HuaweiPushHandler.HANDLER_ID_HUAWEI) }

    override fun onToken(context: Context?, token: String?, extras: Bundle?) {
        if (token.isNullOrEmpty()) {
            mHandler?.logger?.log("huawei push get empty token")
            return
        }
        mHandler?.onToken(PushManager.PLATFORM_HUAWEI, token)
    }

    override fun onPushMsg(context: Context?, msgBytes: ByteArray?, extras: Bundle?): Boolean {
        if (null == msgBytes || msgBytes.isEmpty()) {
            mHandler?.logger?.log("huawei push get empty token")
            return false
        }

        val info = PushInfo()
        info.content = String(msgBytes)
        info.extra = extras
        info.isPassThrough = true

        val event = PushEvent(PushManager.EVENT_KEY_RECEIVE_MESSAGE)
        event.msg = "receive pass through message from huawei push"
        event.info = info
        mHandler?.callback?.onEvent(PushManager.PLATFORM_HUAWEI, context, event)
        return true
    }

    override fun onEvent(context: Context?, msgEvent: Event?, extras: Bundle?) {
        val event = when (msgEvent) {
            Event.NOTIFICATION_OPENED -> {
                PushEvent(PushManager.EVENT_KEY_NOTIFICATION_OPEN)
            }
            Event.NOTIFICATION_CLICK_BTN -> {
                PushEvent(PushManager.EVENT_KEY_NOTIFICATION_CLICK)
            }
            else -> null
        }
        event?.let {
            val info = PushInfo()
            info.extra = extras
            it.info = info
            mHandler?.callback?.onEvent(PushManager.PLATFORM_HUAWEI, context, event)
        }
    }
}