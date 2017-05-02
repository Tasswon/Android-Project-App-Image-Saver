package ca.algomau.imagesaver;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE = 123;
    private SQLiteDatabase db;
    private TextView tvStats;
    private String countPics;
    private String category = "";
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private ArrayList<String> categoriesDelete = new ArrayList<>();
    private CheckBox array [] = new CheckBox[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("Pictures", MODE_PRIVATE, null);
        tvStats = (TextView) findViewById(R.id.tvStats);
        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        array[0] = cb1;
        array[1] = cb2;
        array[2] = cb3;
        array[3] = cb4;

        db.execSQL("CREATE TABLE IF NOT EXISTS categories("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "category VARCHAR(255) NOT NULL," +
                "url VARCHAR(255) NOT NULL)");

        String query = "SELECT Count(*) FROM categories";

        Cursor cr = db.rawQuery(query, null);
        if (cr.moveToFirst()) {
            do {
                countPics = cr.getString(0);
            } while (cr.moveToNext());
        }
        cr.close();
    }

    public void enter(View view) {
        Intent intent = new Intent(this, SelectionScreen.class);
        startActivity(intent);
        resetPage();
        category = "";
    }

    public void updateNumbers(View view) {
        category = "";
        switch(view.getId()) {
            case R.id.r1: category = "art";
                break;
            case R.id.r2: category = "environment";
                break;
            case R.id.r3: category = "memes";
                break;
            case R.id.r4: category = "other";
                break;
            case R.id.r5: category = "total saved";
                break;
        }
        String query;
        if(category.equals("total saved")) {
            query = "SELECT Count(*) FROM categories";
        }
        else {
            query = "SELECT Count(*) FROM categories WHERE category = '" + category + "'" ;
        }

        Cursor cr = db.rawQuery(query, null);
        if (cr.moveToFirst()) {
            do {
                countPics = cr.getString(0);
            } while (cr.moveToNext());
        }
        cr.close();
        tvStats.setText(category + " urls: " + countPics);
    }

    public void clearPictures(View view) {
        categoriesDelete.clear();
        for(int i = 0; i < 4; i++) {
            if(array[i].isChecked()) {
                categoriesDelete.add(array[i].getText().toString());
            }
        }
        for(int i = 0; i < categoriesDelete.size(); i++) {
            db.delete("categories", "category='" + categoriesDelete.get(i) + "'", null);
        }
        Toast.makeText(this, "Selected images have been removed...", Toast.LENGTH_LONG).show();

        String query = "SELECT Count(*) FROM categories";
        Cursor cr = db.rawQuery(query, null);
        if (cr.moveToFirst()) {
            do {
                countPics = cr.getString(0);
            } while (cr.moveToNext());
        }
        cr.close();
        tvStats.setText(category + " urls: " + countPics);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("keyCheck1", category);
        outState.putString("keyCheck2", countPics);
    }

    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        category = inState.getString("keyCheck1");
        countPics = inState.getString("keyCheck2");
        if(!(category.equals(""))) {
            tvStats.setText(category + " urls: " + countPics);
        }
    }

    public void resetPage() {
        RadioButton r1 = (RadioButton) findViewById(R.id.r1);
        RadioButton r2 = (RadioButton) findViewById(R.id.r2);
        RadioButton r3 = (RadioButton) findViewById(R.id.r3);
        RadioButton r4 = (RadioButton) findViewById(R.id.r4);
        RadioButton r5 = (RadioButton) findViewById(R.id.r5);

        r1.setChecked(false);
        r2.setChecked(false);
        r3.setChecked(false);
        r4.setChecked(false);
        r5.setChecked(false);

        cb1.setChecked(false);
        cb2.setChecked(false);
        cb3.setChecked(false);
        cb4.setChecked(false);

        tvStats.setText("");
    }

    public void information(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Image Saver Information");
        builder.setMessage(R.string.app_info);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}