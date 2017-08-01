package sg.edu.rp.c346.firebaseinventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InventoryDetailsActivity extends AppCompatActivity {

    private static final String TAG = "InventoryDetailsActivity";

    private EditText etName, etCost;
    private Button btnUpdate, btnDelete;

    private Inventory inventory;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference inventoryListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);

        etName = (EditText)findViewById(R.id.editTextName);
        etCost = (EditText)findViewById(R.id.editTextCost);
        btnUpdate = (Button)findViewById(R.id.buttonUpdate);
        btnDelete = (Button)findViewById(R.id.buttonDelete);

        firebaseDatabase = FirebaseDatabase.getInstance();
        inventoryListRef = firebaseDatabase.getReference("/inventoryList");

        Intent intent = getIntent();
        inventory = (Inventory) intent.getSerializableExtra("Inventory");

        //Display Student details as retrieved from the intent
        etName.setText(inventory.getName());
        etCost.setText(String.valueOf(inventory.getUnitCost()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Update Student record based on input given

// Point X
                String name = etName.getText().toString();
                double cost = Double.parseDouble(etCost.getText().toString());
                Inventory inventory1 = new Inventory(name, cost);
                String id = inventory.getId();

//                studentListRef.setValue(student);
                inventoryListRef.child(id).setValue(inventory1);


                Toast.makeText(getApplicationContext(), "Inventory record updated successfully", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Delete Student record based on student id

// Point X
                String id = inventory.getId();

//                studentListRef.setValue(student);
                inventoryListRef.child(id).removeValue();




                Toast.makeText(getApplicationContext(), "Inventory record deleted successfully", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
