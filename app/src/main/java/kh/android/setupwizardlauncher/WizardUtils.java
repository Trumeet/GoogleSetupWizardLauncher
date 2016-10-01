package kh.android.setupwizardlauncher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Project SetupWizardLauncher
 * <p/>
 * Created by 宇腾 on 2016/10/1.
 * Edited by 宇腾
 */
public class WizardUtils {
    public static final String[] EXEC_SHELL = {
    "pm enable com.google.android.setupwizard",
    "pm grant com.google.android.setupwizard android.permission.READ_PHONE_STATE",
    "pm grant com.google.android.setupwizard android.permission.CALL_PHONE",
    "pm grant com.google.android.setupwizard android.permission.WRITE_CONTACTS",
    "pm grant com.google.android.setupwizard android.permission.PROCESS_OUTGOING_CALLS",
    "pm grant com.google.android.setupwizard android.permission.GET_ACCOUNTS",
    "pm grant com.google.android.setupwizard android.permission.READ_CONTACTS",
    "pm grant com.google.android.setupwizard android.permission.WRITE_SECURE_SETTINGS",
    "pm enable com.google.android.setupwizard/com.google.android.setupwizard.SetupWizardTestActivity",
    "pm enable com.google.android.setupwizard/com.google.android.setupwizard.SetupWizardExitActivity",
    "pm enable com.google.android.setupwizard/com.google.android.setupwizard.SetupWizardActivity",
    "am start -n com.google.android.setupwizard/.SetupWizardTestActivity"};
    public static final String PREFS_NEW = "new";
    Context mContext;
    public WizardUtils (Context context) {
        mContext = context;
    }
    public AlertDialog.Builder showAlert (DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.title_alert);
        builder.setMessage(R.string.alert);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, listener);
        builder.setNegativeButton(android.R.string.cancel, null);
        return builder;
    }
    public AlertDialog.Builder notInstall () {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(R.string.error_not_install);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setCancelable(false);
        return builder;
    }
    public AlertDialog.Builder showInfo () {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.action_info);
        builder.setMessage(R.string.text_info);
        builder.setPositiveButton(R.string.action_web, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.startUrl(mContext, "https://lyt54.wordpress.com");
            }
        });
        return builder;
    }
}
