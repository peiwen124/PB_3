package com.example.celineneohpeiwen.pb_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import com.example.celineneohpeiwen.pb_3.Common.CustomAdapter;
import com.example.celineneohpeiwen.pb_3.Model.Ranking;
import com.example.celineneohpeiwen.pb_3.DbHelper.DbHelper;

public class Score extends AppCompatActivity {

    ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        lstView = (ListView)findViewById(R.id.lstRanking);
        DbHelper db = new DbHelper(this);
        List<Ranking> lstRanking = db.getRanking();
        if(lstRanking.size() > 0) {
            CustomAdapter adapter = new CustomAdapter(this, lstRanking);
            lstView.setAdapter(adapter);
        }
    }
}
