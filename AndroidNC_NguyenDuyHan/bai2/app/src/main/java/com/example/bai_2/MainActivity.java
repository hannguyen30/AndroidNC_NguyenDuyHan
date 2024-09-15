package com.example.bai_2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView1, textView2, textView3;
    private Button button1, button2, button3;

    private Handler handler;
    private Thread thread1, thread2, thread3;
    private volatile boolean running1, running2, running3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        textView1.setText("Thread 1: " + msg.arg1);
                        break;
                    case 2:
                        textView2.setText("Thread 2: " + msg.arg1);
                        break;
                    case 3:
                        textView3.setText("Thread 3: " + msg.arg1);
                        break;
                }
            }
        };

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running1) {
                    stopThread1();
                } else {
                    startThread1();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running2) {
                    stopThread2();
                } else {
                    startThread2();
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running3) {
                    stopThread3();
                } else {
                    startThread3();
                }
            }
        });

        // Start threads 2 seconds after activity is created
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startThread1();
                startThread2();
                startThread3();
            }
        }, 2000);
    }

    private void startThread1() {
        running1 = true;
        button1.setText("Stop");
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                while (running1) {
                    int number = random.nextInt(51) + 50;
                    Message msg = handler.obtainMessage(1, number, 0);
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        thread1.start();
    }

    private void stopThread1() {
        running1 = false;
        button1.setText("Start");
        thread1 = null;
    }

    private void startThread2() {
        running2 = true;
        button2.setText("Stop");
        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int number = 1;
                while (running2) {
                    Message msg = handler.obtainMessage(2, number, 0);
                    handler.sendMessage(msg);
                    number += 2;
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        thread2.start();
    }

    private void stopThread2() {
        running2 = false;
        button2.setText("Start");
        thread2 = null;
    }

    private void startThread3() {
        running3 = true;
        button3.setText("Stop");
        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int number = 0;
                while (running3) {
                    Message msg = handler.obtainMessage(3, number, 0);
                    handler.sendMessage(msg);
                    number ++;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        thread3.start();
    }

    private void stopThread3() {
        running3 = false;
        button3.setText("Start");
        thread3 = null;
    }
}
