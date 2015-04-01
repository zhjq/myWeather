package pku.ss.zhjq.myweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import pku.ss.zhjq.app.MyApplication;
import pku.ss.zhjq.bean.City;
import pku.ss.zhjq.bean.TodayWeather;


public class CityManagerActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MyAPP";

    private TodayWeather todayWeather;

    private ListView cityLv;
    private ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
        Intent intent = getIntent();
        if (intent.getSerializableExtra("TodayWeather") != null) {
            this.todayWeather = (TodayWeather) intent.getSerializableExtra("TodayWeather");
//            Log.d(TAG, "CityManager Get " + this.todayWeather.getCity());
        }

        backImg = (ImageView) findViewById(R.id.title_back);
        backImg.setOnClickListener(this);

        MyApplication myApp = MyApplication.getInstance();
        List<City> list = myApp.getCityList();
        int len = list.size();
        final String[] cities = new String[len];
        final String[] cityCodes = new String[len];
        for (int i = 0; i < len; i++) {
            cities[i] = list.get(i).getProvince() + " " + list.get(i).getCity();
        }

        cityLv = (ListView) findViewById(R.id.city_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CityManagerActivity.this, android.R.layout.simple_list_item_1, cities);
        cityLv.setAdapter(adapter);
        cityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finish();
                Toast.makeText(CityManagerActivity.this, "你点击了" + cities[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
