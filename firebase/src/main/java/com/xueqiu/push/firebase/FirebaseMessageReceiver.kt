package com.xueqiu.push.firebase

import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.xueqiu.push.PushEvent
import com.xueqiu.push.PushInfo
import com.xueqiu.push.PushManager

class FirebaseMessageReceiver : FirebaseMessagingService() {

    private val mHandler by lazy { PushManager.getHandler<FirebaseMessageHandler>(FirebaseMessageHandler.HANDLER_ID_FCM) }

    override fun onNewToken(token: String) {
        mHandler?.onToken(PushManager.PLATFORM_FIREBASE, token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val info = PushInfo()
        info.id = message.messageId
        info.title = message.notification?.title
        info.content = message.notification?.body

        val bundle = Bundle()
        for (key in message.data.keys) {
            bundle.putString(key, message.data[key])
        }
        info.extra = bundle

        val event = PushEvent(PushManager.EVENT_KEY_RECEIVE_MESSAGE)
        event.info = info

        mHandler?.callback?.onEvent(PushManager.PLATFORM_FIREBASE, null, event)
    }

}