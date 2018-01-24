package fr.wcs.wcstravel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "IsoyaKureno";

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mMyStudentsRef = mDatabase.getReference("checkpoint5/students/IsoyaKureno/hasContent");
    final Calendar mMyCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinnerDestAirport = findViewById(R.id.spinnerDestAirport);
        final EditText editTextDepartDate = findViewById(R.id.editTextDepartDate);
        final EditText editTextReturnDate = findViewById(R.id.editTextReturnDate);
        final Button buttonFindAFly = findViewById(R.id.buttonFindAFly);

        mMyStudentsRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean hasContent = dataSnapshot.getValue(boolean.class);
                Log.d(TAG, "Value is: " + hasContent);
            }
            @Override public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        final List<String> listDestAirport = new ArrayList<>();
        listDestAirport.add("BOS");
        listDestAirport.add("LAX");
        listDestAirport.add("MIA");
        listDestAirport.add("NYC");
        ArrayAdapter<String> destAirportDataAdapter = new ArrayAdapter<>
                (MainActivity.this, android.R.layout.simple_spinner_item, listDestAirport);
        destAirportDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestAirport.setAdapter(destAirportDataAdapter);


        final DatePickerDialog.OnDateSetListener dateDeparture = new DatePickerDialog.OnDateSetListener() {
            @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mMyCalendar.set(Calendar.YEAR, year);
                mMyCalendar.set(Calendar.MONTH, monthOfYear);
                mMyCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateActu(editTextDepartDate);
            }
        };

        final DatePickerDialog.OnDateSetListener dateReturn = new DatePickerDialog.OnDateSetListener() {
            @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mMyCalendar.set(Calendar.YEAR, year);
                mMyCalendar.set(Calendar.MONTH, monthOfYear);
                mMyCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateActu(editTextReturnDate);
            }
        };

        editTextDepartDate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, dateDeparture,
                        mMyCalendar.get(Calendar.YEAR),
                        mMyCalendar.get(Calendar.MONTH),
                        mMyCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        editTextReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, dateReturn,
                        mMyCalendar.get(Calendar.YEAR),
                        mMyCalendar.get(Calendar.MONTH),
                        mMyCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        buttonFindAFly.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String destAirport = spinnerDestAirport.getSelectedItem().toString();
                String departDate = editTextDepartDate.getText().toString();
                String returnDate = editTextReturnDate.getText().toString();
                if (destAirport.equals("")||departDate.equals("")||returnDate.equals("")) {
                    Toast.makeText(MainActivity.this,"Please",Toast.LENGTH_SHORT).show();
                } else {
                    Intent toResultActivity = new Intent(MainActivity.this,ResultActivity.class);
                    toResultActivity.putExtra("destAirport",destAirport);
                    toResultActivity.putExtra("departDate",departDate);
                    toResultActivity.putExtra("returnDate",returnDate);
                    startActivity(toResultActivity);
                }
            }
        });
    }

    void dateActu(EditText editText){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(mMyCalendar.getTime()));
    }
}