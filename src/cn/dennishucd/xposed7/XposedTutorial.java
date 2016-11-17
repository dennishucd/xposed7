package cn.dennishucd.xposed7;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedTutorial implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.systemui")) {
        	return;
        } else {
        	XposedBridge.log("dennis: Loaded app: " + lpparam.packageName);
        }
            

        findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            	TextView tv = (TextView) param.thisObject;
                String text = tv.getText().toString();
                tv.setText(text + " :)");
                tv.setTextColor(Color.RED);
                Log.e("dennis", "hook success!");
            }
    });
    }
}
