package com.smithsona2.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

/**
 * This activity involves buttons of common food items that will be added to
 * the shopping list in the main activity when clicked. When an item is clicked,
 * it is removed from the list of common food items.
 */
public class SecondActivity extends AppCompatActivity {

    public static final String COMMON_ITEM = "com.smithsona2.shoppinglist.extra.CHOICE";
    private static final String LOG_TAG = SecondActivity.class.getSimpleName();
    private static final LinkedList<String> mCommonList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(LOG_TAG, "onCreate");
        if (mCommonList.size() < 1) {
            createCommonlist();
        }
        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new ListAdapter(this, mCommonList, new ClickListener() {
            @Override
            public void onPositionClicked(int mPosition) {
                String commonItem = mCommonList.get(mPosition);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(COMMON_ITEM, commonItem);
                setResult(RESULT_OK, returnIntent);
                Log.d(LOG_TAG, "End SecondActivity");
                finish();
            }

            @Override
            public void onLongClicked(int position) {
            }
        });
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    /**
     * adds common food items to mCommonList
     */
    private void createCommonlist() {
        mCommonList.add("Apple");
        mCommonList.add("Banana");
        mCommonList.add("Lemons");
        mCommonList.add("Limes");
        mCommonList.add("Carrots");
        mCommonList.add("Celery");
        mCommonList.add("Salad");
        mCommonList.add("Potatoes");
        mCommonList.add("Onions");
        mCommonList.add("Bread");
        mCommonList.add("Milk");
        mCommonList.add("Cheese");
        mCommonList.add("Eggs");
        mCommonList.add("Chicken");
        mCommonList.add("Bacon");
        mCommonList.add("Cooking Oil");
    }
}
