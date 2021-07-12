# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#noinspection ShrinkerUnresolvedReference

-keepattributes SourceFile,LineNumberTable,Exceptions,Signature
-keepattributes Exceptions,InternalClasses,RuntimeVisibleAnnotations,InnerClasses

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

-keep class com.testfairy.** { *; }
-dontwarn com.testfairy.**

-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn kotlin.**
-keep class androidx.appcompat.widget.** { *; }

-keep class kotlin.Metadata { *; }

-keep class org.aspectj.* {
    <fields>;
    <init>();
    <methods>;
 }
-keep class cn.jzvd.*{
 <fields>;
    <init>();
    <methods>;

}

-keep class com.code.challenge.utils.* {
<fields>;
<init>();
<methods>;
                                            }
-keep class com.code.challenge.common.utils.jzvd_player.* {
<fields>;
<init>();
<methods>;
                                            }
-keep public class cn.jzvd.JZMediaSystem {*; }
-keep public class cn.jzvd.demo.CustomMedia.CustomMedia {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaIjk {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaSystemAssertFolder {*; }

-keep class tv.danmaku.ijk.media.player.** {*; }
-dontwarn tv.danmaku.ijk.media.player.*
-keep interface tv.danmaku.ijk.media.player.** { *; }

# Keep Huawei SDK
-ignorewarnings
-keepattributes *Annotation*
-keep class com.huawei.hianalytics.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
-keep class com.hianalytics.android.**{*;}
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
