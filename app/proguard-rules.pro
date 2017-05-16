# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android-sdk_r24.1.2-windows\android-sdk-windows/tools/proguard/proguard-android.txt
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

#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------
-keep class com.xinyu.mwp.activity.base.** { *; }
-keep class com.xinyu.mwp.activity.unionpay.** { *; }
-keep class com.xinyu.mwp.activity.** { *; }
-keep class com.xinyu.mwp.adapter.base.** { *; }
-keep class com.xinyu.mwp.adapter.viewholder.** { *; }
-keep class com.xinyu.mwp.adapter.** { *; }
-keep class com.xinyu.mwp.annotation.** { *; }
-keep class com.xinyu.mwp.application.** { *; }
-keep class com.xinyu.mwp.constant.** { *; }
-keep class com.xinyu.mwp.emptyview.** { *; }
-keep class com.xinyu.mwp.entity.** { *; }
-keep class com.xinyu.mwp.exception.** { *; }
-keep class com.xinyu.mwp.fragment.** { *; }
-keep class com.xinyu.mwp.fragment.base.** { *; }
-keep class com.xinyu.mwp.helper.** { *; }
-keep class com.xinyu.mwp.listener.** { *; }
-keep class com.xinyu.mwp.manage.** { *; }
-keep class com.xinyu.mwp.networkapi.** { *; }
-keep class com.xinyu.mwp.networkapi.http.** { *; }
-keep class com.xinyu.mwp.networkapi.socketapi.SocketReqeust.** { *; }
-keep class com.xinyu.mwp.swipeback.** { *; }
-keep class com.xinyu.mwp.user.** { *; }
-keep class com.xinyu.mwp.util.** { *; }
-keep class com.xinyu.mwp.view.** { *; }
-keep class com.xinyu.mwp.view.banner.** { *; }
-keep class com.xinyu.mwp.wxapi.** { *; }
-keep class in.srain.cube.views.ptr.** { *; }
-keep class in.srain.cube.views.ptr.header.** { *; }
-keep class in.srain.cube.views.ptr.indicator.** { *; }
-keep class in.srain.cube.views.ptr.util.** { *; }
#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------
#eventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#imageLoader
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *; }         #imageLoader包下所有类及类里面的内容不要混淆
#MPAndroidChart
-keep class com.github.mikephil.charting.** { *; }
#Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
#netty
-keepattributes Signature,InnerClasses
-keepclasseswithmembers class io.netty.** {
    *;
}
-dontwarn io.netty.**
-dontwarn sun.**
# 银联
-dontwarn com.unionpay.** -keep class com.unionpay.** { *; }
#StatusBarUtil
-keep class com.jaeger.library.**{*;}
#okhttp
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
#zxing
-dontwarn com.google.zxing.**
-keep class com.google.zxing.** { *; }
# Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keepattributes SourceFile,LineNumberTable,*Annotation*
-keep class com.crashlytics.android.**

-dontwarn org.apache.http.**
-keep class org.apache.http.** { *;}

-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient

-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient

#-----------------------------------微信支付
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------