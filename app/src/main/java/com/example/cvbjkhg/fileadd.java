package com.example.cvbjkhg;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;
import android.os.Bundle;

public class fileadd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileadd);

        // Define an array of items to display in the Spinner
        String[] items = {"Batch", "C21"};
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Initialize the second Spinner (spinner2)
        Spinner sem_spinner = findViewById(R.id.spinner2);
        String[] sem_items = {"sem","sem 1", "sem 2", "sem 3", "sem 4", "sem 5"};
        ArrayAdapter<String> semAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sem_items);
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem_spinner.setAdapter(semAdapter);

        //initializing the third spinner
        Spinner exam_spinner = findViewById(R.id.spinner3);
        String[] exam_items = {"Mid 1","Mid 2","Sem"};
        ArrayAdapter<String> examAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exam_items);
        examAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exam_spinner.setAdapter(examAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem = (String) parentView.getSelectedItem();
                Toast.makeText(getApplicationContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

                // Check if "C21" is selected
                if (selectedItem.equals("C21")) {
                    // Show the second Spinner (spinner2)
                    sem_spinner.setVisibility(View.VISIBLE);
                } else {
                    // Hide the second Spinner (spinner2)
                    sem_spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected
            }
        });

        // Set an OnItemSelectedListener for spinner2
        sem_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem2 = (String) parentView.getSelectedItem();
                Toast.makeText(getApplicationContext(), "Selected (Spinner 2): " + selectedItem2, Toast.LENGTH_SHORT).show();
                if (selectedItem2.equals("sem 1") || selectedItem2.equals("sem 2") || selectedItem2.equals("sem 3") || selectedItem2.equals("sem 4") || selectedItem2.equals("sem 5")) {
                    // Show the second Spinner (spinner2)
                    exam_spinner.setVisibility(View.VISIBLE);
                } else {
                    // Hide the second Spinner (spinner2)
                    exam_spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected in Spinner 2
            }
        });
    }
}
