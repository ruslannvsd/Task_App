package com.example.tasks.menus;

import android.content.Context;
import android.widget.PopupMenu;

import androidx.cardview.widget.CardView;

import com.example.tasks.R;
import com.example.tasks.data.ProjVM;
import com.example.tasks.data.thread.Thd;
import com.example.tasks.interfaces.OnThdAction;
import com.example.tasks.interfaces.OnThdPosChange;
import com.example.tasks.popups.TaskAddPopup;
import com.example.tasks.popups.TaskPopup;
import com.example.tasks.popups.ThdRenamePopup;

public class ThdMenu {
    public void thdMenu(
            Context ctx,
            Thd thread,
            ProjVM projVM,
            CardView card,
            OnThdAction thdRename,
            OnThdPosChange deleteThread,
            int position
    ) {
        PopupMenu popupMenu = new PopupMenu(ctx, card);
        popupMenu.getMenuInflater().inflate(R.menu.thread_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.add) {
                new TaskAddPopup().taskAddPopup(ctx, thread.id, projVM);
                return true;
            }
            if (item.getItemId() == R.id.rename) {
                new ThdRenamePopup().renamePopup(ctx, thread, thdRename);
                return true;
            }
            if (item.getItemId() == R.id.delete) {
                deleteThread.onThdPosChange(thread, position);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }
}
