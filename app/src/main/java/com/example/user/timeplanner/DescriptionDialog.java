package com.example.user.timeplanner;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.EventLogTags;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by User on 19.04.2018.
 */

public class DescriptionDialog extends AppCompatDialogFragment {

    private EditText descriptionEditText;
    private DescriptionDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.description_dialog_layout, null);
        descriptionEditText = (EditText) view.findViewById(R.id.descriptionEditText);

        builder.setView(view)
                .setTitle("Description")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String description = descriptionEditText.getText().toString();
                        listener.applyTexts(description);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DescriptionDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement DescriptionDialogListener");
        }
    }

    public interface DescriptionDialogListener{
        void applyTexts(String description);
    }
}
