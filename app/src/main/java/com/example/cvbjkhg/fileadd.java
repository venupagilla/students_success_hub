package com.example.cvbjkhg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class fileadd extends AppCompatActivity {




    private String selectedsem; // Declare as a member variable
    private String selectedbatch;
    private String selectedexamtype;
    private String selectedsub;



    StorageReference storageReference;
    DatabaseReference databaseReference;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileadd);

        //code to create a button to go to view files page
        textView=findViewById(R.id.view_files_button);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), View_files.class);
                startActivity(intent);
                finish();
            }
        });



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

        //upload button
        Button upload_button= findViewById(R.id.upload_button2);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                selectedbatch = (String) parentView.getSelectedItem();
                Toast.makeText(getApplicationContext(), "Selected Batch : " + selectedbatch, Toast.LENGTH_SHORT).show();

                // Check if "C21" is selected
                if (selectedbatch.equals("C21")) {
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
                selectedsem = (String) parentView.getSelectedItem(); // Store the selected item
                Toast.makeText(getApplicationContext(), "Selected sem : " + selectedsem, Toast.LENGTH_SHORT).show();
                if (selectedsem.equals("sem 1")||selectedsem.equals("sem 2")||selectedsem.equals("sem 3")||selectedsem.equals("sem 4")||selectedsem.equals("sem 5")) {
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

        exam_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                selectedexamtype = (String) parentView.getSelectedItem();
                Toast.makeText(getApplicationContext(), "Selected Exam type : " + selectedexamtype, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected
            }
        });

        // Initialize the fourth Spinner (sub_spinner)
        Spinner sub_spinner = findViewById(R.id.spinner4);
        // You can populate sub_spinner based on selectedItem2 here
        populateSubSpinner();

        //Database
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");


        //code for uploading using upload button
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectfiles();
            }
        });
    }

    private void selectfiles(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select PDf file.."),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            Uploadfiles(data.getData());
        }
    }

    private void Uploadfiles(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("uploading..");
        progressDialog.show();

        StorageReference reference = storageReference.child("upload/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri url=uriTask.getResult();

                        String pdfname= selectedbatch + " " + selectedsem + " " + selectedexamtype + " " + selectedsub;

                        pdfClass pdfClass = new pdfClass(pdfname,url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(pdfClass);

                        Toast.makeText(fileadd.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("uploaded:"+(int)progress+"%");

                    }
                });
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
        if ("sem 1".equals(selectedsem)) {
            sub_real_items = sub_items1;
        } else if ("sem 2".equals(selectedsem)) {
            sub_real_items = sub_items2;
        } else if ("sem 3".equals(selectedsem)) {
            sub_real_items = sub_items3;
        } else if ("sem 4".equals(selectedsem)) {
            sub_real_items = sub_items4;
        } else if ("sem 5".equals(selectedsem)) {
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
                selectedsub = (String) parentView.getSelectedItem();
                Toast.makeText(getApplicationContext(), "Selected: " + selectedsub, Toast.LENGTH_SHORT).show();
                if (!selectedsub.equals("subject")) {
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
