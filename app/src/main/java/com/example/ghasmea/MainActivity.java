package com.example.ghasmea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ghasmea.Jalasat.JalasatActivity;
import com.example.ghasmea.login.SignInSMSActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcome();
        Intent i = new Intent(MainActivity.this, JalasatActivity.class);
        startActivity(i);
        finish();


    }



    private void welcome() {
        sp = getSharedPreferences("Logged-in", MODE_PRIVATE);
        String mobile = sp.getString("userPhone", null);
        boolean login = sp.getBoolean("login",false);

        txtWelcome = findViewById(R.id.txt_welcome);
        txtWelcome.setText(mobile +"\n" + "خوش آمدید" + "\n" + login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_logout)
        {
            sp = getSharedPreferences("Logged-in", Context.MODE_PRIVATE);
            editor = sp.edit();
            editor.putBoolean("login", false);
            editor.commit();
            //editor.putString("userPhone","");
            Intent i = new Intent(getApplicationContext(), SignInSMSActivity.class);
            startActivity(i);
            finish();
        }
        else if (itemId == R.id.menu_account)
        {
            Toast.makeText(this, "your usernme is: " + sp.getString("userPhone", null), Toast.LENGTH_SHORT).show();
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}