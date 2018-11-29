package org.fly.xposed_test.app;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class System implements IApp {

    private XC_LoadPackage.LoadPackageParam loadPackageParam;
    private ClassLoader classLoader;

    public static boolean findTutorial008 = false;

    public static boolean isFindTutorial008() {
        return findTutorial008;
    }

    public static void setFindTutorial008(boolean v)
    {
        findTutorial008 = v;
    }

    public System(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        this.loadPackageParam = loadPackageParam;
    }

    @Override
    public void hook() {
        synchronized(System.class)
        {
            if (!isFindTutorial008())
            {
                XposedBridge.log("-----System hook-----");
                XposedHelpers.findAndHookMethod(Class.class, "forName", String.class, Boolean.TYPE, ClassLoader.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        //super.afterHookedMethod(param);

                        if ("de.robv.android.xposed.mods.tutorial.Tutorial008".equals(param.args[0]) && !isFindTutorial008())
                        {
                            XposedBridge.log("HOOK Tutorial008 in System: " + ((ClassLoader)(param.args[2])).toString());

                            setFindTutorial008(true);

                            hook008InXposed((ClassLoader)param.args[2]);
                        }
                    }
                });
            }

        }

    }

    public static void hook008InXposed(ClassLoader classLoader)
    {
        // 解密类，替换成JSON解码
        XposedHelpers.findAndHookMethod("com.a.a.a", classLoader,"b", String.class, Apk008.jsonToE(classLoader));

        // 所有HTTP GET
        XposedHelpers.findAndHookMethod("com.lishu.c.u", classLoader, "run", Apk008.HttpGet(classLoader));

        // 所有HTTP POST
        XposedHelpers.findAndHookMethod("com.lishu.c.q", classLoader, "run", Apk008.HttpPost(classLoader));

        Apk008.hookTutorial(classLoader);


    }
}
