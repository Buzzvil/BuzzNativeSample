package sample.buzznative.buzzvil.com.buzznativesample;

import android.app.Application;
import android.support.multidex.MultiDex;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
