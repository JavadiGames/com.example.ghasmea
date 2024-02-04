package com.example.ghasmea.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ghasmea.MainActivity;
import com.example.ghasmea.R;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Hashtable;
import java.util.Map;

public class SignInSMSActivity extends AppCompatActivity {

    EditText edt_phone, edt_code;
    LinearLayout layout_sendphone, layout_sendcode, layout_success;
    TextView send_phone, send_code, txt_again;
    IntentFilter intentFilter;
    AppSMSBroadcastReceiver appSMSBroadcastReceiver;
    Context context = SignInSMSActivity.this;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private static final int DELAY = 5000; //5 seconds
    protected boolean login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_sms);


        checkCurrentUser(); // Checks if the user has logged in before
        initView(); //
        smsListener();
        initBroadcast();
    }

    private void checkCurrentUser()
    {
        sp = getSharedPreferences("Logged-in",Context.MODE_PRIVATE);
        login = sp.getBoolean("login",false);

        if (login)
        {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
            finish();
        }

    }


    private void initView()
    {
        layout_sendphone = findViewById(R.id.layout_sendphone);
        layout_sendcode = findViewById(R.id.layout_sendcode);
        layout_success = findViewById(R.id.layout_success);
        send_phone = findViewById(R.id.txt_sendphone);
        send_code = findViewById(R.id.txt_send_code);
        txt_again = findViewById(R.id.txt_again);
        edt_phone = findViewById(R.id.edt_phone);
        edt_code = findViewById(R.id.edt_code);

        send_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(edt_phone.getText().toString().trim().length() == 11 && edt_phone.getText().toString().startsWith("09"))
                    sendcodewithsms(edt_phone.getText().toString());
                else
                    Toast.makeText(context, "شماره تلفن وارد شده درست نیست!" , Toast.LENGTH_LONG ).show();

            }
        });


        // In case the user clicks the confirm (تایید شماره همراه) TextView
        // Otherwise the code will automatically completes the edt_txt with the sent code (see line 230)
        send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checkAuth(edt_code.getText().toString(), edt_phone.getText().toString());
            }
        });

        txt_again.setOnClickListener(view ->
        {
            layout_sendphone.setVisibility(View.VISIBLE);
            send_code.setVisibility(View.GONE);
            layout_success.setVisibility(View.GONE);
        });

    }



    private void sendcodewithsms(String mobile)
    {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("لطفاً کمی صبر کنید");
        dialog.setTitle("در حال ارسال کد");
        dialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, parameters.LINKSMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        dialog.dismiss();
                        if (Integer.valueOf(result) > 2000){
                            Toast.makeText(context, "پیامک برای شما ارسال شد", Toast.LENGTH_LONG).show();
                            layout_sendphone.setVisibility(View.GONE);
                            layout_sendcode.setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.txt_show_phone)).setText(mobile);
                        }else {
                            Toast.makeText(context, "خطا در ارسال پیامک", Toast.LENGTH_LONG).show();
                            layout_sendphone.setVisibility(View.GONE);
                            layout_sendcode.setVisibility(View.VISIBLE);

                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(context, "Error!!", Toast.LENGTH_LONG).show();
                        layout_sendphone.setVisibility(View.GONE);
                        layout_sendcode.setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.txt_show_phone)).setText(mobile);


                    }
                })
        {
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new Hashtable<>();
                params.put("UserName", parameters.USERNAME);
                params.put("Password", parameters.PASSWORD);
                params.put("Mobile", mobile);
                params.put("Footer", "رشد و تعالی فردی");

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }



    private void smsListener()
    {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsRetriever();
    }

    private void initBroadcast()
    {
        intentFilter = new IntentFilter("com.google.android.gms.auth.api.phone.SMS_RETRIEVED");
        appSMSBroadcastReceiver = new AppSMSBroadcastReceiver();

        appSMSBroadcastReceiver.setOnSmsReceiverListener(new AppSMSBroadcastReceiver.OnSmsReceiverListener(){

            @Override
            public void onReceive(String code)
            {
                String number = code.replaceAll("[^0-9]", "");
                number = number.substring(0, 6);
                edt_code.setText(number.trim());
                checkAuth(edt_code.getText().toString(), edt_phone.getText().toString());
            }
        });

    }

    private void checkAuth(String code, String mobile)
    {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("لطفاً کمی صبر کنید");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, parameters.CHECKSMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        dialog.dismiss();
                        if (Boolean.parseBoolean(result)){
                            Toast.makeText(context, "احراز هویت با موفقیت انجام شد خوش آمدید", Toast.LENGTH_LONG).show();
                            layout_sendcode.setVisibility(View.GONE);
                            layout_success.setVisibility(View.VISIBLE);
                            sp = getSharedPreferences("Logged-in", Context.MODE_PRIVATE );
                            editor = sp.edit();
                            editor.putString("userPhone", mobile);
                            editor.putBoolean("login",true);
                            editor.commit();


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(context, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            },1000);


                        }else{
                            Toast.makeText(context, "کد وارد شده صحیح نمی باشد", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(context, "Error!!", Toast.LENGTH_LONG).show();
                    }
                })
        {
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new Hashtable<>();
                params.put("UserName", parameters.USERNAME);
                params.put("Password", parameters.PASSWORD);
                params.put("Mobile", mobile);
                params.put("Code", code);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }




    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(appSMSBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(appSMSBroadcastReceiver);
    }
}
