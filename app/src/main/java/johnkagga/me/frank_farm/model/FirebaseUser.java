package johnkagga.me.frank_farm.model;

import java.util.HashMap;

public class FirebaseUser {

    private String mName;
    private String mEmail;
    private HashMap<String, Object> timestampJoined;

    public FirebaseUser() {
    }

    public FirebaseUser(String name, String email, HashMap<String, Object> timestampJoined) {
        mName = name;
        mEmail = email;
        this.timestampJoined = timestampJoined;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public HashMap<String, Object> getTimestampJoined() {
        return timestampJoined;
    }
}
