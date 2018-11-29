package org.fly.xposed_test.app;

import android.app.AndroidAppHelper;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Apk008 implements IApp{

    private ClassLoader classLoader;
    private Class LoadActivity;
    private Class StartActivity;
    private Class Apk008Activity;
    private Class PoseHelper008;
    private Class SimulateDataTest;

    private static final String TAG = "[008] ";

    public Apk008(ClassLoader classLoader) {
        this.classLoader = classLoader;
        XposedBridge.log(TAG + "FOUND ");

        LoadActivity = XposedHelpers.findClass("com.soft.apk008.LoadActivity", classLoader);
        StartActivity = XposedHelpers.findClass("com.soft.apk008.StartActivity", classLoader);
        Apk008Activity = XposedHelpers.findClass("com.soft.apk008.Apk008Activity", classLoader);
        PoseHelper008 = XposedHelpers.findClass("de.robv.android.xposed.mods.tutorial.PoseHelper008", classLoader);
        SimulateDataTest = XposedHelpers.findClass("com.data.simulate.SimulateDataTest", classLoader);
    }

    @Override
    public void hook() {

        // 解密类，替换成JSON解码
        XposedHelpers.findAndHookMethod("com.a.a.a", classLoader,"b", String.class , jsonToE(classLoader));

        // 所有HTTP GET
        XposedHelpers.findAndHookMethod("com.lishu.c.u", classLoader, "run", HttpGet(classLoader));

        // 所有HTTP POST
        XposedHelpers.findAndHookMethod("com.lishu.c.q", classLoader, "run", HttpPost(classLoader));

        //hookTutorial(classLoader);

        // LoadActivity 网络callback函数
        XposedHelpers.findAndHookMethod(LoadActivity, "a", String.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {

                int a = 1;
                int b = 2;
                XposedHelpers.setStaticIntField(LoadActivity, "a", a);
                XposedHelpers.setStaticIntField(LoadActivity, "b", b);
                XposedHelpers.setStaticObjectField(StartActivity, "e", XposedHelpers.callStaticMethod(LoadActivity, "stringFromJNI", param.thisObject, a, b));
                XposedHelpers.setStaticIntField(PoseHelper008, "a", a);
                XposedHelpers.setStaticIntField(PoseHelper008, "b", b);

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.soft.apk008v", "com.soft.apk008.StartActivity"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AndroidAppHelper.currentApplication().startActivity(intent);

                return null;
            }
        });

        // StartActivity 网络callback函数
        XposedHelpers.findAndHookMethod(StartActivity, "a", String.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {

                XposedHelpers.setStaticObjectField(StartActivity, "c", "OK"); // systemMsg
                XposedHelpers.setStaticObjectField(StartActivity, "i", "/DownLoadFileAction.action?id="); // promoteUrl
                XposedHelpers.setStaticObjectField(StartActivity, "j", "http://api2.apk008.com/lishu008AppManager/AuToAddDays.action"); // sellUrl
                XposedHelpers.setStaticBooleanField(StartActivity, "f", true); // vip

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.soft.apk008v", "com.soft.apk008.Apk008Activity"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AndroidAppHelper.currentApplication().startActivity(intent);

                return null;
            }
        });

        XposedHelpers.findAndHookMethod(Apk008Activity, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                XposedBridge.log(TAG + "Apk008Activity.onCreate After ---------------------");

                ArrayList apiLevelMap = (ArrayList)XposedHelpers.getStaticObjectField(SimulateDataTest, "apiLevelMap");

                apiLevelMap.add(new String[]{"23", "6.0"});
                apiLevelMap.add(new String[]{"24", "7.0"});
                apiLevelMap.add(new String[]{"25", "7.1.1"});
                apiLevelMap.add(new String[]{"26", "8.0"});
                apiLevelMap.add(new String[]{"27", "8.1"});
                apiLevelMap.add(new String[]{"28", "9"});

                HashMap map = (HashMap)XposedHelpers.getStaticObjectField(XposedHelpers.findClass("com.soft.apk008.cu", classLoader), "e");
                map.put("21", "5.0");
                map.put("22", "5.1");
                map.put("23", "6.0");
                map.put("24", "7.0");
                map.put("25", "7.1.1");
                map.put("26", "8.0");
                map.put("27", "8.1");
                map.put("28", "9");

                apiLevelMap = (ArrayList)XposedHelpers.getStaticObjectField(XposedHelpers.findClass("com.data.simulate.a", classLoader), "a");
                apiLevelMap.add(new String[]{"22", "5.1"});
                apiLevelMap.add(new String[]{"23", "6.0"});
                apiLevelMap.add(new String[]{"24", "7.0"});
                apiLevelMap.add(new String[]{"25", "7.1.1"});
                apiLevelMap.add(new String[]{"26", "8.0"});
                apiLevelMap.add(new String[]{"27", "8.1"});
                apiLevelMap.add(new String[]{"28", "9"});

                Object j = XposedHelpers.getStaticObjectField(PoseHelper008, "valueMap");
                XposedBridge.log("j getDeviceId == " + XposedHelpers.callMethod(j, "j", "getDeviceId"));

                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true)
                        {
                            try {
                                XposedHelpers.callStaticMethod(PoseHelper008, "saveDataToFile", "checkError", "0");
                                Thread.sleep(1000);
                            } catch (InterruptedException e)
                            {
                                break;
                            }
                        }
                    }
                }).start();*/

            }
        });

    }

    public static void hookTutorial(final ClassLoader classLoader)
    {
        XposedHelpers.findAndHookMethod("de.robv.android.xposed.mods.tutorial.d", classLoader, "a", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                //super.beforeHookedMethod(param);

                param.setResult(Boolean.valueOf(true));
                XposedBridge.log("tutorial.d.a() in system.");

            }

        });

        XposedHelpers.findAndHookMethod("de.robv.android.xposed.mods.tutorial.d", classLoader, "a", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                //super.beforeHookedMethod(param);

                param.args[0] = "{\"status\":\"true\"}";
                XposedBridge.log("tutorial.d.a(string) in system.");

            }

        });

        /*XposedHelpers.findAndHookMethod("de.robv.android.xposed.mods.tutorial.Tutorial008", classLoader, "logMsg", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                //super.beforeHookedMethod(param);

                if("checkError".equals(param.args[0])) {
                    param.setResult("0");
                }

                XposedBridge.log("Tutorial008.logMsg() in system.");

            }

        });
*/

    }

    public static XC_MethodHook HttpPost(final ClassLoader classLoader)
    {
        return new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Map<String, String> params = (HashMap)XposedHelpers.getObjectField(param.thisObject, "b");
                String url = (String)XposedHelpers.getObjectField(param.thisObject, "c");
                Object callback = XposedHelpers.getObjectField(param.thisObject, "d");

                String[] segments = url.split("\\?");
                url = "http://192.168.1.30/008.php?" + (segments.length > 1 ? segments[1] : "");

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .build();

                FormBody.Builder builder = new FormBody.Builder();
                StringBuilder uri = new StringBuilder();

                for (Map.Entry<String, String> entry: params.entrySet()
                        ) {

                    uri.append("&")
                            .append(URLEncoder.encode(entry.getKey(), "utf-8"))
                            .append("=");
                    if (entry.getValue() != null)
                        uri.append(URLEncoder.encode(entry.getValue(), "utf-8"));
                }

                url += uri.toString();

                Request request = new Request.Builder()
                        .post(builder.build())
                        .url(url)
                        .build();

                XposedBridge.log(TAG + "HTTP POST: " + url);

                Response response = client.newCall(request).execute();

                if (response.isSuccessful())
                {
                    XposedBridge.log(TAG + "HTTP POST OK: " + url);

                    XposedHelpers.callMethod(callback, "a", response.body().string());
                }

                param.setResult(null);
            }
        };
    }

    public static XC_MethodHook HttpGet(final ClassLoader classLoader)
    {
        return new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String url = (String)XposedHelpers.getObjectField(param.thisObject, "b");
                Object callback = XposedHelpers.getObjectField(param.thisObject, "c");

                String[] segments = url.split("\\?");
                url = "http://192.168.1.30/008.php?" + (segments.length > 1 ?  segments[1] : "");

                XposedBridge.log(TAG + "HTTP GET: " + url);

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful())
                {
                    XposedBridge.log(TAG + "HTTP GET OK: " + url);

                    XposedHelpers.callMethod(callback, "a", response.body().string());
                }

                param.setResult(null);
            }
        };
    }

    public static XC_MethodHook jsonToE(final ClassLoader classLoader)
    {
        return new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param)
                    throws Throwable {

                String json = (String)param.args[0];

                XposedBridge.log(TAG + "JSON Parser:" + json);

                Map<String, Object> res = new HashMap<>();

                if (json != null && !json.isEmpty())
                {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.enable(JsonParser.Feature.IGNORE_UNDEFINED);
                    objectMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
                    objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
                    objectMapper.enable(JsonParser.Feature.ALLOW_TRAILING_COMMA);
                    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                    res = objectMapper.readValue((String)param.args[0], new TypeReference<Map<String, Object>>(){});
                }

                if (res.containsKey("getDeviceId"))
                {
                    XposedBridge.log(TAG + " Fake IMEI:" + res.get("getDeviceId"));
                }

                // 将这个解密函数直接替换成JSON解析函数
                Class e = XposedHelpers.findClass("com.a.a.e", classLoader);
                param.setResult(XposedHelpers.newInstance(e, res));
            }
        };
    }
}
