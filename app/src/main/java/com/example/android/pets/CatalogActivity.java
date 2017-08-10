package com.example.android.pets;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetDbHelper;

import org.w3c.dom.Text;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private PetDbHelper mDbHelper;
    Cursor cursor;
    PetAdaptor itemsAdapter;
    ListView lvItem;

    SQLiteDatabase dbR;
    SQLiteDatabase dbW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        mDbHelper = new PetDbHelper(this);

        dbR =  mDbHelper.getReadableDatabase();
        dbW =  mDbHelper.getWritableDatabase();

        lvItem = (ListView) findViewById(R.id.PetList);
        View emptyView = findViewById(R.id.empty_view);
        lvItem.setEmptyView(emptyView);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.i("KINGMAN", "POS : " + position + " - ID : " + id);

                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                intent.putExtra("EXTRA_FPKEY", id);
                startActivity(intent);
            }
        });

        displayDatabaseLv();
    }

    private void displayDatabaseLv() {
        String sqlStr = "select * from " + PetEntry.TABLE_NAME;
        cursor = dbR.rawQuery(sqlStr, null);

        itemsAdapter = new PetAdaptor(this, cursor);
        lvItem.setAdapter(itemsAdapter);
    }

    private void refreshListData() {
        String sqlStr = "select * from " + PetEntry.TABLE_NAME;
        cursor = dbR.rawQuery(sqlStr, null);

        itemsAdapter.changeCursor(cursor);
    }

//    private void displayDatabaseInfo() {
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//
////        String sqlStr = "select * from " + PetEntry.TABLE_NAME;
////        Cursor cursor = db.rawQuery(sqlStr, null);
//
//        String[] projection = {
//                PetEntry._ID,
//                PetEntry.COLUMN_PET_NAME,
//                PetEntry.COLUMN_PET_BREED,
//                PetEntry.COLUMN_PET_GENDER,
//                PetEntry.COLUMN_PET_WEIGHT};
//
//        Cursor cursor = db.query(
//                PetEntry.TABLE_NAME,   // The table to query
//                projection,            // The columns to return
//                null,                  // The columns for the WHERE clause
//                null,                  // The values for the WHERE clause
//                null,                  // Don't group the rows
//                null,                  // Don't filter by row groups
//                null);                 // The sort order
//
//        try {
//            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
//
//            displayView.setText("Total Data : " + String.valueOf(cursor.getCount()) + "\n");
//
//            displayView.append(PetEntry._ID + " - " +
//                    PetEntry.COLUMN_PET_NAME + " - " +
//                    PetEntry.COLUMN_PET_BREED + " - " +
//                    PetEntry.COLUMN_PET_GENDER + " - " +
//                    PetEntry.COLUMN_PET_WEIGHT + "\n");
//
//            // Figure out the index of each column
//            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
//            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
//            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
//            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
//            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);
//
//            while (cursor.moveToNext()) {
//                int currentID = cursor.getInt(idColumnIndex);
//                String currentName = cursor.getString(nameColumnIndex);
//                String currentBreed = cursor.getString(breedColumnIndex);
//                int currentGender = cursor.getInt(genderColumnIndex);
//                int currentWeight = cursor.getInt(weightColumnIndex);
//
//                displayView.append(("\n" + currentID + " - " +
//                        currentName + " - " +
//                        currentBreed + " - " +
//                        currentGender + " - " +
//                        currentWeight));
//            }
//        } finally {
//            cursor.close();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                createDummyData();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                showDeleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_all_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteAllData();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteAllData() {
        dbR = mDbHelper.getWritableDatabase();

        long returnKey;
        returnKey = dbR.delete(PetEntry.TABLE_NAME, "1", null);

        if (returnKey == -1) {
            Toast.makeText(this, "Error deleting All Data", Toast.LENGTH_SHORT).show();
        } else {
            refreshListData();
        }
    }

    private void createDummyData() {
        ContentValues temp = new ContentValues();
        temp.put(PetEntry.COLUMN_PET_NAME, "KIM");
        temp.put(PetEntry.COLUMN_PET_BREED, "CHOW");
        temp.put(PetEntry.COLUMN_PET_WEIGHT, 14.5);
        temp.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_FEMALE);

        long insertKey = dbW.insert(PetEntry.TABLE_NAME, null, temp);

        if (insertKey == -1) {
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Pet saved with row id: " + insertKey, Toast.LENGTH_SHORT).show();
            refreshListData();
        }
    }
}
