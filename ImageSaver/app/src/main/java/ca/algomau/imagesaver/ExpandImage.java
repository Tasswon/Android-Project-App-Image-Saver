package ca.algomau.imagesaver;

import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.Date;

public class ExpandImage extends AppCompatActivity {

    private String url;
    private SQLiteDatabase db;
    private EditText displayUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_image);

        ImageView imagePick = (ImageView) findViewById(R.id.imagePick);
        db = openOrCreateDatabase("Pictures", MODE_PRIVATE, null);
        displayUrl = (EditText) findViewById(R.id.getUrl);

        Intent intent = getIntent();
        String id = intent.getStringExtra("key");
        String query = "SELECT url FROM Categories WHERE id='" + id + "'";

        Cursor cr = db.rawQuery(query, null);
        url = "";
        if (cr.moveToFirst()) {
            url = cr.getString(0);
        }
        cr.close();
        Picasso.with(getApplicationContext()).load(url).placeholder(R.drawable.badlink)
                .error(R.drawable.badlink).into(imagePick);
    }

    public void download(View view) {
        try {
            DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
            Uri Download_Uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            String name = new Date().toString() + ".jpg";
            request.setDescription("Image URL Download: " + name);
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, name);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            downloadManager.enqueue(request);
            Toast.makeText(this, name + " is now in the application download folder...", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(this, "Failed to download image...", Toast.LENGTH_LONG).show();
        }
    }

    public void cancel(View view) {
        finish();
    }

    public void switchOrientation(View view) {
        Toast.makeText(this, "Switch to portrait view for more options...", Toast.LENGTH_LONG).show();
    }

    public void displayURL(View view) {
        if(displayUrl.getText().toString().equals("")) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = (float)0.5;
            displayUrl.setLayoutParams(params);
            displayUrl.setText(url);
            displayUrl.selectAll();
        }
        else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    0);
            params.weight = 0;
            displayUrl.setLayoutParams(params);
            displayUrl.setText("");
        }
    }
}
