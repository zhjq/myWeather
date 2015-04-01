package pku.ss.zhjq.app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pku.ss.zhjq.bean.City;
import pku.ss.zhjq.db.CityDB;

/**
 * Created by zhjq on 2015/3/28.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyAPP";
    private static MyApplication mApplication;
    private CityDB mCityDB;
    private List<City> mCityList = new ArrayList<City>();

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "MyApplication->onCreate");
        mApplication = this;

        mCityDB = openCityDB();
        initCityList();

    }

    public static MyApplication getInstance(){
        return mApplication;
    }


    private CityDB openCityDB(){
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + getPackageName()
                + File.separator + "databases"
                + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);
        Log.d(TAG,path);
        if(!db.exists()){
            Log.i("MyApp","db is not exits");
            try{
                InputStream is = getResources().getAssets().open("city.db");
                db.getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while((len = is.read(buffer))!=-1){
                    fos.write(buffer,0,len);
                    fos.flush();
                }
                fos.close();
                is.close();
            }catch (IOException e){
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this,path);
    }

    private void initCityList(){
        mCityList = new ArrayList<City>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prepareCityList();
            }
        }).start();
    }
    private boolean prepareCityList(){
        mCityList = mCityDB.getAllCity();
        for(City city:mCityList){
            String cityName = city.getCity();
            Log.d(TAG,cityName);

        }
        return true;
    }

    public List<City> getCityList() {
        return mCityList;
    }

}
