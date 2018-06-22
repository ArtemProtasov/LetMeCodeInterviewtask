package ru.protasov_dev.letmecodeinterviewtask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class CriticPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critic_page);

        Intent intent = getIntent();
        String URL = intent.getStringExtra("URL");
        String name = intent.getStringExtra("NAME");

        //Устанавливаем наш кастомный тулбар
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        setTitle(name);

    }
}
