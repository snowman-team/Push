package com.xueqiu.push.huawei

import android.content.Context
import com.huawei.android.hms.agent.HMSAgent
import com.xueqiu.push.BasePushHandler

class HuaweiPushHandler : BasePushHandler() {

    companion object {
        const val HANDLER_ID_HUAWEI = "huawei"
    }

    override fun getHandlerID(): String = HANDLER_ID_HUAWEI

    override fun initHandler(context: Context) {
        HMSAgent.init(context)
        HMSAgent.connect {
            if (it != HMSAgent.AgentResultCode.HMSAGENT_SUCCESS) {
                return@connect
            }
            HMSAgent.Push.getToken { code ->
                logger?.log("huawei push request token result code -> $code")
            }
        }
    }

    override fun requestToken(context: Context) {
        HMSAgent.Push.getToken { code ->
            logger?.log("huawei push request token result code -> $code")
        }
    }

}