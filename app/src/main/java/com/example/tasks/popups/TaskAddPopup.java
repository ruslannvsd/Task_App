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
import com.example.tasks.data.ProjVM;
import com.example.tasks.data.task.Tsk;
import com.example.tasks.databinding.TaskAddPopupBinding;
import com.example.tasks.interfaces.OnTaskPosChange;

public class TaskAddPopup {
    public void taskAddPopup(Context ctx, int thread, ProjVM projVM, OnTaskPosChange taskPosChange) {
        LayoutInflater inflater =
                (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.task_add_popup, new LinearLayout(ctx), false);
        TaskAddPopupBinding bnd = TaskAddPopupBinding.bind(popupView);
        PopupWindow window = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        window.setElevation(2f);
        window.showAtLocation(popupView, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
        PopupWindowCompat.setWindowLayoutType(
                window,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        );
        EditText taskEt = bnd.enterField;
        taskEt.setOnKeyListener((view, i, keyEvent) -> {
            String title = taskEt.getText().toString();
            if (!title.matches("") && i == KeyEvent.KEYCODE_ENTER) {
                Tsk task = new Tsk(0, title, null, true, thread);
                taskPosChange.onTaskPosChange(task);
                window.dismiss();
            }
            return true;
        });
    }
}
