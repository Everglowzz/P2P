package hzyj.come.p2p.copy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import hzyj.come.p2p.R;


public class AlertDialogFragment extends DialogFragment {


    public static final String TAG = "AlertDialogFragment";
    private AlertDialogFragmentCallBack callBack;

    public static AlertDialogFragment newInstance(boolean cancelable,
                                                  AlertDialogFragmentCallBack callBack,
                                                  String... text) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", text[0]);
        bundle.putString("message", text[1]);
        bundle.putString("positive", text[2]);
        if (text.length > 3)
            bundle.putString("negative", text[3]);
        fragment.setCancelable(cancelable);
        fragment.setArguments(bundle);
        fragment.setAlertDialogFragmentCallBack(callBack);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String message = bundle.getString("message");
        String positive = bundle.getString("positive");
        String negative = bundle.getString("negative");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        builder.setTitle(title).setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.onClick(dialog, which);
                    }
                });
        if (negative != null)
            builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    callBack.onClick(dialog, which);
                }
            });

        AlertDialog dialog = builder.show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(15);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        lp.width = (int) (metric.widthPixels - 50 * metric.density);
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public interface AlertDialogFragmentCallBack {
        void onClick(DialogInterface dialog, int which);
    }

    public void setAlertDialogFragmentCallBack(AlertDialogFragmentCallBack callBack) {
        this.callBack = callBack;
    }
}
