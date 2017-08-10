package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;

import static android.R.attr.priority;

/**
 * Created by KingMan on 09-Aug-17.
 */

public class PetAdaptor extends CursorAdapter {
    public PetAdaptor(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.pet_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView petName = (TextView) view.findViewById(R.id.petName);
        String pName = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_NAME));
        petName.setText(pName);

        TextView petBreed = (TextView) view.findViewById(R.id.petBreed);
        String pBreed = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_BREED));

        if (pBreed.equals("")) {
            pBreed = "Unknown Breed";
        }

        petBreed.setText(pBreed);
    }
}
