package org.fly.xposed_test;

import org.fly.xposed_test.app.Apk008;
import org.fly.xposed_test.app.IApp;
import org.fly.xposed_test.app.System;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookLogic implements IXposedHookLoadPackage {



    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        IApp app = null;
        switch (loadPackageParam.packageName)
        {
            case "com.soft.apk008v":
                app = new Apk008(loadPackageParam.classLoader);
                break;
            default:
                app = new System(loadPackageParam);
                break;
        }
        if (app != null)
            app.hook();
    }

}
