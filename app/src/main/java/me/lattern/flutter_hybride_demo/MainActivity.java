package me.lattern.flutter_hybride_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.lattern.flutter_utils.CYFlutterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn_show_flutter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String route = "main";

                Intent intent = new Intent(MainActivity.this, CYFlutterActivity.class);
                intent.setAction("android.intent.action.RUN");
                intent.putExtra("route", route);
                startActivity(intent);
            }
        });
    }
}
