package org.fly.xposed_test;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Test implements IXposedHookLoadPackage {

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("Loaded org.fly.xposed_test.app: " + lpparam.packageName);
        Log.d("XP","ssssssssss5555ssssssss000000----------------------");
    }

}
