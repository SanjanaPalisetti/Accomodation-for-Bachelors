package com.example.Bachelors;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.zip.Inflater;

public class VerificationDialogue extends AppCompatDialogFragment {

    private EditText amount;
    private VerificationDialogueListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.verification_dialogue, null);

        builder.setView(view).setTitle("Add money").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String money = amount.getText().toString().trim();
                listener.applyTexts(money);
            }
        });

        amount = view.findViewById(R.id.editTextAmount);

        return builder.create();

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (VerificationDialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement VerificationDialogueListener");
        }

    }

    public interface VerificationDialogueListener{

        void applyTexts(String money);

    }
}
