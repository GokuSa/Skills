### Settings.apk 6.0开发记录
其apk在设备上的位置:/system/priv-app/Settings。

###开发前准备
- 除了需要导入Settings的源码，还需导入SettingsLib模块作为依赖
- 在app目录下新建systemlib目录作为系统库，需添加	如下jar包作为Provider，具体参考模块依赖
	-  android_stubs_current_intermediates.jar
	- bouncycastle_intermediates.jar
	- conscrypt_intermediates.jar
	- core-libart_intermediates.jar
	- ext_intermediates.jar
	- framework_intermediates.jar
	- frameworks-core-util-lib_intermediates.jar
	- ims-common_intermediates.jar
	- jsr305_intermediates.jar
	- setup-wizard-lib_intermediates.jar
	- setup-wizard-navbar_intermediates.jar
	- telephony-common_intermediates.jar
- 整理资源文件，补齐缺失的资源 直到编译通过

###一级选项添加流程 以有线网络为例

1.Settings中一级选项的显示是在**dashboard_categories.xml**中添加的，路径为main\res\xml\，Favorites中有收藏此文件。打开dashboard_categories.xml，选项是按组分类的，如**无线和网络、设备、系统**，我们自定义的无线网络放在无线和网络组中。先创建EthernetFragment，通过title（Wireless&network）找到无线和网络的dashboard-category。
在上述组里添加如下代码

    <dashboard-tile
	 //此id要添加到SettingsActivity的SETTINGS_FOR_RESTRICTED数组中，如果id不存在此数组，加载时会移除
    android:id="@+id/ethernet_settings"
    android:title="有线网络" 
    //此Fragment为之前创建的路径，名称要添加到SettingsActivity的ENTRY_FRAGMENTS数组中
    android:fragment="com.android.settings.shine.EthernetFragment"
    android:icon="@drawable/ic_settings_data_usage"/>

2.在Settings.java中添加如下代码

     public static class EthernetSettingsActivity extends SettingsActivity {  }

3.在manifest中注册EthernetSettingsActivity

     <activity
	    android:name=".Settings$EthernetSettingsActivity"
	    android:configChanges="orientation|keyboardHidden|screenSize"
	    android:label="有线网络设置"
	    android:taskAffinity="">
		    <intent-filter>
		    <action android:name="android.intent.action.MAIN"/>
		    <action android:name="android.settings.ETHERNET_SETTINGS"/>
		    <category android:name="android.intent.category.DEFAULT"/>
		    <category android:name="android.intent.category.VOICE_LAUNCH"/>
		    <category android:name="com.android.settings.SHORTCUT"/>
		    </intent-filter>
	    //当点击添加的有线网络选项时会跳转到此页面
		    <meta-data
			    android:name="com.android.settings.FRAGMENT_CLASS"
			    //真正显示的内容，路径与dashboard_categories.xml中注册的一样
			    android:value="com.android.settings.shine.EthernetFragment"/>
		    <meta-data
	    android:name="com.android.settings.TOP_LEVEL_HEADER_ID"
	    //id与dashboard_categories.xml中注册的一样
	    android:resource="@id/ethernet_settings"/>
        </activity>
 
4.添加有线网络选项的id到SettingsActivity的SETTINGS\_FOR\_RESTRICTED数组中

    private int[] SETTINGS_FOR_RESTRICTED = {
	    R.id.wireless_section,//无线
	    R.id.wifi_settings,//wifi
	    R.id.bluetooth_settings,//蓝牙
	    R.id.data_usage_settings,
	    R.id.ethernet_settings,//有线网络
	    R.id.server_settings,....}
 
5.将EthernetFragment类名添加到SettingsActivity的ENTRY_FRAGMENTS数组

	private static final String[] ENTRY_FRAGMENTS = {
        WirelessSettings.class.getName(),
        EthernetFragment.class.getName(),//有线网络
        ServerSettingFragment.class.getName(),//服务器设置
        OnOffSettingFragment.class.getName(),//开关机设置
		...}


----------
### 添加退出选项

退出不需要进入子页面，则不需要注册Activity和添加Fragment。同样打开dashboardcategories.xml，我们把退出选项添加在系统组里，知道title为System的dashboard-category，添加如下代码

	 <dashboard-tile
		//此id可作为身份判断
        android:id="@+id/exit_settings"
        android:title="退出"
        android:icon="@drawable/ic_settings_about"
        />

直接在DashboardTileView的OnClick方法中添加退出点击处理

	public void onClick(View v) {
        if (mTile.fragment != null) {
            Utils.startWithFragment(getContext(), mTile.fragment, mTile.fragmentArguments, null, 0,
                    mTile.titleRes, mTile.getTitle(getResources()));
        } else if (mTile.intent != null) {
            int numUserHandles = mTile.userHandle.size();
            if (numUserHandles > 1) {
                ProfileSelectDialog.show(((Activity) getContext()).getFragmentManager(), mTile);
            } else if (numUserHandles == 1) {
                getContext().startActivityAsUser(mTile.intent, mTile.userHandle.get(0));
            } else {
                getContext().startActivity(mTile.intent);
            }
        }else if (mTile.id == R.id.exit_settings) {
            //点击退出按钮退出应用
            ((SettingsActivity)getContext()).finish();
        }
    }

