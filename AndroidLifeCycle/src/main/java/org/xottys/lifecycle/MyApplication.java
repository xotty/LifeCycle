/**Application的生命周期演示
 * 1) onCreate().................在App创建时执行
 * 2) onTerminate()..............在App终止时执行，通常由Android系统在低内存清理时才会触发
 * 3) onLowMemory()..............在系统内存不足的时执行
 * 4) onTrimMemory().............在系统内存不足的时执行
 * 5) onConfigurationChanged.....当配置信息改变的时执行，如屏幕旋转、语言切换时
 *
 * 另外本程序还提供了演示Activity生命骤周期用到的工具类，通过反射方式获取Activity栈内容(无序)
 * 按照"栈顶-中间-栈底"的顺序输出其中Activity的名称
 */
package org.xottys.lifecycle;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.Log;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyApplication extends Application {
    private static final String TAG = "ApplicationLC";;

    //构造全部Context
    private static Context mContext;

    //用于在普通类（非Activity、Service）中获得MyAppliction实例（系统单例）
    private static MyApplication myApplication;

    public
    static MyApplication getMyApplication() {
        return myApplication;
    }

    static String appStatus;

    public static boolean isFirstSetUserVisibleHint;

    Handler mHandler;

    // 程序创建的时候执行
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "这里是Android程序入口");
        Log.d(TAG, "MyApplication-->onCreate");
        appStatus="MyApplication-->onCreate";
        myApplication = this;
        mContext = getApplicationContext();
        instance = this;
        manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

//      mHandler.sendEmptyMessage(0);
    }

    // 程序终止的时候执行
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "MyApplication-->onTerminate");
        appStatus="MyApplication-->onTerminate";
        mHandler.sendEmptyMessage(0);
    }

    //低内存的时候执行
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "MyApplication-->onLowMemory");
        appStatus="MyApplication-->onLowMemory";
//      mHandler.sendEmptyMessage(0);

    }

    //低内存的时候执行，用来代替onLowMemory()
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "MyApplication-->onTrimMemory:"+level);
        appStatus="MyApplication-->onTrimMemory,level="+level;
//      mHandler.sendEmptyMessage(0);

    }

    //配置变化（如语言、屏幕方向切换）时执行，屏幕方向变化时newConfig取值如下：
    //Configuration.ORIENTATION_LANDSCAPE，Configuration.ORIENTATION_PORTRAIT
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "MyApplication-->onConfigurationChanged");
        appStatus="MyApplication-->onConfigurationChanged";
        mHandler.sendEmptyMessage(0);
    }
    private static MyApplication instance;
    private ActivityManager manager;

    public static MyApplication getInstance() {
        return instance;
    }

    private List<Activity> getActivitiesByApplication(Application application) {
        List<Activity> list = new ArrayList<>();
        try {
            Class<Application> applicationClass = Application.class;
            Field mLoadedApkField = applicationClass.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mLoadedApk = mLoadedApkField.get(application);
            Class<?> mLoadedApkClass = mLoadedApk.getClass();
            Field mActivityThreadField = mLoadedApkClass.getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            Object mActivityThread = mActivityThreadField.get(mLoadedApk);
            Class<?> mActivityThreadClass = mActivityThread.getClass();
            Field mActivitiesField = mActivityThreadClass.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(mActivityThread);
            // 注意这里一定写成Map，低版本这里用的是HashMap，高版本用的是ArrayMap
            if (mActivities instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<Object, Object> arrayMap = (Map<Object, Object>) mActivities;
                int seq = 0;
                for (Map.Entry<Object, Object> entry : arrayMap.entrySet()) {
                    Object value = entry.getValue();
                    Class<?> activityClientRecordClass = value.getClass();
                    Field activityField = activityClientRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Object o = activityField.get(value);
                    list.add((Activity) o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    public String createtActivityStackInfo() {

        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(5);
        String base = runningTasks.get(0).baseActivity.getShortClassName();
        base = base.substring(1);
        String top = (runningTasks.get(0).topActivity.getShortClassName()).substring(18);
        int swithcNumber = runningTasks.get(0).numActivities;
        List<Activity> list;
        String str = "";
        StringBuilder midStr = new StringBuilder("");
        switch (swithcNumber) {
            case 0:
                break;
            case 1:
                str = top;
                break;
            case 2:
                str = top + "\n\u21E7\n" + base;
                break;
            default:
                boolean topflag = false;
                boolean baseflag = false;
                list = getActivitiesByApplication(this);
                for (Activity aty : list) {
                    if (!aty.isFinishing()) {
                        if (!aty.getLocalClassName().equals("SingleInstanceActivity")) {
                            if (aty.getLocalClassName().equals(top)) {
                                if (topflag)
                                    midStr.append(aty.getLocalClassName().substring(17) + "/");
                                else
                                    topflag = true;
                            } else if (aty.getLocalClassName().equals(base)) {
                                if (baseflag)
                                    midStr.append(aty.getLocalClassName().substring(17) + "/");
                                else
                                    baseflag = true;
                            } else
                                midStr.append(aty.getLocalClassName().substring(17) + "/");
                        }
                    }
                }
                if (midStr.length() > 0) {
                    midStr.deleteCharAt(midStr.length() - 1);
                }
                str = top + "\n\u21E7\n(" + midStr + ")\n\u21E7\n" + base;
                break;
        }
//        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
//            Log.v(TAG, "Task ID:"+runningTaskInfo.id + "---Activity数量:" + runningTaskInfo.numActivities + "---Base:" + runningTaskInfo.baseActivity.getShortClassName() + "---Top:" + (runningTaskInfo.topActivity.getShortClassName()).substring(18));
//        }
        return ("Task ID:" + runningTasks.get(0).id + "     当前Activity数量:" + runningTasks.get(0).numActivities + "\n\n\n" + str);
    }

}