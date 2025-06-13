# Android Widget With Compose

This project sets up the Widget in Android using Jetpack Compose Glance.

# Setup

## Step 1 add the follow dependencies libraries

```Kotlin
val composeGlanceWidgetVersion by extra("1.1.1")

dependencies {

    //...
    //Glance Widget
    implementation("androidx.glance:glance-appwidget:$composeGlanceWidgetVersion")
    //Using Material 2
    implementation("androidx.glance:glance-material:$composeGlanceWidgetVersion")
    //Using Material 3
    implementation("androidx.glance:glance-material3:$composeGlanceWidgetVersion")
}
```

## Steps 2 Create the Glance Receiver class that extend GlanceAppWidgetReceiver() and return the Widget Class

```Kotlin
class MyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = MyAppWidget()
}
```

## Step 3 Create the xml file into the xml directory (configuration to declare the size etc.)

```XML
<?xml version="1.0" encoding="utf-8"?>
<appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
    android:initialLayout="@layout/glance_default_loading_layout" android:minWidth="250dp"
    android:minHeight="50dp" android:resizeMode="horizontal|vertical"
    android:widgetCategory="home_screen">
</appwidget-provider>
```

## Step 4 Register the Receiver Class into the Manifest and set the xml configuration for the Glance Widget

```XML

<receiver android:name=".glance_widget.receiver.MyAppWidgetReceiver" android:exported="true">
    <intent-filter>
        <action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
    </intent-filter>
    <meta-data android:name="android.appwidget.provider"
        android:resource="@xml/my_app_widget_info" />
</receiver>
```

## Step 5 Create the Widget that extend GlanceAppWidget() class

```Kotlin
//...
class MyAppWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MyContent(context) //Compose UI
        }
    }
    //....
}
```
> [!IMPORTANT]  
> Check my article for the setup :point_right: [Android Widget Setup with Jetpack Compose Glance (with a Flutter example) - Medium](https://medium.com/@nicosnicolaou/android-widget-setup-with-jetpack-compose-glance-with-a-flutter-example-e07fb63c9466) :point_left: <br />

> [!IMPORTANT]
> Similar project with (Dart Language) :point_right: [Android_Widget_With_Compose_With_Flutter](https://github.com/NicosNicolaou16/Android_Widget_With_Compose_With_Flutter) :point_left: <br />

# Versioning

Target SDK version: 35 <br />
Minimum SDK version: 28 <br />
Kotlin version: 2.1.21 <br />
Gradle version: 8.10.1 <br />

# References

https://developer.android.com/jetpack/compose/glance/create-app-widget  <br />