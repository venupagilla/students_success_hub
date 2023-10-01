package com.example.cvbjkhg;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;
import android.os.Bundle;

public class fileadd extends AppCompatActivity {

    private String selectedItem2; // Declare as a member variable

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
        String[] sem_items = {"sem", "sem 1", "sem 2", "sem 3", "sem 4", "sem 5"};
        ArrayAdapter<String> semAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sem_items);
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem_spinner.setAdapter(semAdapter);

        //initializing the third spinner
        Spinner exam_spinner = findViewById(R.id.spinner3);
        String[] exam_items = {"Mid 1", "Mid 2", "Sem"};
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
                selectedItem2 = (String) parentView.getSelectedItem(); // Store the selected item
                Toast.makeText(getApplicationContext(), "Selected (Spinner 2): " + selectedItem2, Toast.LENGTH_SHORT).show();
                if (selectedItem2.equals("sem 1")||selectedItem2.equals("sem 2")||selectedItem2.equals("sem 3")||selectedItem2.equals("sem 4")||selectedItem2.equals("sem 5")) {
                    // Show the second Spinner (spinner2)
                    exam_spinner.setVisibility(View.VISIBLE);
                } else {
                    // Hide the second Spinner (spinner2)
                    exam_spinner.setVisibility(View.GONE);
                }
                // Based on selectedItem2, you can populate sub_spinner here
                populateSubSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected in Spinner 2
            }
        });

        // Initialize the fourth Spinner (sub_spinner)
        Spinner sub_spinner = findViewById(R.id.spinner4);
        // You can populate sub_spinner based on selectedItem2 here
        populateSubSpinner();
    }

    private void populateSubSpinner() {
        // Define arrays of subjects for different semesters
        String[] sub_items1 = {"subject","Mathematics", "Physics", "Chemistry", "BEEE", "English"};
        String[] sub_items2 = {"subject","Programming in C", "Mathematics", "Physics", "Chemistry", "English"};
        String[] sub_items3 = {"subject","Mathematics", "Digital electronics", "Data structures through C", "Computer architecture", "Fundamentals of Artificial intelligence"};
        String[] sub_items4 = {"subject","Mathematics", "Relational Database Management Systems", "Data Visualization", "Computer Hardware & Networking", "Python Programming for Artificial Intelligence"};
        String[] sub_items5 = {"subject","Industrial management and entrepreneurship", "Cloud computing", "Fundamentals of image processing", "Internet of Things", "Machine learning"};
        String[] sub_real_items;

        // Determine which array to use based on selectedItem2
        if ("sem 1".equals(selectedItem2)) {
            sub_real_items = sub_items1;
        } else if ("sem 2".equals(selectedItem2)) {
            sub_real_items = sub_items2;
        } else if ("sem 3".equals(selectedItem2)) {
            sub_real_items = sub_items3;
        } else if ("sem 4".equals(selectedItem2)) {
            sub_real_items = sub_items4;
        } else if ("sem 5".equals(selectedItem2)) {
            sub_real_items = sub_items5;
        } else {
            // Handle other cases or set a default array
            sub_real_items = new String[0]; // Empty array as a placeholder
        }

        // Populate sub_spinner with the appropriate array of subjects
        Spinner sub_spinner = findViewById(R.id.spinner4);
        ArrayAdapter<String> subAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sub_real_items);
        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_spinner.setAdapter(subAdapter);

        Button upload_button2 = findViewById(R.id.upload_button2);
        upload_button2.setVisibility(View.GONE);


        sub_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selected_sub_Item = (String) parentView.getSelectedItem();
                Toast.makeText(getApplicationContext(), "Selected: " + selected_sub_Item, Toast.LENGTH_SHORT).show();
                if (!selected_sub_Item.equals("subject")) {
                    upload_button2.setVisibility(View.VISIBLE);
                }else{
                    upload_button2.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected
            }
        });
    }
}
