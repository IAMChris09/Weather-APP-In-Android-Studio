package com.example.weatherapptutorial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class UserLoginDialog extends DialogFragment {

    int count = 4;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.setCancelable(false);


        View view = getActivity().getLayoutInflater().inflate(R.layout.user_login_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Login");
        builder.setView(view);

        final EditText emailText = view.findViewById(R.id.email);
        EditText passwordText = view.findViewById(R.id.password);

//        builder.setNegativeButton("Cancel", null);

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);}
        });//31~34 ==> new

        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final AlertDialog dialog = builder.create();
//                Toast.makeText(getActivity(), "Email is" + emailText.getText().toString() + "and password is" + passwordText.getText().toString(), Toast.LENGTH_SHORT).show();
                if(emailText.getText().toString().equals("admin") && passwordText.getText().toString().equals("123")){
                    Toast.makeText(getActivity(), "successful login", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    count -= 1;
                    if(count <= 0){
                        System.exit(0);
                        Toast.makeText(getActivity(), "goodbye!", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), "you have only " + count +  " last chances", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        Toast.makeText(getActivity(), "Dialog Canceled", Toast.LENGTH_SHORT).show();
    }
}
