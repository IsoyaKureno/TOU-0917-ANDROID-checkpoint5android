package fr.wcs.wcstravel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "IsoyaKureno";

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mMyStudentsRef = mDatabase.getReference("students/IsoyaKureno");
    final Calendar mMyCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextDestAirport = findViewById(R.id.editTextDestAirport);
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
                String destAirport = editTextDestAirport.getText().toString();
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
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(mMyCalendar.getTime()));
    }
}