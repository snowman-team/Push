package com.xueqiu.push.xiaomi

import android.content.Context
import android.os.Bundle
import androidx.annotation.Keep
import com.xiaomi.mipush.sdk.*
import com.xueqiu.push.PushEvent
import com.xueqiu.push.PushInfo
import com.xueqiu.push.PushManager

@Keep
class MiPushReceiver : PushMessageReceiver() {

    private val mHandler by lazy { PushManager.getHandler<MiPushHandler>(MiPushHandler.HANDLER_ID_XIAOMI) }

    override fun onReceiveRegisterResult(context: Context?, commandMessage: MiPushCommandMessage) {
        if (commandMessage.resultCode notEqualCast ErrorCode.SUCCESS) {
            mHandler?.logger?.log("mi push get failed command result: command -> ${commandMessage.command}, result -> ${commandMessage.resultCode}, reason -> ${commandMessage.reason}")
            return
        }
        if (commandMessage.commandArguments.isEmpty()) {
            mHandler?.logger?.log("mi push register result get empty token")
            return
        }
        if (commandMessage.command != MiPushClient.COMMAND_REGISTER) {
            return
        }
        mHandler?.onToken(PushManager.PLATFORM_XIAOMI, commandMessage.commandArguments[0])
    }

    override fun onReceivePassThroughMessage(context: Context?, message: MiPushMessage) {
        val event = PushEvent(PushManager.EVENT_KEY_RECEIVE_MESSAGE)
        event.info = getInfoFromMiMessage(message)
        event.msg = "receive pass through message from mi push"
        mHandler?.callback?.onEvent(PushManager.PLATFORM_XIAOMI, context, event)
    }

    override fun onNotificationMessageClicked(context: Context?, message: MiPushMessage) {
        val event = PushEvent(PushManager.EVENT_KEY_NOTIFICATION_CLICK)
        event.info = getInfoFromMiMessage(message)
        event.msg = "mi push get notification click event"
        mHandler?.callback?.onEvent(PushManager.PLATFORM_XIAOMI, context, event)
    }

    override fun onCommandResult(context: Context?, commandMessage: MiPushCommandMessage) {
        if (commandMessage.resultCode notEqualCast ErrorCode.SUCCESS) {
            mHandler?.logger?.log("mi push get failed command result: command -> ${commandMessage.command}, result -> ${commandMessage.resultCode}, reason -> ${commandMessage.reason}")
            return
        }
        val event = when (commandMessage.command) {
            MiPushClient.COMMAND_SET_ALIAS -> {
                PushEvent(PushManager.EVENT_KEY_SET_ALIAS)
            }
            MiPushClient.COMMAND_UNSET_ALIAS -> {
                PushEvent(PushManager.EVENT_KEY_REMOVE_ALIAS)
            }
            MiPushClient.COMMAND_SET_ACCOUNT -> {
                PushEvent(PushManager.EVENT_SET_USER)
            }
            MiPushClient.COMMAND_UNSET_ACCOUNT -> {
                PushEvent(PushManager.EVENT_REMOVE_USER)
            }
            MiPushClient.COMMAND_SUBSCRIBE_TOPIC -> {
                PushEvent(PushManager.EVENT_KEY_SUBSCRIBE_TOPIC)
            }
            MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC -> {
                PushEvent(PushManager.EVENT_KEY_UNSUBSCRIBE_TOPIC)
            }
            else -> null
        }
        event?.let {
            it.arguments = commandMessage.commandArguments
            it.msg = "mi push get on command result"
            mHandler?.callback?.onEvent(PushManager.PLATFORM_XIAOMI, context, it)
        }
    }

    override fun onNotificationMessageArrived(context: Context?, message: MiPushMessage) {
        val event = PushEvent(PushManager.EVENT_KEY_RECEIVE_MESSAGE)
        event.info = getInfoFromMiMessage(message)
        event.msg = "receive message from mi push"
        mHandler?.callback?.onEvent(PushManager.PLATFORM_XIAOMI, context, event)
    }

    private fun getInfoFromMiMessage(message: MiPushMessage): PushInfo {
        val info = PushInfo()
        info.id = message.messageId
        info.title = message.title
        info.content = message.content
        info.desc = message.description
        info.alias = message.alias
        info.topic = message.topic
        info.category = message.category
        info.isPassThrough = message.passThrough == 1
        if (null != message.extra) {
            val extra = message.extra
            val bundle = Bundle()
            for (key in extra.keys) {
                bundle.putString(key, extra[key])
            }
            info.extra = bundle
        }
        return info
    }

    private infix fun Long.notEqualCast(value: Int): Boolean = this != value.toLong()

}