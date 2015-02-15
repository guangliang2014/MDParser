### 前因

前两天入手了一台MX4 Pro。自己拿到真机之后，着实高兴了一阵，立刻把玩了一会。!虽然MX4 Pro!的小圆圈没了，但是为了指纹传感器，因此这点我忍了。
![MX4 Pro](http://fyales.qiniudn.com/MX4_PRO.jpg)
但是我发现了一个问题：就是原来常按小圆点之后的锁屏功能没了，现在必须要按下去之后才能实现锁屏。每次锁屏那“卡”的一声对于我来说简直就是折磨------我的指纹传感器被按坏了怎么办。。。。。。。于是，决定自己写一个很简单的一键锁屏程序（[Github地址](https://github.com/weidouble/lockScreen)）。
![MX4 Pro](http://fyales.qiniudn.com/MX4_PRO.jpg)
### 开始
自从安卓2.2以后，Android通过提供[设备管理API](http://developer.android.com/guide/topics/admin/device-admin.html#lock)对设备进行系统级别的操作，比如远程擦除数据，锁屏功能等。在这里，我们就需要用到这个API来实现一键锁屏功能。

首先我们需要在res/xml目录下建一个xml文件，这个文件用来说明我们想实现什么样的策略，例如这样：

	<device-admin xmlns:android="http://schemas.android.com/apk/res/android">
		<uses-policies>
  			<limit-password />
    		<watch-login />
   			<reset-password />
    		<force-lock />
    		<wipe-data />
    		<expire-password />
    		<encrypted-storage />
    		<disable-camera />
  		</uses-policies>
  	</device-admin>
  	
在上面的文件列出了你想要实现的策略，比如禁用摄像头，擦除数据，锁屏等，你想要实现想要的策略都必须在这里进行定义，如果你没在这里进行定义，将会导致SecurityException。在这里，我们只需要实现锁屏策略，因此，只需要以下代码就行了：

	<?xml version="1.0" encoding="UTF-8"?>
		<device-admin xmlns:android="http://schemas.android.com/apk/res/android" >
    		<uses-policies>
        		<force-lock />
   			</uses-policies>
	</device-admin>

之后你要写一个Receiver,这个Receiver必须继承DeviceAdminReceiver(仅仅需要继承即可，一般情况下啊，你其实不需要写任何东西)。这个Receiver用来接受系统的消息，即你是否可以得到使用这些策略的授权。因此，这个Receiver必须包含以下内容：

* 必须具有BIND_DEVICE_ADMIN的权限
* 必须可以响应ACTION_DEVICE_ADMIN_ENABLED的意图，这个定义在manifest文件中。

manifest文件定义如下：

	<receiver
		android:name=".DeviceFyalesReceiver"
 		android:description="@string/app_name"
 		android:label="@string/app_name"
 		android:permission="android.permission.BIND_DEVICE_ADMIN"
	>
		<meta-data
			android:name="android.app.device_admin"
			android:resource="@xml/lock_screen"
		/>
		<intent-filter >
			<action android:name="android.app.action.DEVICE_ADMIN_ENABLED"/>
		</intent-filter>
	</receiver>

接下来就是写Activity了：

	package fyales.com.lockscreen;
    import android.app.Activity;
    import android.app.admin.DevicePolicyManager;
    import android.content.ComponentName;
    import android.content.Context;
    import android.content.Intent;
    import android.os.*;
    import android.os.Process;
    
    /**
    *	@author fyales
    *  @date 2015-01-10
    */
    public class MainActivity extends Activity {
        private DevicePolicyManager devicePolicyManager;
        private ComponentName componentName;
        private final static int LOCK_SCREEN_CODE = 201;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
    
            devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            componentName = new ComponentName(this,DeviceFyalesReceiver.class);
            lockScreen();
            android.os.Process.killProcess(Process.myPid());
    
        }
    
        /**
         * 实现系统锁屏
         */
        private void lockScreen(){
            if (devicePolicyManager.isAdminActive(componentName)){
                devicePolicyManager.lockNow();
            }else{
                 Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                 intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                 startActivityForResult(intent, LOCK_SCREEN_CODE);
            }
    
        }
    
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case LOCK_SCREEN_CODE:
                        devicePolicyManager.lockNow();
                        break;
                    default:
                        break;
                }
    
            }
        }
    }

这边的DevicePolicyManager就是管理设备策略的。这里需要注意的一点就是，因为我们是实现锁屏功能的，因此，当activity开始执行后，当锁屏成功会后，应该立刻杀死该进程，所以我们调用了android.os.Process.killProcess(Process.myPid())方法。

### 优化
我们的程序到这里差不多就告一段落了，但是我在运行的时候发现一个问题，就是点击一键锁屏的图标时，它有一个打开窗口的事件发生。这在MX4 Pro上不是很明显，但是在MX3上就显而易见了。在MX3上，整个屏幕会先白一下，然后再会锁屏，这个很令人抓狂，因此，我们必须要实现窗口不显示，直接锁屏的功能。

原来我想了各种方法，比如用Service或者Widget啊，但是他们有各自的短板。最后，我找到了一个很好的方法就是设置android:theme="@android:style/Theme.NoDisplay"。完美解决！

最后，容许我晒一张MX4 Pro的图片，哈哈。

口号：Make things interesting!

参考了下面的博客

[http://www.cnblogs.com/sommer/p/3238060.html](http://www.cnblogs.com/sommer/p/3238060.html)
<br />
<br />
![MX4 Pro](http://fyales.qiniudn.com/MX4_PRO.jpg)
<br />
<br />


