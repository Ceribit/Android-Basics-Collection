package com.ceribit.android.fragmentlifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FrameLayout frameLayout = findViewById(R.id.frame_main);

        Button button = findViewById(R.id.button_main_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomFragment fragment = new BottomFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_main, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
