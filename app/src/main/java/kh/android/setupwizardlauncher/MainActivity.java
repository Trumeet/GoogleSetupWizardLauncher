package kh.android.setupwizardlauncher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.github.jorgecastilloprz.FABProgressCircle;

import java.io.DataOutputStream;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    WizardUtils mUtils;
    SharedPreferences mPrefs;
    FABProgressCircle mFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        mUtils = new WizardUtils(mContext);
        mFab = (FABProgressCircle)findViewById(R.id.fab_progress);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (Utils.isPackageExisted(mContext, Utils.WIZARD_PKG_NAME)) {
                    mUtils.showAlert(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            start(view);
                        }
                    }).show();
                } else {
                    mUtils.notInstall().show();
                }
            }
        });
        ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton_info);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUtils.showInfo().show();
            }
        });
        if (mPrefs.getBoolean(WizardUtils.PREFS_NEW, true)) {
            mUtils.showInfo().show();
            mPrefs.edit().putBoolean(WizardUtils.PREFS_NEW, false).apply();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    private void start (View v) {
        Snackbar.make(v, R.string.text_start, Snackbar.LENGTH_SHORT).show();
        mFab.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataOutputStream dataOutputStream = null;
                try {
                    Process process = Runtime.getRuntime().exec("su");
                    dataOutputStream = new DataOutputStream(process.getOutputStream());
                    for (String cmd : WizardUtils.EXEC_SHELL) {
                        //Runtime.getRuntime().exec(new String[]{"/system/bin/su","-c", cmd});
                        dataOutputStream.writeBytes(cmd + "\n");
                        dataOutputStream.flush();
                    }
                    dataOutputStream.writeBytes("exit\n");
                    dataOutputStream.flush();
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mFab.hide();
                            Utils.error(e, mContext).show();
                        }
                    });
                } finally {
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mFab.hide();
                                }
                            });
                        } catch (final Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mFab.hide();
                                    Utils.error(e, mContext).show();
                                }
                            });
                        }
                    }
                }
            }
        }).start();
    }
}
