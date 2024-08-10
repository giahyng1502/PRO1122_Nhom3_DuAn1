package FPT.PRO1122.Nhom3.DuAn1.Dialogs;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import java.util.Objects;

import FPT.PRO1122.Nhom3.DuAn1.R;

public class Dialogs {
    public static AlertDialog dialog;

    public static void showProgressBar(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.layout_progress);
        dialog = builder.create();
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
    }

    public static void show() {
        dialog.show();
    }

    public static void dismiss() {
        dialog.dismiss();
    }
}
