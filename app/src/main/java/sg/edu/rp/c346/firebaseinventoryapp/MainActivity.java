package sg.edu.rp.c346.firebaseinventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView lvInventory;
    private ArrayList<Inventory> alInventory;
    private ArrayAdapter<Inventory> aaInventory;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference inventoryListRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvInventory = (ListView)findViewById(R.id.listViewInventory);
        alInventory = new ArrayList<Inventory>();
        aaInventory = new ArrayAdapter<Inventory>(this, android.R.layout.simple_list_item_1, alInventory);
        lvInventory.setAdapter(aaInventory);

        firebaseDatabase = FirebaseDatabase.getInstance();
        inventoryListRef = firebaseDatabase.getReference("inventoryList");

        lvInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Inventory inventoryList = (Inventory)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, InventoryDetailsActivity.class);
                intent.putExtra("Inventory", inventoryList);
                startActivity(intent);
            }
        });

        inventoryListRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("MainActivity", "onChildAdded()");
                Inventory inventory = dataSnapshot.getValue(Inventory.class);
                if (inventory != null) {
                    inventory.setId(dataSnapshot.getKey());

                    alInventory.add(inventory);
                    aaInventory.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i("MainActivity", "onChildChanged()");

                String selectedId = dataSnapshot.getKey();
                Inventory inventory = dataSnapshot.getValue(Inventory.class);
                if (inventory != null) {
                    for (int i = 0; i < alInventory.size(); i++) {
                        if (alInventory.get(i).getId().equals(selectedId)) {
                            inventory.setId(selectedId);
                            alInventory.set(i, inventory);
                        }
                    }
                    aaInventory.notifyDataSetChanged();
                }
            }


            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("MainActivity", "onChildRemoved()");

                String selectedId = dataSnapshot.getKey();
                for(int i= 0; i < alInventory.size(); i++) {
                    if (alInventory.get(i).getId().equals(selectedId)) {
                        alInventory.remove(i);
                    }
                }
                aaInventory.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i("MainActivity", "onChildMoved()");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MainActivity", "Database error occurred", databaseError.toException());

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddInventoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
