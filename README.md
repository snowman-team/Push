Snowball Android Notification Library <br> [ ![Download](https://api.bintray.com/packages/aquarids/maven/push/images/download.svg?version=0.1.1) ](https://bintray.com/aquarids/maven/push/0.1.1/link)
============

Make it easier to use push with different platforms.

## Installation

```groovy
dependencies {
    // add dependency, please replace x.y.z to the latest version
    implementation "com.xueqiu.push:push:x.y.z"
    // with xiaomi push
    implementation "com.xueqiu.push:xiaomi:x.y.z"
    // with huawei
    implementation "com.xueqiu.push:huawei:x.y.z"
    // with firebase
    implementation "com.xueqiu.push:firebase:x.y.z"
}
```

## Usage

Before you use the library, please read relevant documents.
- [MiPush](https://dev.mi.com/console/doc/detail?pId=41)
- [HuaweiPush](https://developer.huawei.com/consumer/cn/service/hms/catalog/huaweipush_agent.html?page=hmssdk_huaweipush_devguide_client_agent)
- [FirebaseMessage](https://firebase.google.com/docs/cloud-messaging/android/client)

Initialize the push manager in the proper place.
```kotlin
val options = PushOptions()
    .isDebug(BuildConfig.DEBUG)
    .withHandler(xxxHandler)
    .withLogger(object : Logger {
        override fun log(msg: String) {
            Log.d("push log", msg) // set your logger
        }
    })
    .withCallback(object : PushCallback {
        override fun onToken(platform: String, token: String) {
            // get token here
        }

        override fun onEvent(platform: String, context: Context?, event: PushEvent) {
            // handle push event here
        }

    })

PushManager.init(this, options)
```

You can get handler like below.

```kotlin
val handler = PushManager.getHandler<MiPushHandler>(MiPushHandler.HANDLER_ID_XIAOMI)
handler?.setUser(this, "uid")
```

The methods list of handler.

| Method | MiPush | HuaweiPush | FirebaseMessage |
| ------ | ------ | ------ | ------ | 
| requestToken | Y | Y | Y |
| setUser | Y | N | N |
| removeUser | Y | N | N |
| setAlias | Y | N | N |
| removeAlias | Y | N | N |
| subscribeTopic | Y | N | Y |
| unsubscribeTopic | Y | N | Y |

And the push event list.

| Event | MiPush | HuaweiPush | Firebase |
| ------ | ------ | ------ | ------ |
| notification_click | Y | Y | N |
| notification_open | N | Y | N |
| receive_message | Y | Y | Y |
| set_user | Y | N | N |
| remove_user | Y | N | N |
| set_alias | Y | N | N |
| remove_alias | Y | N | N |
| subscribe_topic | Y | N | Y |
| unsubscribe_topic | Y | N | Y |

PS: If your application is in the background, even on Huawei's mobile phone you won't receive any push events from huawei push.


### MiPush

Register mi push to push manager.
```kotlin
withHandler(MiPushHandler(miAppId, miAppKey))
```

### HuaweiPush

Set huaweiAppId at AndroidManifest.

```xml
<meta-data
    android:name="com.huawei.hms.client.appid"
    android:value="appid=app_ip" /> <!-- replace app_id to your huawei id-->    
```

Then register it to push manager.
```kotlin
withHandler(HuaweiPushHandler())
```

### Firebase

Firstly, add firebase to your project and add your google-services.json to module (app-level) folder..
```groovy
buildscript {

  repositories {
    google()  // Google's Maven repository
  }

  dependencies {
    classpath 'com.google.gms:google-services:4.3.1'  // Google Services plugin
  }
}

apply plugin: 'com.google.gms.google-services' // In your module (app-level) Gradle file, add this plugin
```

Then register it to push manager.
```kotlin
withHandler(FirebaseMessageHandler())
```

### Custom handler

You can use your own handler with push manager.

```kotlin
class CustomHandler : BasePushHandler() {
    
    companion object{
        const val HANDLER_ID_CUSTOM = "custom"
    }
    
    override fun getHandlerID(): String = HANDLER_ID_CUSTOM

    override fun requestToken(context: Context) {
        // todo
    }

    override fun initHandler(context: Context) {
        // todo
    }

}

// ...

withHandler(CustomHandler())
```

For more details, please read the example or source code.
