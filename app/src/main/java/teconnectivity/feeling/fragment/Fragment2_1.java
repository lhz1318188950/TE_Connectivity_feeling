package teconnectivity.feeling.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import teconnectivity.feeling.R;


public class Fragment2_1 extends Fragment {

    private Button female;
    private Button male;

    private ViewFlipper viewflipper_f;
    private ViewFlipper viewflipper_m;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment2_1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewflipper_f = (ViewFlipper) getActivity().findViewById(R.id.viewflipper_f);
        viewflipper_m = (ViewFlipper) getActivity().findViewById(R.id.viewflipper_m);

        viewflipper_f.startFlipping();
        viewflipper_m.startFlipping();

        female = (Button) getActivity().findViewById(R.id.female);
        male = (Button) getActivity().findViewById(R.id.male);

        female.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //viewflipper_f.setVisibility(View.VISIBLE );
                viewflipper_m.setVisibility(View.INVISIBLE);
                //viewflipper_f.bringToFront();
            }
        }));
        male.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //viewflipper_f.setVisibility(View.GONE );
                viewflipper_m.setVisibility(View.VISIBLE);
                //viewflipper_m.bringToFront();
            }
        }));
    }
}