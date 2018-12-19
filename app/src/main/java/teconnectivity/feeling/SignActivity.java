package teconnectivity.feeling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        Button button_3 = findViewById(R.id.button_return);                 //设置切换页面功能
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignActivity.this,LogActivity.class);
                startActivity(intent);
            }
        });
        Button button_4 = findViewById(R.id.button_sign_data);                 //设置切换页面功能
        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignActivity.this, "Successful Registered!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
