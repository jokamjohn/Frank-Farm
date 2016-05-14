package johnkagga.me.frank_farm.utils;

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
}
