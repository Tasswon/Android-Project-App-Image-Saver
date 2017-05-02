package ca.algomau.imagesaver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class DialogFrag extends DialogFragment {

    public DialogFrag() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inflatedView = inflater.inflate(R.layout.fragment_dialog, null);

        builder.setView(inflatedView);
        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText urlSelect = (EditText)inflatedView.findViewById(R.id.etURL);
                RadioButton r1 = (RadioButton)inflatedView.findViewById(R.id.art);
                RadioButton r2 = (RadioButton)inflatedView.findViewById(R.id.environment);
                RadioButton r3 = (RadioButton)inflatedView.findViewById(R.id.memes);

                String url = urlSelect.getText().toString();
                String category;
                if(r1.isChecked()) {
                    category = "art";
                }
                else if(r2.isChecked()) {
                    category = "environment";
                }
                else if (r3.isChecked()){
                    category = "memes";
                }
                else {
                    category = "other";
                }

                if(!urlSelect.getText().toString().equals("")) {
                    ((SelectionScreen)getActivity()).updateData(category, url);
                }
                else {
                    Toast.makeText(getActivity(), "Nothing entered in the URL field...", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int it) {
            }
        });
        return builder.create();
    }
}