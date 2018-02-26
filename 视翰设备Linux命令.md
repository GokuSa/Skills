由于我们在PC上和android设备通信都是采用adb，所以先配置adb的环境变量，这样无论在什么目录下都可以直接使用adb。如果不配置每次执行adb命令必须进入其所在目录。

找到adb.exe安装目录并复制路径

![](E:\A64version6\four.png)

右击计算机选择属性——进入控制面板——高级系统设置——

![](E:\A64version6\one.png)

环境变量——选中系统变量的Path

![](E:\A64version6\two.png)

点击编辑——使用end键定位到最后，添加一个英文状态下的分号;再复制abd.exe的路径即可，点击确定保存对话框

![](E:\A64version6\five.png)

以下操作都将依赖adb环境变量的正确配置,进入dos环境，输入adb version，如果配置正确会有如下输出

![](E:\A64version6\six.png)


----------

### 安装系统应用，比如Settings.apk 
    adb shell mount -o remount,rw /system
    adb push E:\workspace\Settings64\app\build\outputs\apk\Settings.apk /system/priv-app/Settings/

### 列出目标设备中已安装的应用程序包 
    C:\Users\Administrator>adb shell pm list packages

### 卸载应用程序包 如多媒体，shine.shineplayer为其包名 
    C:\Users\Administrator>adb uninstall shine.shineplayer

###解决dos窗口中文乱码
	//设置编码为UTF-8
    C:\Users\Administrator>chcp 65001
	左上角点击——选择属性——字体——Lucida Console——确定

###Logcat的使用
    C:\Users\Administrator>adb logcat OnOffHelper:D *:S
	OnOffHelper 表示TAG，
	：后面的参数表示日志级别，D-debug（调式），E-error（错误） ，S-Slient（什么都不输出）
	上面这句话的意思是打印TAG为OnOffHelper级别debug以上的日志，其他都忽略

	C:\Users\Administrator>adb logcat OnOffHelper:D ShineService:D *:S
	打印TAG为OnOffHelper或ShineService，级别debug以上的日志，其他都忽略
	
	清空日志
	C:\Users\Administrator>adb logcat -c
