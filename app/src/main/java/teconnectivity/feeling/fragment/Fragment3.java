package teconnectivity.feeling.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import teconnectivity.feeling.R;


public class Fragment3 extends Fragment {

    private EditText temp_1;
    private EditText humidity_1;
    private EditText wind_speed_1;
    private EditText air_pressure_1;
    private EditText air_quality_1;
    private EditText air_pollution_1;

    private Spinner temp_spinner;
    private Spinner humidity_spinner;
    private Spinner wind_speed_spinner;
    private Spinner air_pressure_spinner;
    private Spinner air_quality_spinner;
    private Spinner air_pollution_spinner;

    private Button index;
    private Button reset;
    private Button button_submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment3, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        index = (Button) getActivity().findViewById(R.id.Index);
        reset = (Button) getActivity().findViewById(R.id.reset);
        button_submit = (Button) getActivity().findViewById(R.id.button_submit);

        temp_1 = (EditText) getActivity().findViewById(R.id.temp_1);
        humidity_1 = (EditText) getActivity().findViewById(R.id.humidity_1);
        wind_speed_1 = (EditText) getActivity().findViewById(R.id.wind_speed_1);
        air_pressure_1 = (EditText) getActivity().findViewById(R.id.air_pressure_1);
        air_quality_1 = (EditText) getActivity().findViewById(R.id.air_quality_1);
        air_pollution_1 = (EditText) getActivity().findViewById(R.id.air_pollution_1);

        temp_spinner = (Spinner) getActivity().findViewById(R.id.temp_spinner);
        humidity_spinner = (Spinner) getActivity().findViewById(R.id.humidity_spinner);
        wind_speed_spinner = (Spinner) getActivity().findViewById(R.id.wind_speed_spinner);
        air_pressure_spinner = (Spinner) getActivity().findViewById(R.id.air_pressure_spinner);
        air_quality_spinner = (Spinner) getActivity().findViewById(R.id.air_quality_spinner);
        air_pollution_spinner = (Spinner) getActivity().findViewById(R.id.air_pollution_spinner);

        reset.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp_1.setVisibility(View.INVISIBLE);
                humidity_1.setVisibility(View.INVISIBLE);
                wind_speed_1.setVisibility(View.INVISIBLE);
                air_pressure_1.setVisibility(View.INVISIBLE);
                air_quality_1.setVisibility(View.INVISIBLE);
                air_pollution_1.setVisibility(View.INVISIBLE);

                button_submit.setVisibility(View.VISIBLE);

                temp_spinner.setVisibility(View.VISIBLE);
                humidity_spinner.setVisibility(View.VISIBLE);
                wind_speed_spinner.setVisibility(View.VISIBLE);
                air_pressure_spinner.setVisibility(View.VISIBLE);
                air_quality_spinner.setVisibility(View.VISIBLE);
                air_pollution_spinner.setVisibility(View.VISIBLE);
            }
        }));
        index.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp_1.setVisibility(View.VISIBLE);
                humidity_1.setVisibility(View.VISIBLE);
                wind_speed_1.setVisibility(View.VISIBLE);
                air_pressure_1.setVisibility(View.VISIBLE);
                air_quality_1.setVisibility(View.VISIBLE);
                air_pollution_1.setVisibility(View.VISIBLE);

                button_submit.setVisibility(View.INVISIBLE);

                temp_spinner.setVisibility(View.INVISIBLE);
                humidity_spinner.setVisibility(View.INVISIBLE);
                wind_speed_spinner.setVisibility(View.INVISIBLE);
                air_pressure_spinner.setVisibility(View.INVISIBLE);
                air_quality_spinner.setVisibility(View.INVISIBLE);
                air_pollution_spinner.setVisibility(View.INVISIBLE);
            }
        }));
    }
}
