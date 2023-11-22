package com.example.cvbjkhg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_files extends AppCompatActivity {

    ListView listView;
    DatabaseReference databaseReference;
    List<pdfClass> uploads;
    Button search_button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_files);

        search_button7=findViewById(R.id.search_button);
        search_button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), search_1.class);
                startActivity(intent);
            }
        });

        listView=findViewById(R.id.files_view);
        uploads = new ArrayList<>();

        viewfiles();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pdfClass pdfupload = uploads.get(i);

                // Assuming pdfClass contains a field with the URL of the PDF file
                String pdfUrl = pdfupload.getUrl();

                if (pdfUrl != null && !pdfUrl.isEmpty()) {
                    // Create an Intent to view the PDF using an external viewer
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // Handle the case where a suitable PDF viewer is not installed
                        // You can display a message to the user or prompt them to install a viewer.
                    }
                }
            }
        });





    }

    private void viewfiles() {

        databaseReference= FirebaseDatabase.getInstance().getReference("upload");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postsnapshot:snapshot.getChildren()){
                    pdfClass pdfClass=postsnapshot.getValue(com.example.cvbjkhg.pdfClass.class);
                    uploads.add(pdfClass);
                }
                String[] Uploads= new String[uploads.size()];
                for (int i=0;i<Uploads.length;i++){
                    Uploads[i]=uploads.get(i).getName();
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,Uploads){

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view=super.getView(position,convertView,parent);
                        TextView text=(TextView) view.findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        text.setTextSize(22);

                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}