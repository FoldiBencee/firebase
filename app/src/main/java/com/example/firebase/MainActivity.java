package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private Button buttonkuldes;
    private EditText editfelhasznalonev, editjelszo, editemail;
    private Tagok tagok;
    private long maxid;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        buttonkuldes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editfelhasznalonev.getText().toString().isEmpty() ||
                        editjelszo.getText().toString().isEmpty() ||
                        editemail.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Minen mezot ki kell tolteni", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    tagok.setFelhasznalonev(editfelhasznalonev.getText().toString());
                    tagok.setEmail(editemail.getText().toString());
                    tagok.setJelszo(editjelszo.getText().toString());
                    databaseReference.child(String.valueOf(maxid+1)).setValue(tagok);
                }
            }
        });
    }



    public  void init()
    {
        buttonkuldes = findViewById(R.id.buttonkuldes);
        editfelhasznalonev = findViewById(R.id.edittexemail);
        editemail = findViewById(R.id.edittexemail);
        editjelszo = findViewById(R.id.edittextjelszo);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Felhasználók");
        tagok = new Tagok();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
