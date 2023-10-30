package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebasecrud.model.CourseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCourseActivity extends AppCompatActivity {

    private Button addCourseBtn;
    private EditText courseName, coursePrice, courseDesc, courseLink, courseImageLink, courseSuited;
    private ProgressBar progressBar;
    private String courseId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        addCourseBtn = findViewById(R.id.addCourseBtn);
        courseName = findViewById(R.id.courseNameEtxt);
        coursePrice = findViewById(R.id.coursePriceEtxt);
        courseDesc = findViewById(R.id.courseDescEtxt);
        courseLink = findViewById(R.id.courseLinkEtxt);
        courseImageLink = findViewById(R.id.courseImageLinkEtxt);
        courseSuited = findViewById(R.id.courseSuitedEtxt);

        progressBar = findViewById(R.id.loadingPro);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String CourseName = courseName.getText().toString();
                String CoursePrice = coursePrice.getText().toString();
                String CourseDesc = courseDesc.getText().toString();
                String CourseLink = courseLink.getText().toString();
                String CourseImageLink = courseImageLink.getText().toString();
                String CourseSuited = courseSuited.getText().toString();

                courseId = CourseName;

                CourseModel courseModel = new CourseModel(courseId, CourseName, CourseDesc, CoursePrice, CourseSuited, CourseImageLink,CourseLink);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(courseId).setValue(courseModel);
                        Toast.makeText(AddCourseActivity.this, "Course added Successfully...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCourseActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddCourseActivity.this, "Failed to add Course...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}