###移除选项，比如位置选项
在SettingsActivity中的updateTilesList()方法里添加如下代码

	private void updateTilesList(List<DashboardCategory> target) {
		....省略一批代码
	 // Ids are integers, so downcasting is ok
        int id = (int) category.id;
        int n = category.getTilesCount() - 1;
        while (n >= 0) {
		....省略一批代码
	    	else if (id == R.id.location_settings) {
	         removeTile=true;
	       }
		....省略一批代码
		n--;
		}

----------


6.0系统默认永不休眠，要显示这个选项，打开app\src\main\res\values\custom_config.xml文件，修改代码如下

	 <bool name="config_show_screen_off_never_timeout" translatable="false">true</bool>


 系统默认的高亮色比较淡，不容易区分条目是否被选中，打开\src\main\res\values\themes.xml文件，在如下样式中添加自定义背景色

 	<style name="Theme.Settings" parent="Theme.SettingsBase">
	...省略其他样式设置
	//修改高亮色
	<item name="android:colorControlHighlight">@color/memory_user_light</item>
    </style>

屏幕旋转的处理，显示选项和后台都能控制设备的旋转，前提是Launcher允许桌面旋转（具体操作查看Launcher.apk的修改记录），否则只会应用旋转，桌面不会同步旋转；代码如下，需在子线程中执行：

 	IWindowManager wm = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
	//保存到全局
    Settings.System.putInt(getContentResolver(),Settings.System.USER_ROTATION, value);
	//保存到数据库，与后台同步
    ShineDataBaseHelper.getInstance(this).save(Common.DESKTOP_ORIENTATION, data);
    try {
        wm.freezeRotation(value);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        wallpaperManager.setBitmap(wallpaperManager.getBitmap());
        Log.d(TAG, "设置旋转完成");
    } catch (RemoteException | IOException e) {
        Log.d(TAG, "设置旋转异常");
        e.printStackTrace();
    }


提示音和通知选项，在src\main\java\com\android\settings\notification\NotificationSettings.java文件中移除了**闹钟和铃音设置**，只留下媒体音量，其功能实现在src\main\java\com\android\settings\notification\VolumeSeekBarPreference.java文件中。
主要改动有修改了SeekBarPreference的源码，否则SeekBar控件找不到报空，打开src\main\res\layout\preference_volume_slider.xml，修改id
	
	<SeekBar 
		//@*android:id/seekbar 源代码
		android:id="@+id/seekbar"
        android:layout_marginStart="24dp"
        android:layout_gravity="center_vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

拷贝SeekBarPreference源码到项目，使VolumeSeekBarPreference继承之，本项目路径为E:\workspace_a64\Settings\app\src\main\java\com\android\settings\shine\SeekBarPreference.java。修改SeekBarPreference的onBindView方法：

	@Override
    protected void onBindView(View view) {
        super.onBindView(view);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekbar);
	//  SeekBar seekBar = (SeekBar) view.findViewById( com.android.internal.R.id.seekbar);
        ...
    }

打开VolumeSeekBarPreference.java文件，添加遥控器调节音量的响应，具体修改参考源文件

 	@Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
                    || keyCode == KeyEvent.KEYCODE_EQUALS) {
                mSeekBar.setProgress(mSeekBar.getProgress() + 1);
                mVolumizer.onProgressChanged(mSeekBar,mSeekBar.getProgress(),true);
                return true;
            }
            if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                mSeekBar.setProgress(mSeekBar.getProgress() - 1);
                mVolumizer.onProgressChanged(mSeekBar,mSeekBar.getProgress(),true);
                return true;
            }
        }
        return false;
    }

显示输入法，在系统完成Boot，调用以下方法，在SettingProvider中没有找到默认设置

	Settings.Secure.putInt(getContentResolver(), "show_ime_with_hard_keyboard", 1);

修改PhoneWindowManager.java源码，设定对F12按键响应，使遥控器对设置应用的直接调用。找到interceptKeyBeforeDispatching方法添加如下代码：

 	@Override
    public long interceptKeyBeforeDispatching(WindowState win, KeyEvent event, int policyFlags) {
	...
	...
	 if (keyCode == KeyEvent.KEYCODE_HOME) {
	...响应首页键
	...其他按键
	}else if(keyCode == KeyEvent.KEYCODE_F12){
        if(down){
			//应用的首页需在清单文件添加类别android.intent.category.SETTINGS
            Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, "android.intent.category.SETTINGS");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                mContext.startActivityAsUser(intent, UserHandle.CURRENT);
            } catch (ActivityNotFoundException ex) {
               ...
            }
        }
            return -1;   
        }
	....