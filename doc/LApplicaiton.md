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

