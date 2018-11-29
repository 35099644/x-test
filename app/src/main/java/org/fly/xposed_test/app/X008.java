package org.fly.xposed_test.app;

import java.io.File;
import java.io.IOException;
import java.util.regex.MatchResult;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class X008 implements IApp {
    private ClassLoader classLoader;

    public X008(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void hook() {
        XposedBridge.log("Decode X008 --");
        new Thread(new Runnable() {
            @Override
            public void run() {

                    /*while(true)
                    {
                        try {
                            BufferedSource source = Okio.buffer(Okio.source(new File("/storage/emulated/0/Pictures/encode.txt")));
                            BufferedSink sink = Okio.buffer(Okio.sink(new File("/storage/emulated/0/Pictures/decode.txt")));
                            String str = source.readUtf8();
                            source.close();
                            //XposedBridge.log(str);

                            if (!str.isEmpty())
                            {
                                final CallbackMatcher callbackMatcher = new CallbackMatcher("b\\.ʻ\\(\"([a-z0-9A-Z\\+/=]*?)\"\\)");
                                str = callbackMatcher.replaceMatches(str, new CallbackMatcher.Callback() {
                                    @Override
                                    public String foundMatch(MatchResult matchResult) {
                                        try {

                                            return "\"" + (String) XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.a.a.a.a.b", classLoader), "ʻ", matchResult.group(1)) + "\"";
                                        } catch (Exception e)
                                        {
                                            return matchResult.group(0);
                                        }
                                    }
                                });

                                sink.writeUtf8(str);
                                sink.close();
                                //XposedBridge.log(str);

                            }

                            Thread.sleep(1000);
                        } catch (IOException e)
                        {
                            XposedBridge.log(e);
                        } catch (InterruptedException e)
                        {
                            break;
                        }

                    }*/

            }
        }).start();
    }
}
