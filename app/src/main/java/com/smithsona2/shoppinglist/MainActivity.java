package com.smithsona2.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

/**
 * This activity begins an empty shopping list that can be added to by
 * tapping the floating action button.
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG =MainActivity.class.getSimpleName();
    private static final LinkedList<String> mGroceryList = new LinkedList<>();
    private static final int GROCERY_REQUEST = 1;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "onCreate");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Log.d(LOG_TAG, "Add Grocery Button clicked!");
            Intent intent = new Intent(this, SecondActivity.class);
            startActivityForResult(intent, GROCERY_REQUEST);
        });

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ListAdapter(this, mGroceryList, null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Recieves the selected common food item from the second activity
     * and adds it to the grocery list
     * @param requestCode
     * @param resultCode
     * @param data contains the common food item selected
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GROCERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                int mGroceryListSize = mGroceryList.size();
                String selectedItem = data.getStringExtra(SecondActivity.COMMON_ITEM);

                mGroceryList.add(selectedItem);
                mAdapter.notifyItemInserted(mGroceryListSize);
            }
        }
    }

}