[TOC]

## 使用

### 升级

```java
package com.android.weici.senior.student.global;

import android.util.Log;

import com.lib.basex.global.LVersionUpdate;

/**
 * @author Alan
 * 时 间：2020-09-10
 * 简 述：<功能简述>
 */
public class AppUpdate extends LVersionUpdate {

    @Override
    public boolean onUpdate() {
        Log.d("test_lbasex", "onUpdate");
        return true;
    }
}
```

```java
public class App extends LApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        new AppUpdate().update();
    }
}
```

### 监听后台拉起

```java
 /**
 * 从后台拉到 前台
 *
 * @param activity
 */
 @Override
 public void onForegroundListener(Activity activity) {

}

/**
* 拉到后台
*/
@Override
public void onBackgroundListener() {

}

/**
 * 创建页面
 * @param activity
 */
@Override
public void onCreateListener(Activity activity) {

}
```

### 全局异常捕获

```java
/**
* 捕获到异常
*
* @param t
* @param e
*/
@Override
public void uncaughtException(Thread t, Throwable e) {
		Logger.error(e);
}
```

