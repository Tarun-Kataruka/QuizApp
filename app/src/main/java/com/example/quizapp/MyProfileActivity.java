package com.example.quizapp;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyProfileActivity extends AppCompatActivity {

    private EditText name, email,phone;
    private LinearLayout editButton;
    private Button cancelB, saveB;
    private TextView profile_text;
    private LinearLayout button_layout;
    private String nameStr, phoneStr;
    private Dialog progressDialog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        name = findViewById(R.id.mp_name);
        email=findViewById(R.id.mp_email);
        phone=findViewById(R.id.mp_phone);
        editButton=findViewById(R.id.editB);
        profile_text=findViewById(R.id.profile_text);
        cancelB=findViewById(R.id.cancelButton);
        saveB=findViewById(R.id.saveButton);
        button_layout=findViewById(R.id.buttonLayout);

        progressDialog = new Dialog(MyProfileActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDialog.findViewById(R.id.dialog_test);
        dialogText.setText("Saving...");


        disableEditing();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditing();
            }
        });
        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableEditing();
            }
        });
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    savedData();
                }
            }
        });
    }

    private void disableEditing()
    {
        name.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);
        button_layout.setVisibility(View.GONE);

        name.setText(DbQuery.myProfile.getName());
        email.setText(DbQuery.myProfile.getEmail());

        if(DbQuery.myProfile.getPhone()!=null)
        {
            phone.setText(DbQuery.myProfile.getPhone());
        }
        String profileName = DbQuery.myProfile.getName();
        profile_text.setText(profileName.toUpperCase().substring(0,1));
    }

    private void enableEditing()
    {
        name.setEnabled(true);
        phone.setEnabled(true);
        button_layout.setVisibility(View.VISIBLE);
    }

    private boolean validate()
    {
        nameStr = name.getText().toString();
        phoneStr= phone.getText().toString();
        if(nameStr. isEmpty())
        {
            name.setError("Name cannot be empty!");
            return false;
        }
        if(!phoneStr.isEmpty())
        {
            if(!((phoneStr.length() == 10) && (TextUtils.isDigitsOnly(phoneStr))))
            {
                phone.setError("Enter valid Phone Number.");
                return false;
            }

        }
        return true;
    }

    private void savedData()
    {
        progressDialog.show();
        if(phoneStr.isEmpty())
            phoneStr=null;

        DbQuery.saveProfileData(nameStr, phoneStr, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MyProfileActivity.this,"Profile Updated Successfully.",Toast.LENGTH_SHORT).show();
                disableEditing();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(MyProfileActivity.this,"Something Went Wrong!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


}