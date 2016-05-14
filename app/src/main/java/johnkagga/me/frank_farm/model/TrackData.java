package johnkagga.me.frank_farm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class TrackData {

    public String Reader;

    public String location;

    public long capturedTag;

    public String time;

    @JsonIgnoreProperties(ignoreUnknown = true)

    public TrackData() {
    }


    public String getReader() {
        return Reader;
    }

    public String getLocation() {
        return location;
    }

    public long getCapturedTag() {
        return capturedTag;
    }

    public String getTime() {
        return time;
    }
}
