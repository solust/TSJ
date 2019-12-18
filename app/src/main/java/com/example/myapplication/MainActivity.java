package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    String SS;
    String login = null;
    String pass = null;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);//ПРОВЕРИТЬ!
        setContentView(R.layout.activity_main2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView textView = findViewById(R.id.textView4);


    }

    public void onClickLog(View view) {

        setContentView(R.layout.activity_main2);
        if (login == null) {
            final EditText log = findViewById(R.id.Log);
            login = log.getText().toString();
        }
        String pasS = null;
        if (pass == null) {
            EditText p = findViewById(R.id.Pas);
            final String S = p.getText().toString();
            pass = S;
            pasS = S;
        } else {
            final String S = pass;
            pasS = S;
        }

        final String finalPasS = pasS;
        class login extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                final String strPass = finalPasS;
                String password = null;
                try {
                    MessageDigest digest = null;
                    digest = MessageDigest.getInstance("MD5");
                    digest.update(strPass.getBytes(Charset.forName("US-ASCII")), 0, strPass.length());
                    byte messageDigest[] = digest.digest();
                    BigInteger bi = new BigInteger(1, messageDigest);
                    password = String.format("%0" + (messageDigest.length << 1) + "x", bi);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost http = new HttpPost("http://solust.000webhostapp.com/login.php");
                List nameValuePairs = new ArrayList(2);
                nameValuePairs.add(new BasicNameValuePair("login", login));
                //nameValuePairs.add(new BasicNameValuePair("pass", pass.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("pass", password));
                try {
                    http.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//получаем ответ от сервера
                try {
                    String response = (String) httpclient.execute(http, new BasicResponseHandler());
                    SS = response;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @SuppressLint("SetJavaScriptEnabled")
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(!SS.equals("Неправильный пароль и/или логин")) {
                    setContentView(R.layout.login);
                    webView = findViewById(R.id.web);
                    // включаем поддержку JavaScript
                    webView.getSettings().setJavaScriptEnabled(true);
                    // указываем страницу загрузки
                    webView.loadUrl("http://solust.000webhostapp.com/golos.php");
                    webView.getSettings().setUseWideViewPort(true);
                }else{
                    setContentView(R.layout.activity_main);
                    TextView text = findViewById(R.id.textView4);
                    text.setText(SS);
                    pass = null;
                    login = null;
                }
            }
        }
        login l = new login();
        l.execute();

        //Вызов окна загрузки
        setContentView(R.layout.loading);







    }

    public void onClickReg(View view) {
        setContentView(R.layout.reg);
    }

    public void regUser(View view) {
        final EditText log = findViewById(R.id.login);
        login = log.getText().toString();
        final EditText password = findViewById(R.id.pass);
        final EditText firstName = findViewById(R.id.firstName);
        final EditText lastName = findViewById(R.id.lastName);
        final EditText middleName = findViewById(R.id.middleName);
        final EditText phone = findViewById(R.id.phone);
        final EditText email = findViewById(R.id.email);
        final EditText apartament = findViewById(R.id.apartment);

        //Вызов окна загрузки
        setContentView(R.layout.loading);
        class reg extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                String S = password.getText().toString();
                try {
                    MessageDigest digest = null;
                    digest = MessageDigest.getInstance("MD5");
                    digest.update(S.getBytes(Charset.forName("US-ASCII")), 0, S.length());
                    byte messageDigest[] = digest.digest();
                    BigInteger bi = new BigInteger(1, messageDigest);
                    pass = String.format("%0" + (messageDigest.length << 1) + "x", bi);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost http = new HttpPost("http://solust.000webhostapp.com/reg.php");
                List nameValuePairs = new ArrayList(8);
                nameValuePairs.add(new BasicNameValuePair("login", login));
                nameValuePairs.add(new BasicNameValuePair("pass", pass));
                nameValuePairs.add(new BasicNameValuePair("firstName", firstName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("lastName", lastName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("middleName", middleName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("phone", phone.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("apartament", apartament.getText().toString()));
                try {
                    http.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//получаем ответ от сервера
                try {
                    String response = (String) httpclient.execute(http, new BasicResponseHandler());
                    SS = response;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setContentView(R.layout.activity_main);
                TextView textView = findViewById(R.id.textView4);
                textView.setText(SS);
                login = null;
                pass = null;
            }
        }
        reg user = new reg();
        user.execute();
    }

    public void onClickBack(View view) {
        pass = null;
        login = null;
        setContentView(R.layout.activity_main);
    }

    public void appData(View view) {

        final EditText firstName = findViewById(R.id.editText8);
        final EditText lastName = findViewById(R.id.editText6);
        final EditText middleName = findViewById(R.id.editText4);
        final EditText phone = findViewById(R.id.editText);
        final EditText email = findViewById(R.id.editText3);
        final EditText apartament = findViewById(R.id.editText2);

        class addData extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost http = new HttpPost("http://solust.000webhostapp.com/addData.php");
                List nameValuePairs = new ArrayList(7);
                nameValuePairs.add(new BasicNameValuePair("login", login));
                nameValuePairs.add(new BasicNameValuePair("firstName", firstName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("lastName", lastName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("middleName", middleName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("phone", phone.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("apartament", apartament.getText().toString()));
                try {
                    http.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//получаем ответ от сервера
                try {
                    String response = (String) httpclient.execute(http, new BasicResponseHandler());
                    SS = response;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                View butt = findViewById(R.id.button5);
                onClickLog(butt);
            }
        }
        addData user = new addData();
        user.execute();
    }

    public void onClickProfile(View view) {
        if (login == null) {
            final EditText log = findViewById(R.id.Log);
            login = log.getText().toString();
        }
        String pasS = null;
        if (pass == null) {
            EditText p = findViewById(R.id.Pas);
            final String S = p.getText().toString();
            pass = S;
            pasS = S;
        } else {
            final String S = pass;
            pasS = S;
        }

        final String finalPasS = pasS;
        class login extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                final String strPass = finalPasS;
                String password = null;
                try {
                    MessageDigest digest = null;
                    digest = MessageDigest.getInstance("MD5");
                    digest.update(strPass.getBytes(Charset.forName("US-ASCII")), 0, strPass.length());
                    byte messageDigest[] = digest.digest();
                    BigInteger bi = new BigInteger(1, messageDigest);
                    password = String.format("%0" + (messageDigest.length << 1) + "x", bi);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost http = new HttpPost("http://solust.000webhostapp.com/login.php");
                List nameValuePairs = new ArrayList(2);
                nameValuePairs.add(new BasicNameValuePair("login", login));
                //nameValuePairs.add(new BasicNameValuePair("pass", pass.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("pass", password));
                try {
                    http.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//получаем ответ от сервера
                try {
                    String response = (String) httpclient.execute(http, new BasicResponseHandler());
                    SS = response;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(!SS.equals("Неправильный пароль и/или логин")) {
                    setContentView(R.layout.users);
                    parser A = new parser();
                    parser.User user = A.getUser(SS);
                    TextView textView = findViewById(R.id.dataLogin);
                    textView.setText(user.login);
                    if (user.lastName.equals("")) {
                        EditText editText = findViewById(R.id.editText6);
                        editText.setVisibility(View.VISIBLE);
                    } else {
                        textView = findViewById(R.id.textViewfio1);
                        textView.setText(user.lastName);
                    }
                    if (user.firstName.equals("")) {
                        EditText editText = findViewById(R.id.editText8);
                        editText.setVisibility(View.VISIBLE);
                    } else {
                        textView = findViewById(R.id.textViewfio2);
                        textView.setText(user.firstName);
                    }
                    if (user.middleName.equals("")) {
                        EditText editText = findViewById(R.id.editText4);
                        editText.setVisibility(View.VISIBLE);
                    } else {
                        textView = findViewById(R.id.textViewfio3);
                        textView.setText(user.middleName);
                    }
                    if (user.email.equals("")) {
                        EditText editText = findViewById(R.id.editText3);
                        editText.setVisibility(View.VISIBLE);
                    } else {
                        textView = findViewById(R.id.textViewEmail);
                        textView.setText(user.email);
                    }
                    if (user.phone.equals("")) {
                        EditText editText = findViewById(R.id.editText);
                        editText.setVisibility(View.VISIBLE);
                    } else {
                        textView = findViewById(R.id.textViewphone);
                        textView.setText(user.phone);
                    }
                    if (user.apartament.equals("")) {
                        EditText editText = findViewById(R.id.editText2);
                        editText.setVisibility(View.VISIBLE);
                    } else {
                        textView = findViewById(R.id.textViewapartament);
                        textView.setText(user.apartament);
                    }
                }else{
                    setContentView(R.layout.activity_main);
                    TextView text = findViewById(R.id.textView4);
                    text.setText(SS);
                    pass = null;
                    login = null;
                }
            }
        }
        login l = new login();
        l.execute();

        //Вызов окна загрузки
        setContentView(R.layout.loading);
    }

    public void onClickExit(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Выйти из приложения?")
                .setMessage("Вы действительно хотите выйти?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    public void onClickCall(View view) {
        //Call to special phone number
    }
}

