package com.xueqiu.push.firebase

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.xueqiu.push.BasePushHandler
import com.xueqiu.push.PushEvent
import com.xueqiu.push.PushManager

class FirebaseMessageHandler : BasePushHandler() {

    companion object {
        const val HANDLER_ID_FCM = "fcm"
    }

    override fun getHandlerID(): String = HANDLER_ID_FCM

    override fun initHandler(context: Context) {
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        requestToken(context)
    }

    override fun requestToken(context: Context) {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                logger?.log("Firebase get token failed ${task.exception?.message}")
                return@OnCompleteListener
            }

            val token = task.result?.token
            if (token.isNullOrEmpty()) {
                logger?.log("Firebase get empty token")
                return@OnCompleteListener
            }

            callback?.onToken(PushManager.PLATFORM_FIREBASE, token)
        })
    }

    fun subscribeTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                logger?.log("Firebase subscribe topic failed ${task.exception?.message}")
                return@addOnCompleteListener
            }

            val event = PushEvent(PushManager.EVENT_KEY_SUBSCRIBE_TOPIC)
            event.msg = "Firebase subscribe $topic topic"
            callback?.onEvent(PushManager.PLATFORM_FIREBASE, null, event)
        }
    }

    fun unsubscribeTopic(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                logger?.log("Firebase unsubscribe topic failed ${task.exception?.message}")
                return@addOnCompleteListener
            }

            val event = PushEvent(PushManager.EVENT_KEY_UNSUBSCRIBE_TOPIC)
            event.msg = "Firebase subscribe $topic topic"
            callback?.onEvent(PushManager.PLATFORM_FIREBASE, null, event)
        }

    }

}