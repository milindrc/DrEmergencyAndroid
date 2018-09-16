package com.matrixdev.dremergency.Helpers;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.matrixdev.dremergency.Controllers.BaseActivity;
import com.matrixdev.dremergency.R;


/**
 * Created by Milind on 15-Jun-17.
 */

public class DialogHelper {

    BaseActivity activity;

    public DialogHelper(BaseActivity activity) {
        this.activity = activity;
    }

    public static DialogHelper with(BaseActivity activity) {
        return new DialogHelper(activity);
    }

    public void confirm(String title, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder
                .setTitle(title)
                .setMessage("Are you sure?")
                .setIcon(R.mipmap.exit)
                .setPositiveButton(android.R.string.yes, listener)
                .setNegativeButton(android.R.string.no, null).create();

        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    public void confirm(String title, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder
                .setTitle(title)
                .setMessage("Are you sure?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, positiveListener)
                .setNegativeButton(android.R.string.no, negativeListener).create();

        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    public void showMessage(DialogInterface.OnClickListener listener, String message, String btn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder

                .setCancelable(false)
                .setMessage(message)
                .setIcon(R.mipmap.exit)
                .setPositiveButton(btn, listener)
                .setNegativeButton(android.R.string.no, null).create();

        dialog.show();
//        new BlurAsyncTask(dialog).execute();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    public void show(String title, DialogInterface.OnClickListener listener, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.mipmap.exit)
                .setPositiveButton(android.R.string.yes, listener)
                .setNegativeButton(android.R.string.no, null).create();

        dialog.show();
//        new BlurAsyncTask(dialog).execute();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    public void showHardVersionUpdate(String title, DialogInterface.OnClickListener listener, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final AlertDialog dialog = builder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, listener)
                .setNegativeButton(android.R.string.no, null)
                .setCancelable(false)
                .create();


        dialog.show();
//        new BlurAsyncTask(dialog).execute();

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(false);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }


    public void showFixed(String title, DialogInterface.OnClickListener listener, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder
                .setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert).create();

        dialog.show();
//        new BlurAsyncTask(dialog).execute();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    public void confirm(String title, DialogInterface.OnClickListener listener, String message) {
//        new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.myDialog))
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder
                .setTitle(title)
                .setMessage("Are you sure, " + message)
                .setIcon(R.mipmap.exit)
                .setPositiveButton(android.R.string.yes, listener)
                .setNegativeButton(android.R.string.no, null).show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    public void exit(String title, DialogInterface.OnClickListener listener, String message) {
//        new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.myDialog))
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.mipmap.exit)
                .setPositiveButton(android.R.string.yes, listener)
                .setNegativeButton(android.R.string.no, null).show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    public void confirm(String title, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder
                .setTitle(title)
                .setMessage("Are you sure, " + message)
                .setIcon(R.mipmap.exit)
                .setPositiveButton(android.R.string.yes, positiveListener)
                .setNegativeButton(android.R.string.no, negativeListener).show();
        dialog.setCancelable(false);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    public AlertDialog showCustomDialog(int view, ViewHandler viewHandler)
    {
        ////        new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.myDialog))
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final AlertDialog dialog = builder
                .setCancelable(false)
                .setView(view)
                .show();
        viewHandler.init(dialog);
        viewHandler.setListeners(dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        return dialog;
    }
    public AlertDialog showCustomDialogCancelable(int view, ViewHandler viewHandler)
    {
        ////        new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.myDialog))
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final AlertDialog dialog = builder
                .setView(view)
                .show();
        viewHandler.init(dialog);
        viewHandler.setListeners(dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        return dialog;
    }

    public AlertDialog showInputDialog(String hint, final InputResponse inputResponse, final DialogInterface.OnClickListener listener)
    {
        ////        new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.myDialog))

        final EditText editText = new EditText(new ContextThemeWrapper(activity, R.style.AppTheme));
        editText.setHint(hint);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 30, 10, 10);
        editText.setLayoutParams(layoutParams);

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editText);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final AlertDialog dialog = builder
                .setCancelable(false)
                .setView(layout)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inputResponse.setData(editText.getText().toString());
                        listener.onClick(dialogInterface,i);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.show();
        return dialog;
    }


    public AlertDialog startIndicator() {
//        new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.myDialog))
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final AlertDialog dialog = builder
                .setCancelable(false)
                .setView(R.layout.indicator_layout)
                .show();
        dialog.findViewById(R.id.loader_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.d("----", e.getMessage());
                }
            }
        });
        dialog.findViewById(R.id.loader_close).setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.findViewById(R.id.loader_close).setVisibility(View.VISIBLE);
            }
        }, 10000);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private static Bitmap takeScreenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    public Bitmap fastblur(Bitmap sentBitmap, int radius) {
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    public void stopIndicator(AlertDialog dialog) {
        try {
            dialog.dismiss();

        } catch (Exception e) {
            Log.d("----", e.getMessage());
        }
    }


    class BlurAsyncTask extends AsyncTask<Void, Integer, Bitmap> {

        private final String TAG = BlurAsyncTask.class.getName();
        private final AlertDialog alert;

        public BlurAsyncTask(AlertDialog dialog) {
            this.alert = dialog;
        }

        protected Bitmap doInBackground(Void... arg0) {

            Bitmap map = takeScreenShot(activity);
            Bitmap fast = fastblur(map, 10);

            return fast;
        }


        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                final Drawable draw = new BitmapDrawable(activity.getResources(), result);

                WindowManager.LayoutParams params = alert.getWindow().getAttributes();
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Window window = alert.getWindow();
                window.setBackgroundDrawable(draw);
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER);
                window.setAttributes(params);
                alert.show();

                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);

            }

        }
    }

    public static class InputResponse{
        String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static interface ViewHandler{
        void init(AlertDialog dialog);
        void setListeners(AlertDialog dialog);
    }
}
