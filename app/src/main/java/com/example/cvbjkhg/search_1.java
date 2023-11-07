package com.example.cvbjkhg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.cvbjkhg.pdfClass;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class search_1 extends AppCompatActivity {

    private String selectedsem_1;
    private String selectedbatch_1;
    private String selectedexamtype_1;
    private String selectedsub_1;
    private String fileUrl;
    ListView listView;
    DatabaseReference databaseReference;
    List<pdfClass> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search1);

        Button openFileButton = findViewById(R.id.search_button_2);

        openFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected criteria (batch, semester, exam type, subject)
                String selectedCriteria = getSelectedCriteria();

                // Construct a path or unique identifier for the file in Firebase Storage
                String filePath = "upload/"+ selectedCriteria +".pdf"; // Adjust the path as needed

                // Retrieve the file from Firebase Storage
                retrieveFileFromStorage(filePath);
            }
        });

        String[] items = {"Batch", "C21"};
        Spinner spinner_1 = findViewById(R.id.spinner_1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_1.setAdapter(adapter);

        Spinner sem_spinner = findViewById(R.id.spinner_2);
        String[] sem_items = {"sem", "sem 1", "sem 2", "sem 3", "sem 4", "sem 5"};
        ArrayAdapter<String> semAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sem_items);
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem_spinner.setAdapter(semAdapter);

        Spinner exam_spinner = findViewById(R.id.spinner_3);
        String[] exam_items = {"Mid 1", "Mid 2", "Sem"};
        ArrayAdapter<String> examAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exam_items);
        examAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exam_spinner.setAdapter(examAdapter);

        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedbatch_1 = (String) parentView.getSelectedItem();
                Toast.makeText(getApplicationContext(), "Selected Batch : " + selectedbatch_1, Toast.LENGTH_SHORT).show();

                if (selectedbatch_1.equals("C21")) {
                    sem_spinner.setVisibility(View.VISIBLE);
                } else {
                    sem_spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sem_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedsem_1 = (String) parentView.getSelectedItem();
                Toast.makeText(getApplicationContext(), "Selected sem : " + selectedsem_1, Toast.LENGTH_SHORT).show();
                if ("sem 1".equals(selectedsem_1) || "sem 2".equals(selectedsem_1) || "sem 3".equals(selectedsem_1) || "sem 4".equals(selectedsem_1) || "sem 5".equals(selectedsem_1)) {
                    exam_spinner.setVisibility(View.VISIBLE);
                } else {
                    exam_spinner.setVisibility(View.GONE);
                }
                populateSubSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        exam_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedexamtype_1 = (String) parentView.getSelectedItem();
                Toast.makeText(getApplicationContext(), "Selected Exam type : " + selectedexamtype_1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        Spinner sub_spinner = findViewById(R.id.spinner_4);
        populateSubSpinner();
    }

    private void retrieveFileFromStorage(String filePath) {
        // Use Firebase Storage SDK to retrieve the file based on the filePath
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePath);

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Handle the download URL, and then open the file in ViewFileActivity
                openFileInViewFileActivity(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle the failure to retrieve the file
                Toast.makeText(search_1.this, "File not found.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFileInViewFileActivity(String toString) {
        // Create an Intent to open ViewFileActivity and pass the file URL
        Intent intent = new Intent(search_1.this, ViewFileActivity.class);
        intent.putExtra("file_url", toString);
        startActivity(intent);
    }

    private String getSelectedCriteria() {
        return selectedbatch_1 + "_" + selectedsem_1 + "_" + selectedexamtype_1 + "_" + selectedsub_1;
    }

    private void populateSubSpinner () {
        String[] sub_items1 = {"subject", "Mathematics", "Physics", "Chemistry", "BEEE", "English"};
        String[] sub_items2 = {"subject", "Programming in C", "Mathematics", "Physics", "Chemistry", "English"};
        String[] sub_items3 = {"subject", "Mathematics", "Digital electronics", "Data structures through C", "Computer architecture", "Fundamentals of Artificial intelligence"};
        String[] sub_items4 = {"subject", "Mathematics", "Relational Database Management Systems", "Data Visualization", "Computer Hardware & Networking", "Python Programming for Artificial Intelligence"};
        String[] sub_items5 = {"subject", "Industrial management and entrepreneurship", "Cloud computing", "Fundamentals of image processing", "Internet of Things", "Machine learning"};
        String[] sub_real_items;

        if ("sem 1".equals(selectedsem_1)) {
            sub_real_items = sub_items1;
        } else if ("sem 2".equals(selectedsem_1)) {
            sub_real_items = sub_items2;
        } else if ("sem 3".equals(selectedsem_1)) {
            sub_real_items = sub_items3;
        } else if ("sem 4".equals(selectedsem_1)) {
            sub_real_items = sub_items4;
        } else if ("sem 5".equals(selectedsem_1)) {
            sub_real_items = sub_items5;
        } else {
            sub_real_items = new String[0];
        }

        Spinner sub_spinner = findViewById(R.id.spinner_4);
        ArrayAdapter<String> subAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sub_real_items);
        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_spinner.setAdapter(subAdapter);

        sub_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedsub_1 = (String) parentView.getSelectedItem();
                Toast.makeText(getApplicationContext(), "Selected: " + selectedsub_1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
}
