### Launcher3.apk 5.1开发记录
Launcher3.apk 是android系统的桌面应用。其源码在frameworks/base/packages/apps目录下。其apk在设备上的位置:/system/priv-app/Launcher3/。

launcher3\src\main\java\com\android\launcher3\Launcher.java

	 private void setupViews() {
		...
		 mSearchDropTargetBar = (SearchDropTargetBar)
                mDragLayer.findViewById(R.id.search_drop_target_bar);
        //隐藏首页搜索栏
        mSearchDropTargetBar.setVisibility(View.GONE);
		....
		}

同一热键在水平方向

1 修改launcher3\src\main\res\values\config.xml

	<!-- android会根据不同设备水平或垂直显示热键的方向，在此改为false，表示统一水平，不转置 -->
    <bool name="hotseat_transpose_layout_with_orientation">false</bool>

2 修改Launcher64\launcher3\src\main\java\com\android\launcher3\Hotseat.java

	protected void onFinishInflate() {
       ...
        mAllAppsButtonRank = grid.hotseatAllAppsRank;
        mContent = (CellLayout) findViewById(R.id.layout);
		//本来判断设备是那种布局和类型，决定横向还是竖向的排布热键
       /* if (grid.isLandscape && !grid.isLargeTablet()) {
            mContent.setGridSize(1, (int) grid.numHotseatIcons);
        } else {
            mContent.setGridSize((int) grid.numHotseatIcons, 1);
        }*/
        //改成都水平排布，即一行多列
        mContent.setGridSize((int) grid.numHotseatIcons, 1);
        mContent.setIsHotseat(true);
        resetLayout();
    }


修改launcher3\src\main\res\values\config.xml

	<!--允许屏幕旋转-->
    <bool name="allow_rotation">true</bool>


修改launcher3\src\main\java\com\android\launcher3\LauncherClings.java

	 //不显示开机小提示
    private static final boolean DISABLE_CLINGS = true;