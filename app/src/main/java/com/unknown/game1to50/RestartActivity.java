package com.unknown.game1to50;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class RestartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss:SS", Locale.getDefault());

        TextView tvRecord, tvBestRecord;
        tvBestRecord = findViewById(R.id.tv_best_record_restart);
        tvRecord = findViewById(R.id.tv_record_restart);

        Intent intent = getIntent();
        long recordMilli = intent.getLongExtra("recordMilli", 0);
        String record = simpleDateFormat.format(recordMilli);
        tvRecord.setText(record);

        SharedPreferences pref = getSharedPreferences("prefData", MODE_PRIVATE);
        long bestRecordMilli = pref.getLong("bestRecordMilli", 0);
        String bestRecord = simpleDateFormat.format(bestRecordMilli);
        tvBestRecord.setText(bestRecord);

        if (recordMilli < bestRecordMilli || bestRecordMilli == 0) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putLong("bestRecordMilli", recordMilli);
            editor.apply();
        }

    }

    public void restartGame(View view) {
        startActivity(new Intent(this, GameActivity.class));
        finish();
    }

//    public void saveRecord(View view) {
//        EditText editText = new EditText(this);
//        AlertDialog dialog;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        dialog = builder.setMessage("이름을 입력해주세요")
//                .setView(editText)
//                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        SharedPreferences pref = getSharedPreferences("prefData", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = pref.edit();
//
//                    }
//                })
//                .setNegativeButton("취소", null).create();
//        dialog.show();
//
//    }
}
