package com.example.draymond.crypttracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class DeleteDialog extends AppCompatDialogFragment {

    private String id;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();


        View view = layoutInflater.inflate(R.layout.delete_coin_dialog,null);

        builder.setView(view)
                .setTitle("Delete Coin")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer integer = DatabaseHelper.getInstance(getActivity()).delete(getArguments().getString("ID"));
                        Log.d("id",integer.toString());
                        Intent i = new Intent(getContext(),MainActivity.class);
                        getContext().startActivity(i);




                    }
                });

        return builder.create();

    }
}
