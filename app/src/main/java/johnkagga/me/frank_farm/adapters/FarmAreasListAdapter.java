package johnkagga.me.frank_farm.adapters;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import johnkagga.me.frank_farm.R;
import johnkagga.me.frank_farm.model.TrackData;
import johnkagga.me.frank_farm.utils.Helper;


public class FarmAreasListAdapter extends FirebaseListAdapter<TrackData> {


    public FarmAreasListAdapter(Activity activity, Class<TrackData> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);

        this.mActivity = activity;
    }

    @Override
    protected void populateView(View view, TrackData trackData) {

        TextView tagName = (TextView) view.findViewById(R.id.animalName);
//        TextView readerLocation = (TextView) view.findViewById(R.id.reader_station_textView);
        TextView dateRead = (TextView) view.findViewById(R.id.date);
        TextView reader = (TextView) view.findViewById(R.id.reader);

        tagName.setText(Helper.animalName(String.valueOf(trackData.getCapturedTag())));
//        readerLocation.setText(trackData.getLocation());
        dateRead.setText(Helper.date(Long.parseLong(trackData.getTime())));
        reader.setText(trackData.getReader());
    }


}
