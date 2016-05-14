package johnkagga.me.frank_farm;

import com.firebase.client.Firebase;
import com.firebase.client.Logger;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        //Debug mode
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);
    }
}

