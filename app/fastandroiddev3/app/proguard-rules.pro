# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/caoxiongjie/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn com.ut.mini.**
-dontwarn okio.**
-dontwarn com.xiaomi.**
-dontwarn com.squareup.wire.**
-dontwarn android.support.v4.**
-dontwarn com.squareup.**
-dontwarn com.umeng.**
-dontwarn android.backport.webp.**
-dontwarn android.support.annotation.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**

#   for fastandroiddev 3
-dontwarn sun.misc.**
-dontwarn rx.**
-dontwarn android.databinding.**
-dontwarn java.lang.**
-dontwarn org.codehaus.**
-dontwarn com.ijustyce.**

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.ijustyce.fastandroiddev3.base.BaseViewModel{*;}
-keep class com.ijustyce.fastandroiddev3.IApplication{*;}

## for shortcutbadger
-keep class me.leolin.shortcutbadger.impl.AdwHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.ApexHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.AsusHomeLauncher { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.DefaultBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.NewHtcHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.NovaHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.SolidHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.SonyHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.XiaomiHomeBadger { <init>(...); }

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontwarn com.lzhplus.lzh.bean.**
-dontwarn com.lzhplus.lzh.model.**
-dontwarn com.lzhplus.lzhv2.**

-keepattributes SourceFile,LineNumberTable

-keep class com.lzhplus.lzh.bean.**{*;}
-keep class com.lzhplus.lzh.model.**{*;}
-keep class com.lzhplus.lzhv2.model.**{*;}
-keep class com.lzhplus.lzhv2.viewmodel.**{*;}
-keep class com.lzhplus.lzhv2.bean.**{*;}
-keep class com.lzhplus.common.model.**{*;}
-keep class com.lzhplus.common.bean.**{*;}
-keep class com.lzhplus.lzh.AppApplication
-keep class com.lzhplus.lzh.AppApplication{*;}

#-keep class android.backport.webp.**{*;}
#-keep class android.support.annotation.**{*;}

-dontwarn android.support.design.**
#-keep class android.support.design.**{*;}

-dontwarn android.support.multidex.**
#-keep class android.support.multidex.**{*;}

-dontwarn android.support.v4.**
#-keep class android.support.v4.**{*;}

-dontwarn android.support.v7.**
#-keep class android.support.v7.**{*;}

-dontwarn bolts.**
#-keep class bolts.**{*;}

-dontwarn butterknife.**
#-keep class butterknife.**{*;}

-dontwarn cn.pedant.SweetAlert.**
#-keep class cn.pedant.SweetAlert.**{*;}

-dontwarn cn.trinea.android.view.autoscrollviewpager.**
#-keep class cn.trinea.android.statusView.autoscrollviewpager.**{*;}

-dontwarn com.android.volley.**
#-keep class com.android.volley.**{*;}

-dontwarn com.baidu.**
#-keep class com.baidu.**{*;}

-dontwarn com.facebook.**
#-keep class com.facebook.**{*;}

-dontwarn com.google.gson.**
#-keep class com.google.gson.**{*;}

-dontwarn com.ijustyce.fastandroiddev.**
#-keep class com.ijustyce.fastandroiddev.**{*;}

-dontwarn com.jakewharton.disklrucache.**
#-keep class com.jakewharton.disklrucache.**{*;}

-dontwarn com.lsjwzh.widget.recyclerviewpager.**
#-keep class com.lsjwzh.widget.recyclerviewpager.**{*;}

-dontwarn com.macjay.pulltorefresh.**
#-keep class com.macjay.pulltorefresh.**{*;}

-dontwarn com.malinskiy.superrecyclerview.**
#-keep class com.malinskiy.superrecyclerview.**{*;}

-dontwarn com.nineoldandroids.**
#-keep class com.nineoldandroids.**{*;}

-dontwarn com.nostra13.universalimageloader.**
#-keep class com.nostra13.universalimageloader.**{*;}

-dontwarn com.pnikosis.materialishprogress.**
#-keep class com.pnikosis.materialishprogress.**{*;}

-dontwarn com.readystatesoftware.viewbadger.**
#-keep class com.readystatesoftware.viewbadger.**{*;}

-dontwarn com.sina.sso.**
#-keep class com.sina.sso.**{*;}

-dontwarn com.squareup.**
#-keep class com.squareup.**{*;}

-dontwarn com.tencent.**
#-keep class com.tencent.**{*;}

-dontwarn com.ut.device.**
#-keep class com.ut.device.**{*;}

-dontwarn com.viewpagerindicator.**
#-keep class com.viewpagerindicator.**{*;}

-dontwarn com.zhy.autolayout.**
#-keep class com.zhy.autolayout.**{*;}

-dontwarn de.greenrobot.event.**
#-keep class de.greenrobot.event.**{*;}

-dontwarn kankan.wheel.widget.**
#-keep class kankan.wheel.widget.**{*;}

-dontwarn okio.**
#-keep class okio.**{*;}

-dontwarn org.android.**
#-keep class org.android.**{*;}

-dontwarn u.aly.**
#-keep class u.aly.**{*;}

-dontwarn org.json.alipay.**
#-keep class org.json.alipay.**{*;}

-dontwarn org.xutils.**
#-keep class org.xutils.**{*;}

-keep public class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep public class **$$ViewBinder { *; }

## 微信
-keep class com.lzhplus.lzh.wxapi.**{*;}
-keep public class com.tencent.mm.sdk.modelmsg.WXMediaMessage {public <fields>; public <methods>;}
-keep public class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {public <fields>; public <methods>;}

## 阿里百川等!!!
-keep class com.alibaba.wireless.** {public <fields>;public <methods>;}

-keep class com.ali.auth.third.login.** {public <fields>;public <methods>;}
-keep class com.ali.auth.third.core.model.** {public <fields>;public <methods>;}
-keep class com.ali.auth.third.ut.** {*;}

-keep class com.taobao.tao.** {public <fields>;public <methods>;}

-keep class com.alipay.sdk.** {public <fields>;public <methods>;}

#-keep class com.ali.** {*;}
#-keep class com.alibaba.** {*;}
#-keep class com.taobao.** {*;}
#-keep class com.alipay.android.** {public <fields>;public <methods>;}
#-keep class com.ut.** {*;}
#-keep class com.ta.** {*;}
#-keep class org.json.** {*;}

-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-dontwarn com.ut.**
-dontwarn com.ta.**

## umeng share sdk
-keep class com.umeng.qq.handler.**{ public <fields>;public <methods>;}
##  微博分享
-keep public class com.umeng.socialize.handler.**{public <fields>;public <methods>;}

#-keep class com.tencent.mm.opensdk.openapi.** {*;}
-keep class com.tencent.mm.opensdk.modelpay.** {public <fields>;public <methods>;}
-keep class com.tencent.mm.opensdk.modelbase.** {public <fields>;public <methods>;}
-keep class com.tencent.mm.opensdk.modelmsg.** {public <fields>;public <methods>;}

### 自动注入
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keepclassmembers class ** {
   public void onEvent*(**);
}

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keepattributes *Annotation*

# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep public class com.facebook.imagepipeline.animated.factory.** {
     public <fields>;
    public <methods>;
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

### 环信
-keep class com.hyphenate.chat.adapter.EMACallback{ *;}
-keep class com.hyphenate.chat.adapter.EMACallbackManager{ *;}
-keep class com.hyphenate.chat.adapter.EMACallbackManagerListener{ *;}
-keep class com.hyphenate.chat.adapter.EMACallbackManagerListenerInterface{ *;}
-keep class com.hyphenate.chat.adapter.EMACallSession{ *;}
-keep class com.hyphenate.chat.adapter.**{ *;}


-keep class com.hyphenate.chat.adapter.EMAChatConfig{ *;}
-keep class com.hyphenate.chat.adapter.EMAChatManager{ *;}
-keep class com.hyphenate.chat.adapter.EMAChatManagerListener{ *;}

-dontwarn  com.hyphenate.**

## must keep
-keep class com.hyphenate.chat.EMCursorResult{ public <fields>; public <methods>;}
-keep class com.hyphenate.chat.EMGroupInfo{ public <fields>; public <methods>;}
-keep class com.hyphenate.chat.adapter.EMARHttpAPI{ public <fields>; public <methods>;}
-keep class com.hyphenate.chat.adapter.EMAConversation{ public <fields>; public <methods>;}
-keep class com.hyphenate.chat.adapter.EMAConnectionListener{ public <fields>; public <methods>;}
-keep class com.hyphenate.chat.adapter.EMABase{ *;}
-keep class com.hyphenate.chat.EMPageResult{ *;}

###环信结束

## 极光推送
-dontpreverify
-dontoptimize
-dontskipnonpubliclibraryclasses
-dontwarn cn.jpush.**
-keep class cn.jpush.android.api.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.net.** { *; }
#-keep class cn.jiguang.api.** { *; }

#  防止找不到id等
-keep class **.R$* {
*;
}
-keepattributes Signature

# -applymapping mapping.txt
-keep class * extends java.lang.annotation.Annotation