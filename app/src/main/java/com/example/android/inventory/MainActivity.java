package com.example.android.inventory;

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

import com.example.android.inventory.data.InventoryDbHelper;
import com.example.android.inventory.data.StockItem;

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
                if(scrollState == 0) return;
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
        StockItem eloquentJava = new StockItem(
                "Eloquent JavaScript",
                "$35.00",
                23,
                "BOOKSRUS",
                "647-555-1234",
                "info@booksrus.com",
                "android.resource://com.example.android.inventory/drawable/ej2");
        dbHelper.insertItem(eloquentJava);

        StockItem dontThink = new StockItem(
                "Don\'t Make Me Think",
                "$30.00",
                11,
                "BOOKSRUS",
                "647-555-1234",
                "info@booksrus.com",
                "android.resource://com.example.android.inventory/drawable/dontmakemethink");
        dbHelper.insertItem(dontThink);

        StockItem python = new StockItem(
                "Learning Python",
                "$52.00",
                15,
                "BOOKSRUS",
                "647-555-1234",
                "info@booksrus.com",
                "android.resource://com.example.android.inventory/drawable/python");
        dbHelper.insertItem(python);

        StockItem crackingCode = new StockItem(
                "Cracking the Coding...",
                "$35.00",
                22,
                "BOOKSRUS",
                "647-555-1234",
                "info@booksrus.com",
                "android.resource://com.example.android.inventory/drawable/crack");
        dbHelper.insertItem(crackingCode);

        StockItem cleanCode = new StockItem(
                "Clean Code",
                "$25.00",
                34,
                "BOOKSRUS",
                "647-555-1234",
                "info@booksrus.com",
                "android.resource://com.example.android.inventory/drawable/cleancode");
        dbHelper.insertItem(cleanCode);

        StockItem javascript = new StockItem(
                "JavaScript:...",
                "$43.00",
                26,
                "BOOKSRUS",
                "647-555-1234",
                "info@booksrus.com",
                "android.resource://com.example.android.inventory/drawable/js");
        dbHelper.insertItem(javascript);

        StockItem designPatterns = new StockItem(
                "Design Patterns",
                "$56.00",
                12,
                "BOOKSRUS",
                "647-555-1234",
                "info@booksrus.com",
                "android.resource://com.example.android.inventory/drawable/design");
        dbHelper.insertItem(designPatterns);

        StockItem codeComplete = new StockItem(
                "Code Complete",
                "$60.00",
                46,
                "BOOKSRUS",
                "647-555-1234",
                "info@booksrus.com",
                "android.resource://com.example.android.inventory/drawable/codecomplete");
        dbHelper.insertItem(codeComplete);

        StockItem refactoring = new StockItem(
                "Refactoring",
                "$53.00",
                33,
                "BOOKSRUS",
                "647-555-1234",
                "info@booksrus.com",
                "android.resource://com.example.android.inventory/drawable/refactoring");
        dbHelper.insertItem(refactoring);

        StockItem effectiveJava = new StockItem(
                "Effective Java",
                "$47.00",
                22,
                "BOOKSRUS",
                "647-555-1234",
                "info@booksrus.com",
                "android.resource://com.example.android.inventory/drawable/ejava");
        dbHelper.insertItem(effectiveJava);
    }
}