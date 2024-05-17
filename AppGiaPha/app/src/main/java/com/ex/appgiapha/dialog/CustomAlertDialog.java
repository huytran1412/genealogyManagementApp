package com.ex.appgiapha.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ex.appgiapha.R;

public class CustomAlertDialog {

    public interface OnAddFamilyTreeListener {
        void onAddFamilyTree(String familyTreeName);
    }

    public static void showAddFamilyTreeDialog(Context context, OnAddFamilyTreeListener listener,String tenGiaPha) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.giapha_dialog, null);
        builder.setView(view);

        EditText editTextFamilyTreeName = view.findViewById(R.id.editTextFamilyTreeName);
        Button buttonAddFamilyTree = view.findViewById(R.id.buttonAddFamilyTree);
        editTextFamilyTreeName.setText(tenGiaPha);
        AlertDialog alertDialog = builder.create();
        buttonAddFamilyTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String familyTreeName = editTextFamilyTreeName.getText().toString().trim();
                if (!familyTreeName.isEmpty()) {
                    listener.onAddFamilyTree(familyTreeName);
                    alertDialog.dismiss();
                } else {
                   Toast.makeText(context, "Vui lòng nhập tên gia phả", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }
}
