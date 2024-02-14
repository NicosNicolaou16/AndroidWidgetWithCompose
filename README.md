# Android Widget With Compose

This project is the setup for the Widget in Android with Jetpack Compose (Glance).

# Steps 1 Create the Glance Receiver class that extend GlanceAppWidgetReceiver() and return the Widget Class

```Kotlin
class MyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = MyAppWidget()
}
```

# Step 2 Create the xml file into the xml directory (configuration to declare the size etc.)

```XML
<?xml version="1.0" encoding="utf-8"?>
<appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
    android:minWidth="250dp"
    android:minHeight="50dp"
    android:resizeMode="horizontal|vertical"
    android:widgetCategory="home_screen">
</appwidget-provider>
```

# Step 3 Register the Receiver Class into the Manifest and set the xml configuration for the Glance Widget

```XML

<receiver android:name=".glance_widget.receiver.MyAppWidgetReceiver" android:exported="true">
    <intent-filter>
        <action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
    </intent-filter>
    <meta-data android:name="android.appwidget.provider"
        android:resource="@xml/my_app_widget_info" />
</receiver>
```

# Step 4 Create the Widget that extend GlanceAppWidget() class

# Reference

https://developer.android.com/jetpack/compose/glance/create-app-widget  <br />