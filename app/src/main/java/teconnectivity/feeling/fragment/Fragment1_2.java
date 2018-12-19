package teconnectivity.feeling.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import teconnectivity.feeling.MainActivity;
import teconnectivity.feeling.R;


public class Fragment1_2 extends Fragment {
    private  Button button_detail_2 ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment1_1, container, false);

        super.onCreateView(inflater,container,savedInstanceState);
        //startActivity(new Intent(getActivity(), DetailActivity.class));
        button_detail_2 = (Button)v.findViewById(R.id.button_detail_2);
        button_detail_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
