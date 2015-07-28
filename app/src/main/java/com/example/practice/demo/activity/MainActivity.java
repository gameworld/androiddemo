package com.example.practice.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.content.Intent;

import com.example.practice.demo.R;
import com.example.practice.demo.service.NetService;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
    登陆按钮点击事件
     */
    public void buttonLoginOnClick(View view)
    {
        Log.i("Demo","btn click");
        EditText loginEdit=(EditText)findViewById(R.id.loginNameEdit);
        EditText passwordEdit=(EditText)findViewById(R.id.passwordEdit);

        String name=loginEdit.getText().toString();
        String password=passwordEdit.getText().toString();

        Log.i("Demo",name);

        Log.i("Demo",password);


        if(name.equals("") ||password.equals("")) {
            Log.i("Demo", "error input");
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), NetService.class);
            startService(intent);
        }
    }
}
