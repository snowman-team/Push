package com.xueqiu.push

class PushOptions {

    var isDebug: Boolean = false
        private set

    var logger: Logger? = null
        private set

    var params: Map<String, Any>? = null
        private set

    var callback: PushCallback? = null
        private set

    var pushHandlers: MutableList<BasePushHandler> = ArrayList()
        private set

    fun isDebug(boolean: Boolean): PushOptions {
        isDebug = boolean
        return this
    }

    fun withLogger(logger: Logger): PushOptions {
        this.logger = logger
        return this
    }

    fun withParams(params: Map<String, Any>): PushOptions {
        this.params = params
        return this
    }

    fun withHandler(handler: BasePushHandler): PushOptions {
        var hasItem = false
        pushHandlers.forEach {
            if (it.getHandlerID() == handler.getHandlerID()) {
                hasItem = true
                return@forEach
            }
        }
        if (!hasItem) {
            pushHandlers.add(handler)
        }
        return this
    }

    fun withCallback(callback: PushCallback): PushOptions {
        this.callback = callback
        return this
    }

}