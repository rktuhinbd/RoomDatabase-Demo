package com.rktuhinbd.roomdatabasedemo.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.rktuhinbd.roomdatabasedemo.R;
import com.rktuhinbd.roomdatabasedemo.adapter.ItemListAdapter;
import com.rktuhinbd.roomdatabasedemo.database.MyData;
import com.rktuhinbd.roomdatabasedemo.database.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // = = = Initialize view properties = = = //
    private TextInputEditText inputEditText;
    private Button resetButton;
    private Button addButton;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ItemListAdapter listAdapter;

    // = = = Initialize data properties = = = //
    private RoomDB roomDB;
    private List<MyData> myDataList = new ArrayList<>();
    private MyData myData = new MyData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initProperties();
        initRecyclerView();
        buttonClickListener();

    }

    private void initProperties() {

        // ~ ~ ~ Assign view & data properties ~ ~ ~ //
        inputEditText = findViewById(R.id.tiet_input);
        resetButton = findViewById(R.id.btn_reset);
        addButton = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.rv_items);

        roomDB = RoomDB.getInstance(this);
        myDataList = roomDB.dao().getAllData();
    }

    private void initRecyclerView() {

        // ~ ~ ~ Recyclerview initialization and data manipulation ~ ~ ~ //
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new ItemListAdapter(this, myDataList);
        recyclerView.setAdapter(listAdapter);
    }

    private void updateRecyclerView() {
        myDataList.clear();
        myDataList.addAll(roomDB.dao().getAllData());
        listAdapter.notifyDataSetChanged();
    }

    private void buttonClickListener() {

        // ~ ~ ~ Assign add button click listener ~ ~ ~ //
        addButton.setOnClickListener(v -> {
            String inputText = inputEditText.getText().toString();

            if (inputText.isEmpty()) {
                Toast.makeText(this, "Input can't be empty", Toast.LENGTH_SHORT).show();
            } else {
                inputEditText.setText("");
                myData.setText(inputText);
                roomDB.dao().insert(myData);
                updateRecyclerView();
            }
        });


        // ~ ~ ~ Assign reset button click listener ~ ~ ~ //
        resetButton.setOnClickListener(v -> {

            roomDB.dao().deleteAll(myDataList);
            updateRecyclerView();
        });
    }

}