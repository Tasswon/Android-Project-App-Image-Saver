package ca.algomau.imagesaver;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class SelectionScreen extends AppCompatActivity {

    private SQLiteDatabase db;
    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private int currentCat = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);

        db = openOrCreateDatabase("Pictures", MODE_PRIVATE, null);
    }

    public void addImage(View view) {
        FragmentManager fm = getFragmentManager();
        DialogFrag frag = new DialogFrag();
        frag.show(fm, null);
    }

    public void updateData(String category, String url) {
        db.execSQL("INSERT INTO Categories(category, url) VALUES" +
                "('" + category + "', '" + url + "')");
        if(category.equals("art")) {
            loadImages(0);
        }
        else if(category.equals("environment")) {
            loadImages(1);
        }
        else if(category.equals("memes")) {
            loadImages(2);
        }
        else {
            loadImages(3);
        }
    }

    public void refresh(View view) {
        if(currentCat >= 0) {
            loadImages(currentCat);
        }
    }

    public void loadImages(int position) {
        currentCat = position;
        urls.clear();
        ids.clear();
        String query = "";
        switch (position) {
            case 0:
                query = "SELECT id, url FROM Categories WHERE category='art'";
                break;
            case 1:
                query = "SELECT id, url FROM Categories WHERE category='environment'";
                break;
            case 2:
                query = "SELECT id, url FROM Categories WHERE category='memes'";
                break;
            case 3:
                query = "SELECT id, url FROM Categories WHERE category='other'";
                break;
        }
        Cursor cr = db.rawQuery(query, null);
        if (cr.moveToFirst()) {
            do {
                for (int i = 1; i < cr.getColumnCount(); i++) {
                    ids.add(cr.getString(0));
                    urls.add(cr.getString(1));
                }
            } while (cr.moveToNext());
        }
        cr.close();

        GridLayout gl = (GridLayout) findViewById(R.id.picHolder);
        gl.removeAllViews();

        for (int i = 0; i < urls.size(); i++) {
            final View inflatedView = getLayoutInflater().inflate(R.layout.image_inflator, null);
            ImageView iv = (ImageView) inflatedView.findViewById(R.id.icon);
            Picasso.with(getApplicationContext()).load(urls.get(i)).placeholder(R.drawable.badlink)
                    .error(R.drawable.badlink).into(iv);
            Button btnExpand = (Button) inflatedView.findViewById(R.id.btnExpand);
            Button btnDelete = (Button) inflatedView.findViewById(R.id.btnRemove);
            final String count = ids.get(i);
            btnExpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandImage(count);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteImage(count);
                }
            });
            gl.addView(inflatedView);
        }
    }

    private void expandImage(String id) {
        Intent intent = new Intent(this, ExpandImage.class);
        intent.putExtra("key", id);
        startActivity(intent);
    }

    private void deleteImage(String id) {
        db.delete("categories", "id=" + id, null);
        loadImages(currentCat);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("keyRestore", currentCat);
    }

    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        currentCat = inState.getInt("keyRestore");
        if(currentCat != -1) {
            loadImages(currentCat);
        }
    }

    public void returnMain(View view) {
        finish();
    }
}
