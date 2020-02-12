package com.unknown.game1to50;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Integer> arrNum = new ArrayList<>();
    private GameAdapter gameAdapter;
    private int count = 1;
    private RecyclerView recyclerView;
    private TextView tvRecord, tvNum;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String record;
    private TimerThread timerThread;
    private long recordMilli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvNum = findViewById(R.id.tv_num_game);
        tvRecord = findViewById(R.id.tv_record_game);
        recyclerView = findViewById(R.id.rv_game);
        gameAdapter = new GameAdapter();
        recyclerView.setAdapter(gameAdapter);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);

        simpleDateFormat = new SimpleDateFormat("mm:ss:SS", Locale.getDefault());

        addString();

        timerThread = new TimerThread();
        timerThread.start();
    }

    private class TimerThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (count < 51) {
                calendar.add(Calendar.MILLISECOND, 73);
                record = simpleDateFormat.format(calendar.getTime());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvRecord.setText(record);
                    }
                });

                try {
                    sleep(71);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            recordMilli = calendar.getTimeInMillis();
            startRestartActivity();
        }
    }

    private void startRestartActivity() {
        Intent intent = new Intent(this, RestartActivity.class);
        intent.putExtra("recordMilli", recordMilli);
        startActivity(intent);
        finish();
    }

    private void addString() {
        arrNum.clear();

        for (int i = 1; i <= 25; i++) {
            arrNum.add(i);
        }

        Collections.shuffle(arrNum);

        gameAdapter.notifyDataSetChanged();
    }

    //
    public class GameAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View itemView = inflater.inflate(R.layout.recycler_view_game, parent, false);
            VHolder vHolder = new VHolder(itemView);

            return vHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            final VHolder vHolder = (VHolder) holder;
            int num = arrNum.get(position);
            vHolder.button.setText(num + "");


            vHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = arrNum.get(position);
                    if (num == count) {
                        if (num + 25 < 51) {
                            arrNum.set(position, num + 25);
                            gameAdapter.notifyDataSetChanged();
                        } else {
                            vHolder.button.setVisibility(View.INVISIBLE);
                        }
                        count++;
                        tvNum.setText(count + "");
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrNum.size();
        }

        public class VHolder extends RecyclerView.ViewHolder {

            Button button;

            public VHolder(@NonNull View itemView) {
                super(itemView);

                button = itemView.findViewById(R.id.bt_gv_game);
            }
        }
    }

//    public class GameAdapter extends BaseAdapter {
//
//        LayoutInflater layoutInflater;
//
//        public GameAdapter(LayoutInflater layoutInflater) {
//            this.layoutInflater = layoutInflater;
//        }
//
//        @Override
//        public int getCount() {
//            return arrNum.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return arrNum.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, final View convertView, ViewGroup parent) {
//            View view = layoutInflater.inflate(R.layout.grid_view_game, parent, false);
//
//            final ArrayList<Integer> arrInvisible = new ArrayList<>();
//            button = view.findViewById(R.id.bt_gv_game);
//            int num = arrNum.get(position);
////            Log.e("GameA", "num: " + num);
//            button.setText(num + "");
//
//            convertView.setVisibility(View.INVISIBLE);
//
//
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int num;
//                    num = arrNum.get(position);
//                    if (num == count) {
//                        Log.e("GameA", "num == count");
//                        if (num + 25 < 51) {
//                            Log.e("GameA", "num + 25 : " + (num + 25));
//                            arrNum.set(position, num + 25);
//                        } else {
//                            Log.e("GameA", "num + 25 = <50");
//                            arrInvisible.add(num);
//                        }
//                        count++;
//                        Log.e("GameA", "count: " + count);
//                        gameAdapter.notifyDataSetChanged();
//
//                    }
//                }
//            });
//
//            return view;
//        }
//    }
}
