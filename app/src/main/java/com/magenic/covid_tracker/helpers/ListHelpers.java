package com.magenic.covid_tracker.helpers;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.magenic.covid_tracker.interfaces.IListHelpers;

public class ListHelpers implements IListHelpers {
    private Context _context;

    public ListHelpers(Context context) {
        _context = context;
    }

    public ListenableFuture<String> showStringListForResult(final String[] listItems, String title) {
        final SettableFuture<String> result = SettableFuture.create();

        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        builder.setTitle(title);

        builder.setItems(listItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result.set(listItems[which]);
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                result.set("");
            }
        });
        builder.show();
        return result;
    }
}
