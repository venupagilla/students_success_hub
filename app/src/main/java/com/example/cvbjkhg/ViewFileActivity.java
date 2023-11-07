package com.example.cvbjkhg;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class ViewFileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);

        // Get the file URL passed from the previous activity
        String fileUrl = getIntent().getStringExtra("file_url");

        // Open the PDF file in an external PDF viewer
        openPdfInViewer(fileUrl);
    }

    private void openPdfInViewer(String fileUrl) {
        if (fileUrl != null) {
            // Create an Intent to open a PDF viewer with the file's URL
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(fileUrl), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(Intent.createChooser(intent, "Open with"));
                finish(); // Close the ViewFileActivity
            } catch (ActivityNotFoundException e) {
                // Handle the case where a suitable PDF viewer is not installed
                Toast.makeText(this, "No PDF viewer app installed.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case where the fileUrl is null
            Toast.makeText(this, "File URL is null.", Toast.LENGTH_SHORT).show();
            finish(); // Close the ViewFileActivity
        }
    }

}
