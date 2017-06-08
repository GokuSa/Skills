set app_path=E:\workspace_a64\Settings\app\build\outputs\apk
java -jar sign64\signapk.jar sign64\platform.x509.pem sign64\platform.pk8 %app_path%\app-debug.apk %app_path%\Settings.apk
adb shell mount -o remount,rw /system
adb push %app_path%\Settings.apk /system/priv-app/Settings/
ping /n 2 127.1>nul
::adb shell "am start -n com.android.settings/.Settings"
adb reboot