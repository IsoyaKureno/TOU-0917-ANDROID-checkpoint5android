package fr.wcs.wcstravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mTravelsRef = mDatabase.getReference("checkpoint5/travels/");
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent fromMainActivity = getIntent();
        final String destAirport = fromMainActivity.getStringExtra("destAirport");
        final String departDate = fromMainActivity.getStringExtra("departDate");
        final String returnDate = fromMainActivity.getStringExtra("returnDate");

        mListView = findViewById(R.id.listViewResults);

        final List<TravelModel> travels = new ArrayList<>();

        mTravelsRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot travelSnapshot : dataSnapshot.getChildren()){

                    TravelModel aTravel = travelSnapshot.getValue(TravelModel.class);

                    String dest = aTravel.getTravel().substring(4);
                    if (dest.equals(destAirport) && aTravel.getDeparture_date().equals(departDate) && aTravel.getReturn_date().equals(returnDate)){
                        travels.add(aTravel);
                    }
                }
                mListView.setAdapter(new ResultListAdapter(ResultActivity.this,travels));
            }

            @Override public void onCancelled(DatabaseError databaseError) {}
        });


    }
}
