package com.example.bai_1;  // Đảm bảo package đúng

import android.os.AsyncTask;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWebPageTask extends AsyncTask<String, Void, String> {

    private TextView textView;

    public DownloadWebPageTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder response = new StringBuilder();
        HttpURLConnection urlConnection = null;

        try {
            for (String urlStr : urls) {
                URL url = new URL(urlStr);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                inputStream.close();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        textView.setText(result);
    }
}
