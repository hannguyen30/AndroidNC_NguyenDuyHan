package com.example.bai_1;  // Đảm bảo package đúng

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReadWebpageAsyncTask extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.TextView01);
    }

    public void readWebpage(View view) {
        // Khởi tạo DownloadWebPageTask với textView
        DownloadWebPageTask task = new DownloadWebPageTask(textView);
        task.execute("http://www.google.com");
    }
}