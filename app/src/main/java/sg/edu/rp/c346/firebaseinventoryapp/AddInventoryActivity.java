package sg.edu.rp.c346.firebaseinventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddInventoryActivity extends AppCompatActivity {
    private static final String TAG = "AddInventoryActivity";

    private EditText etName, etCost;
    private Button btnAdd;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference inventoryListRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        etName = (EditText)findViewById(R.id.editTextName);
        etCost = (EditText)findViewById(R.id.editTextCost);
        btnAdd = (Button)findViewById(R.id.buttonAdd);

        firebaseDatabase = FirebaseDatabase.getInstance();
        inventoryListRef = firebaseDatabase.getReference("/inventoryList");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO…

                String name = etName.getText().toString();
                double cost = Double.parseDouble(etCost.getText().toString());
                Inventory inventory = new Inventory(name, cost);

//                studentListRef.setValue(student);
                inventoryListRef.push().setValue(inventory);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }

}
