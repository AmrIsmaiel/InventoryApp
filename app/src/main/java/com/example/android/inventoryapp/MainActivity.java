package com.example.android.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.android.inventoryapp.Data.InventoryDbHelper;
import com.example.android.inventoryapp.Data.StockItem;


public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getCanonicalName();
    InventoryDbHelper dbHelper;
    StockCursorAdapter adapter;
    int lastVisibleItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new InventoryDbHelper(this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        final ListView listView = (ListView) findViewById(R.id.list_view);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        Cursor cursor = dbHelper.readStock();
        adapter = new StockCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) return;
                final int currentFirstVisibleItem = view.getFirstVisiblePosition();
                if (currentFirstVisibleItem > lastVisibleItem) {
                    fab.show();
                } else if (currentFirstVisibleItem < lastVisibleItem) {
                    fab.hide();
                }
                lastVisibleItem = currentFirstVisibleItem;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(dbHelper.readStock());
    }

    public void clickOnViewItem(long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("itemId", id);
        startActivity(intent);
    }

    public void clickOnSale(long id, int quantity) {
        dbHelper.sellOneItem(id, quantity);
        adapter.swapCursor(dbHelper.readStock());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                // add dummy data for testing
                addDummyData();
                adapter.swapCursor(dbHelper.readStock());
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Add data for demo purposes
     */
    private void addDummyData() {
        StockItem oranges = new StockItem(
                "Orange",
                "8 €",
                45,
                "Som3a Market",
                "+20101010101010",
                "som3a@market.com",
                "android.resource://com.example.android.inventoryapp/drawable/orange");
        dbHelper.insertItem(oranges);

        StockItem apples = new StockItem(
                "Apple",
                "8 €",
                50,
                "Som3a Market",
                "+20101010101010",
                "som3a@market.com",
                "android.resource://com.example.android.inventoryapp/drawable/apple");
        dbHelper.insertItem(apples);

        StockItem bananas = new StockItem(
                "Banana",
                "5 €",
                85,
                "Som3a Market",
                "+20101010101010",
                "som3a@market.com",
                "android.resource://com.example.android.inventoryapp/drawable/banana");
        dbHelper.insertItem(bananas);

        StockItem cherries = new StockItem(
                "Cherry",
                "3 €",
                70,
                "Som3a Market",
                "+20101010101010",
                "som3a@market.com",
                "android.resource://com.example.android.inventoryapp/drawable/cherry");
        dbHelper.insertItem(cherries);

        StockItem cupcakes = new StockItem(
                "Cupcake",
                "11 €",
                30,
                "Som3a Market",
                "+20101010101010",
                "som3a@market.com",
                "android.resource://com.example.android.inventoryapp/drawable/cupcake");
        dbHelper.insertItem(cupcakes);

        StockItem marshmallows = new StockItem(
                "Marshmallow",
                "2 €",
                150,
                "Som3a Market",
                "+20101010101010",
                "som3a@market.com",
                "android.resource://com.example.android.inventoryapp/drawable/marshmallow");
        dbHelper.insertItem(marshmallows);

        StockItem strawberries = new StockItem(
                "Strawberry",
                "8 €",
                90,
                "Som3a Market",
                "+20101010101010",
                "som3a@market.com",
                "android.resource://com.example.android.inventoryapp/drawable/strawberry");
        dbHelper.insertItem(strawberries);
        StockItem watermelons = new StockItem(
                "Watermelon",
                "17 €",
                15,
                "Som3a Market",
                "+20101010101010",
                "som3a@market.com",
                "android.resource://com.example.android.inventoryapp/drawable/watermelon");
        dbHelper.insertItem(watermelons);
        StockItem cola = new StockItem(
                "Cola",
                "2 €",
                45,
                "Som3a Market",
                "+20101010101010",
                "som3a@market.com",
                "android.resource://com.example.android.inventoryapp/drawable/cola");
        dbHelper.insertItem(cola);
        StockItem peaches = new StockItem(
                "Peach",
                "4 €",
                45,
                "Som3a Market",
                "+20101010101010",
                "som3a@market.com",
                "android.resource://com.example.android.inventoryapp/drawable/peach");
        dbHelper.insertItem(peaches);
    }
}