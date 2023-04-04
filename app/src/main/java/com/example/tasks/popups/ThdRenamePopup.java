package com.example.tasks.popups;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.core.widget.PopupWindowCompat;

import com.example.tasks.R;
import com.example.tasks.data.thread.Thd;
import com.example.tasks.databinding.AddLayoutBinding;
import com.example.tasks.interfaces.OnThdAction;

public class ThdRenamePopup {
    public void renamePopup(Context ctx, Thd thread, OnThdAction thdRename) {
        LayoutInflater inflater =
                (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_layout, new LinearLayout(ctx), false);
        AddLayoutBinding bnd = AddLayoutBinding.bind(popupView);
        PopupWindow window = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        window.setElevation(2f);
        window.showAtLocation(popupView, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
        PopupWindowCompat.setWindowLayoutType(
                window,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        );

        EditText thdEt = bnd.enterField;
        thdEt.setText(thread.getTitle());
        thdEt.setOnKeyListener((view, i, keyEvent) -> {
            String title = thdEt.getText().toString();
            if (!title.matches("") && i == KeyEvent.KEYCODE_ENTER) {
                Thd newThd = new Thd(thread.id, title, thread.sts);
                thdRename.onThdAction(newThd);
                window.dismiss();
            }
            return true;
        });
    }
}
