package com.ceribit.android.singletons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Properties
    EditText mTitleEditText;
    EditText mDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferencesManager.init(this);

        mTitleEditText = findViewById(R.id.edit_text_title);
        mDescriptionEditText = findViewById(R.id.edit_text_description);

        mTitleEditText.setText(SharedPreferencesManager.getTitle());
        mDescriptionEditText.setText(SharedPreferencesManager.getDescription());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        mTitleEditText.setText(SharedPreferencesManager.getTitle());
        mDescriptionEditText.setText(SharedPreferencesManager.getDescription());
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferencesManager.setTitle(mTitleEditText.getText().toString());
        SharedPreferencesManager.setDescription(mDescriptionEditText.getText().toString());
    }
}
