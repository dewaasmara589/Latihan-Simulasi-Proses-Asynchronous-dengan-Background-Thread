package com.dewa.mybackgroundthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btn_start);
        TextView tvStatus = findViewById(R.id.tv_status);

        // Cara 1 namun masih terdapat forclose
//        btnStart.setOnClickListener(v -> {
//            try {
//                // Simulate Process Compressing
//                for (int i = 0; i <= 10; i++) {
//                    Thread.sleep(500);
//                    int percentage = i * 10;
//                    if (percentage == 100) {
//                        tvStatus.setText(R.string.task_completed);
//                    }else {
//                        tvStatus.setText(String.format(getString(R.string.compressing), percentage));
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        // Cara 2 Menggunakan Executor
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        btnStart.setOnClickListener(v -> {
            executor.execute(() -> {
                try {
                    // Simulate Process in Background thread
                    for (int i = 0; i <= 10; i++) {
                        Thread.sleep(500);
                        int percentage = i * 10;
                        handler.post(() -> {
                            if (percentage == 100) {
                                tvStatus.setText(R.string.task_completed);
                            }else {
                                tvStatus.setText(String.format(getString(R.string.compressing), percentage));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}