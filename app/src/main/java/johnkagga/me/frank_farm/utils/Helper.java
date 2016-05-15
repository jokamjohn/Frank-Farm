package johnkagga.me.frank_farm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import johnkagga.me.frank_farm.R;

public class Helper {

    /**
     *
     * @param capturedTag
     * @return
     */
    public static String animalName(String capturedTag)
    {
        switch (capturedTag)
        {
            case "27209":
                return "Mpuga";
            case "28233":
                return "Kyozi";
            case "29001":
                return "Siina";
            case "4294953522":
                return "Ngabo";
            case "4294955080":
                return "Kyiroko";
            case "28489":
                return "Kanyibure";
            default:
                return "UNKNOWN";

        }
    }

    /**
     *
     * @param capturedTag
     * @return
     */
    public static int animalImage(String capturedTag)
    {
        switch (capturedTag)
        {
            case "27209":
                return R.drawable.img1;
            case "28233":
                return R.drawable.img2;
            case "29001":
                return R.drawable.img3;
            case "4294953522":
                return R.drawable.img4;
            case "4294955080":
                return R.drawable.img5;
            case "28489":
                return R.drawable.img6;
            default:
                return R.drawable.img7;

        }
    }

    public static String date (long timestamp)
    {
        Log.e("time", timestamp*1000 + " ");
        Date date = new Date(timestamp * 1000);
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return smf.format(date);
    }

    /**
     * Encode user email to use it as a Firebase key (Firebase does not allow "." in the key name)
     * Encoded email is also used as "userEmail", list and item "owner" value
     */
    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    /**
     * Checking for network access
     *
     * @return boolean
     */
    public static boolean isOnline(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
        {
            return true;
        }
        else {
            return false;
        }
    }
}
