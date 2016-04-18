package senori.or.jp.sharering.info;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import senori.or.jp.sharering.R;


/**
 * Created by JupiteR on 2016-02-14.
 */
public class CDlalog {

    private Dialog dialog;
    private static CDlalog cDlalog;


    public CDlalog(Context context) {

        dialog = new Dialog(context, R.style.mydialg);
        ProgressBar progressBar = new ProgressBar(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.addContentView(progressBar, params);

    }

    public void show() {
        dialog.show();
    }

    public void cancel() {
        dialog.cancel();

    }
}
