package com.ceribit.android.fragmentlifecycle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BottomFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_fragment, null);

        Button button = rootView.findViewById(R.id.button_bottom_next);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopFragment fragment = new TopFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_bottom, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return rootView;
    }
}
