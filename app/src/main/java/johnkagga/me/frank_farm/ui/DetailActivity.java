package johnkagga.me.frank_farm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import johnkagga.me.frank_farm.R;
import johnkagga.me.frank_farm.model.TrackData;
import johnkagga.me.frank_farm.utils.Constants;
import johnkagga.me.frank_farm.utils.Helper;

public class DetailActivity extends AppCompatActivity {

    protected TextView mAnimalName, mCapturedTag, mReader, mLocation;
    protected Firebase mAnimalTrackData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();

        if (receiveIntent()) return;

        fab();
    }

    /**
     * Receive the intent
     * @return
     */
    private boolean receiveIntent() {
        Intent intent = getIntent();
        //Get the Push Id
        String mListId = intent.getStringExtra(MainActivity.LIST_ID);

        if (mListId == null)
        {
            /**
             * No need of continuing without a Push ID.
             */
            finish();
            return true;
        }

        //Get a reference to the data object in firebase
        mAnimalTrackData = new Firebase(Constants.FIREBASE_FARM_URL).child(mListId);

        /**
         * Add the data value listener so that we track the changes in data.
         */
        mAnimalTrackData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TrackData trackData = dataSnapshot.getValue(TrackData.class);

                mAnimalName.setText(Helper.animalName(String.valueOf(trackData.getCapturedTag())));
                mCapturedTag.setText(String.valueOf(trackData.getCapturedTag()));
                mReader.setText(trackData.getReader());
                mLocation.setText(trackData.location);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return false;
    }

    /**
     *
     */
    private void fab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     *
     */
    private void initializeViews() {
        mAnimalName = (TextView) findViewById(R.id.animal_name);
        mCapturedTag = (TextView) findViewById(R.id.captured_tag);
        mReader = (TextView) findViewById(R.id.reader);
        mLocation = (TextView) findViewById(R.id.location);
    }
}
