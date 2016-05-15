package johnkagga.me.frank_farm.utils;


import johnkagga.me.frank_farm.BuildConfig;

public class Constants {

    public static final String KEY_PROVIDER = "PROVIDER";
    public static final String KEY_ENCODED_EMAIL = "ENCODED_EMAIL";

    //Firebase Url
    public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;

    //Farm node
    public static final String FARM_NODE = "farm";

    //BArn Area
    public static final String BARN_AREA = "barnArea";

    public static final String FIREBASE_FARM_URL = FIREBASE_URL + "/" +FARM_NODE;

    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;

    /**
     * Constants for Firebase login
     */
    public static final String PASSWORD_PROVIDER = "password";
    public static final String GOOGLE_PROVIDER = "google";
    public static final String PROVIDER_DATA_DISPLAY_NAME = "displayName";

    public static final String FIREBASE_PROPERTY_EMAIL = "email";
}
