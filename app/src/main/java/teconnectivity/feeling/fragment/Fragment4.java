package teconnectivity.feeling.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import teconnectivity.feeling.LogActivity;
import teconnectivity.feeling.R;
import teconnectivity.feeling.SignActivity;


public class Fragment4 extends Fragment{

    private Button sign_out;
    private Button exit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment4, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sign_out = (Button) getActivity().findViewById(R.id.sign_out);
        exit = (Button) getActivity().findViewById(R.id.exit);

        sign_out.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LogActivity.class);
                startActivity(intent);
            }
        }));
        exit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        }));
    }
}
