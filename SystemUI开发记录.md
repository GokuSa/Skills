### SystemUI.apk 6.0 开发记录 
此应用负责系统级别的UI控制，其源码在frameworks/base/packages目录下。其apk在设备上的位置:/system/priv-app/SystemUI。本次对SystemUI的修改是对亮度的调节。

E:\workspace_a64\SystemUI\app\src\main\java\com\android\systemui\settings\BrightnessController.java

源码在BrightnessController构造方法中初始化亮度范围

	 mMinimumBacklight = pm.getMinimumScreenBrightnessSetting();
     mMaximumBacklight = pm.getMaximumScreenBrightnessSetting();

由于我们的设备差异，系统默认的亮度参数并不适用，所以使用自定义的
	
	//获取我们为设备自定义的亮度参数
	int[] customBackLight = getCustomBackLight();
	//如果有效使用自定义的，否则使用系统的
    if (customBackLight[0] > 0 && customBackLight[1] > 0) {
           mMinimumBacklight = customBackLight[0];
           mMaximumBacklight = customBackLight[1];
     }else{
           mMinimumBacklight = pm.getMinimumScreenBrightnessSetting();
          mMaximumBacklight = pm.getMaximumScreenBrightnessSetting();
     }


从本地文件获取亮度参数的方法

	 private int[] getCustomBackLight() {
        int[] custom=new int[2];
		//自定义的亮度配置文件，由硬件相关人员负责设置
        File file=new File("/extdata/work/show/system/display.ini");
        Properties properties=new Properties();
        try {
            properties.load(new FileInputStream(file));
            String min= properties.getProperty("min");
            String max = properties.getProperty("max");
            custom[0]=Integer.parseInt(min);
            custom[1]=Integer.parseInt(max);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return custom;
    }