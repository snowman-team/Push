package com.xueqiu.push

import android.content.Context

interface PushCallback {
    fun onCreated() {}
    fun onToken(platform: String, token: String)
    fun onEvent(platform: String, context: Context?, event: PushEvent)
}