package fr.wcs.wcstravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ListView listView = findViewById(R.id.listViewResults);

        List<TravelModel> travels = new ArrayList<>();

        ResultListAdapter adapter = new ResultListAdapter
                (ResultActivity.this,travels);
        listView.setAdapter(adapter);
    }
}
