### SettingProvider.apk 6.0开发记录
SettingProvider.apk 给Settings应用提供设置默认值、设置存储和设置修改服务。其源码在frameworks/base/packages目录下。其apk在设备上的位置:/system/priv-app/SettingsProvider。本次对SettingProvider的修改主要针对一些设置默认值

main\res\values\defaults.xml
	
	修改智能背光默认为0 表示不开启
    <integer name="def_brightness_light">0</integer>
	关屏延时，6.0以前默认是5分钟，现在是永不休眠
	<integer name="def_screen_off_timeout">2147483647</integer>
	是否自动获取网络时间，6.0以前是true，现在是false不用修改
	<bool name="def_auto_time">false</bool>
  
main\res\values\custom_config.xml

	默认设为24小时制
	<string name="def_time_12_24" translatable="false">24</string>


备注：Android M的SettingProvider较之前有重大变更，本来负责对设置数据增删改查的数据库Setting.db不再使用和更新，取而代之的是使用在目录/data/system/users/0下的xml来存储设置。
 