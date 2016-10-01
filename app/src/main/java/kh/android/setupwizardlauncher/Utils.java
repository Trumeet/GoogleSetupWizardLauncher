package kh.android.setupwizardlauncher;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Project SetupWizardLauncher
 * <p/>
 * Created by 宇腾 on 2016/10/1.
 * Edited by 宇腾
 */
public class Utils {
    public static final String WIZARD_PKG_NAME = "com.google.android.setupwizard";
    public static boolean isPackageExisted(Context context, String targetPackage){
        PackageManager pm=context.getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }
    public static AlertDialog.Builder error (Exception e, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(e.getLocalizedMessage());
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setCancelable(false);
        return builder;
    }
    public static void startUrl (Context context, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }
}
