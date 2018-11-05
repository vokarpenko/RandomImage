package com.example.testimage;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.io.InputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    public ImageView imageView;
    public TextView textView;
    public Button random;
    public MyTask myTask;
    private static final String TAG = "MyApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        random = findViewById(R.id.random);
    }

    public void onClick(View view) {
        myTask = new MyTask();
        myTask.execute();
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        Document doc, page = null;

        @Override
        protected Void doInBackground(Void... voids) {
            String random_page="https://ru.wikipedia.org/wiki/Служебная:Случайная_страница";
            try {
                //page = Jsoup.connect("random_page")
                //        .timeout(6000).get();
                //Log.i(TAG, page.toString());
                //urlconn
                URLConnection con = new URL( random_page ).openConnection();
                con.setRequestProperty( "User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)");
                con.connect();
                InputStream is = con.getInputStream();
                is.close();
                //
                String path = con.getURL().toString();
                path = java.net.URLDecoder.decode(path, "UTF-8");
                Log.i(TAG, path );
                doc = Jsoup.connect(path).get();
            } catch (Exception e) {
                Log.i(TAG, e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            String picture_path = doc.select(".image img ").attr("src");
            textView.setText(doc.title());
            Picasso.get().load("https://"+picture_path).into(imageView);
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}


