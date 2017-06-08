###framework-res.apk 开发日记
此应用负责系统资源的共享，其源码在frameworks/base/core/res目录下。其apk在设备上的位置/system/framework/。本次对framework-res的修改是去除状态栏和导航栏

 frameworks/base/core/res/res/values/dimens.xml

	 <!-- 顶部状态栏从24dp改为0dp-->
    <dimen name="status_bar_height">0dp</dimen>
    <!-- 底部导航栏高度48dp改为0dp -->
    <dimen name="navigation_bar_height">0dp</dimen>
    <!--横屏时的高度同样改为0 -->
    <dimen name="navigation_bar_height_landscape">0dp</dimen>
