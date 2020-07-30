package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class test extends AppCompatActivity {

    private ListView mlistView;
    public String id[] = {"1wnueB7Wrz0", "3nK13MpQMa0", "9sQkjMgFglY", "NNJzHK7PGm8", "ckkrXYU-pi0", "n5WnMJ0J6qA", "lIjYdsznj2c", "N2-HsIYd0Go", "g4HDfqEWf6Y", "H39werth1qs"};
    ArrayList<Cards> list;
    String key = "loadUrl";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mlistView = findViewById(R.id.listview);
        list = new ArrayList<>();
        for (String x : id) {
            new Jsontask(this, x).execute("https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=" + x + "&format=json").toString();
        }
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotomainactivity(i);

            }

            private void gotomainactivity(int i) {
                Intent intent = new Intent(test.this, MainActivity.class);
                intent.putExtra(key,id[i]);
                startActivity(intent);
            }
        });

    }


    public class Jsontask extends AsyncTask<String, String, String> {
        Context context;
        String num;

        public Jsontask(Context context,String x) {
            this.context = context;
            this.num=x;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }
                String finalJson = buffer.toString();

                JSONObject jsonObject = new JSONObject(finalJson);
                String title = jsonObject.getString("title");
                return title;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String url = "https://img.youtube.com/vi/" + num + "/0.jpg";
            list.add(new Cards(url, result));
            CustomListAdapter adapter = new CustomListAdapter(test.this, R.layout.cardview, list);
            mlistView.setAdapter(adapter);
        }
    }
}
