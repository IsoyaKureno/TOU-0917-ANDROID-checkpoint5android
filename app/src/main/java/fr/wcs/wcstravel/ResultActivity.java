package fr.wcs.wcstravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    String actualMoney = "USD", moneyWanted = "EUR";

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

                    if (aTravel != null) {
                        String dest = aTravel.getTravel().substring(4);
                        if (dest.equals(destAirport) && aTravel.getDeparture_date().equals(departDate) && aTravel.getReturn_date().equals(returnDate)){
                            travels.add(aTravel);
                        }
                    }
                }
                mListView.setAdapter(new ResultListAdapter(ResultActivity.this,travels));
            }

            @Override public void onCancelled(DatabaseError databaseError) {}
        });

        Button buttonConvert = findViewById(R.id.buttonConvert);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                for (int i = 0; i<travels.size(); i++){
                    String p = travels.get(i).getPrice();
                    p = p.replace("$","").replace(" €","");
                    double price = Double.parseDouble(p);
                    travels.get(i).setPrice(convertPrice(price,actualMoney,moneyWanted));
                }
                String a = actualMoney;
                actualMoney = moneyWanted;
                moneyWanted = a;

                mListView.setAdapter(new ResultListAdapter(ResultActivity.this,travels));
            }
        });

    }

    String convertPrice(double originPrice, String from, String to){
        String finalPrice = "";
        if (from.equals("USD")&&to.equals("EUR")){
            finalPrice += String.valueOf(originPrice*0.806851);
            finalPrice = finalPrice.substring(0,finalPrice.lastIndexOf(".")+3);
            finalPrice += " €";
        } else {
            finalPrice += "$";
            finalPrice += String.valueOf(originPrice*1.23920);
            finalPrice = finalPrice.substring(0,finalPrice.lastIndexOf(".")+3);
        }
        return finalPrice;
    }
}
