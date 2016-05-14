package johnkagga.me.frank_farm.utils;


import johnkagga.me.frank_farm.BuildConfig;

public class Constants {

    //Firebase Url
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;

    //Farm node
    public static final String FARM_NODE = "farm";

    //BArn Area
    public static final String BARN_AREA = "barnArea";

    public static final String FIREBASE_FARM_URL = FIREBASE_URL + "/" +FARM_NODE;
}
