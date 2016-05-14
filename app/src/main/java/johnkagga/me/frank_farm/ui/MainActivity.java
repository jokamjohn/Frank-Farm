package johnkagga.me.frank_farm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import johnkagga.me.frank_farm.R;
import johnkagga.me.frank_farm.adapters.FarmAreasListAdapter;
import johnkagga.me.frank_farm.model.TrackData;
import johnkagga.me.frank_farm.utils.Constants;

public class MainActivity extends AppCompatActivity {

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

        mTrackdataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TrackData selectedTrackData = mTrackDataListAdapter.getItem(position);
                String listId = mTrackDataListAdapter.getRef(position).getKey();

                if (selectedTrackData != null)
                {
                    Intent intent = new Intent(MainActivity.this,DetailActivity.class);

                    listId = mTrackDataListAdapter.getRef(position).getKey();

                    intent.putExtra(LIST_ID,listId);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    protected void onDestroy() {
        super.onDestroy();
        mTrackDataListAdapter.cleanup();
    }

}
