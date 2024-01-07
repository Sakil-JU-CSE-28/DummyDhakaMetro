package com.example.dummydhakametro;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    Set<String> stations;
    String sourceStation,destinationStation;
    Button minDistBtn,minCostBtn;
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView1;
    ArrayAdapter<String>adapterStation,adapterStation1;
    public void storeStations() {
        stations = new HashSet<>();
        // Load the file using getResources() to access resources in the 'raw' directory
        InputStream inputStream = getResources().openRawResource(R.raw.stations);
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNext()) {
            String cur = "";
            String token = scanner.next(); // Tokenizing by words
            for(int i = 0;i < token.length();i++){
                if(token.charAt(i) == '-'){
                    stations.add(cur);
                    cur = "";
                }
                else
                    cur += token.charAt(i);
            }
            stations.add(cur);
        }

        scanner.close();

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storeStations();
        ArrayList<String>stationView = new ArrayList<>();
        for(String i : stations){
            stationView.add(i);
        }
        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        adapterStation = new ArrayAdapter<String>(this,R.layout.list_station,stationView);
        autoCompleteTextView.setAdapter(adapterStation);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sourceStation = adapterStation.getItem(position).toString();
                Toast.makeText(MainActivity.this,"Your select " + sourceStation,Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView1 = findViewById(R.id.autoCompleteText1);
        adapterStation1 = new ArrayAdapter<String>(this,R.layout.list_station,stationView);
        autoCompleteTextView1.setAdapter(adapterStation1);

        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                destinationStation = adapterStation1.getItem(position).toString();
                Toast.makeText(MainActivity.this,"Your select " + destinationStation,Toast.LENGTH_SHORT).show();
            }
        });

        minCostBtn = findViewById(R.id.mincostSelector);
        minDistBtn = findViewById(R.id.mintdistselector);

        minDistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sourceStation.equals(destinationStation)){
                    Toast.makeText(MainActivity.this,"Starting and Ending must be different!!",Toast.LENGTH_SHORT).show();
                }
                else{
                   ///Implementation of Dijkstra

                    Toast.makeText(MainActivity.this,"Stations List for min Distance from " + sourceStation + " to " + destinationStation,Toast.LENGTH_SHORT).show();
                }
            }
        });

        minCostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sourceStation.equals(destinationStation)){
                    Toast.makeText(MainActivity.this,"Starting and Ending must be different!!",Toast.LENGTH_SHORT).show();
                }
                else{
                    ///Implementation of Dijkstra

                    Toast.makeText(MainActivity.this,"Stations List for min Cost from " + sourceStation + " to " + destinationStation,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
