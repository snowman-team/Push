package com.xueqiu.push.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.xueqiu.push.*
import com.xueqiu.push.huawei.HuaweiPushHandler
import com.xueqiu.push.xiaomi.MiPushHandler

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val options = PushOptions()
            .isDebug(BuildConfig.DEBUG)
            .withHandler(MiPushHandler("2882303761518139092", "5121813924092"))
            .withHandler(HuaweiPushHandler())
            .withLogger(object : Logger {
                override fun log(msg: String) {
                    Log.d("push log", msg)
                }

            })
            .withCallback(object : PushCallback {
                override fun onToken(platform: String, token: String) {
                    Log.d("push log", "get $platform push token -> $token")
                }

                override fun onEvent(platform: String, context: Context?, event: PushEvent) {
                    Log.d("push log", "get $platform push event -> $event")
                }

            })

        PushManager.init(this, options)
    }
}