package johnkagga.me.frank_farm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import johnkagga.me.frank_farm.BaseActivity;
import johnkagga.me.frank_farm.R;
import johnkagga.me.frank_farm.adapters.FarmAreasListAdapter;
import johnkagga.me.frank_farm.model.TrackData;
import johnkagga.me.frank_farm.utils.Constants;

public class MainActivity extends BaseActivity {

    public static final String LIST_ID = "list_id";
    private static final String TAG = "Mainactivity-log-tag";
    protected ListView mTrackdataListView;
    protected FarmAreasListAdapter mTrackDataListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTrackdataListView = (ListView) findViewById(R.id.areas_list_view);

        TextView emptyTV = new TextView(this);
        emptyTV.setText("No Data at the moment");

        mTrackdataListView.setEmptyView(emptyTV);

        mTrackdataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TrackData selectedTrackData = mTrackDataListAdapter.getItem(position);
                String listId = mTrackDataListAdapter.getRef(position).getKey();

                if (selectedTrackData != null) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                    intent.putExtra(LIST_ID, listId);

                    startActivity(intent);
                }
            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        Firebase firebase = new Firebase(Constants.FIREBASE_URL);

        Firebase farm = firebase.child(Constants.FARM_NODE);

        Query mOrderedTrackData = farm.limitToLast(100);

        mTrackDataListAdapter = new FarmAreasListAdapter(this,
                TrackData.class, R.layout.tag_list_item, mOrderedTrackData);

        mTrackdataListView.setAdapter(mTrackDataListAdapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTrackDataListAdapter.cleanup();
    }

}
