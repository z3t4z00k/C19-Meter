package com.z3t4z00k.c19meter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class dashboard extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "dashboard";
    private static final String URL_GRAPH = "https://api.covid19india.org/data.json";
    //String URL = "";
    ConstraintLayout nav;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = getSharedPreferences(MainActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("India"));
        final TextView count = findViewById(R.id.count);
        final TextView death = findViewById(R.id.deaths);
        final TextView recov = findViewById(R.id.recoveries);
        final TextView cases = findViewById(R.id.cases);
        /*final TextView s1c = findViewById(R.id.state1count);
        final TextView s2c = findViewById(R.id.state2count);
        final TextView s3c = findViewById(R.id.state3count);
        final TextView s1h = findViewById(R.id.state1Heading);
        final TextView s2h = findViewById(R.id.state2Heading);
        final TextView s3h = findViewById(R.id.state3Heading);*/
        final TextView t1 = findViewById(R.id.title1);
        final TextView d1 = findViewById(R.id.date1);
        final TextView t2 = findViewById(R.id.title2);
        final TextView d2 = findViewById(R.id.date2);
        final TextView t3 = findViewById(R.id.title3);
        final TextView d3 = findViewById(R.id.date3);
        final TextView t4 = findViewById(R.id.title4);
        final TextView d4 = findViewById(R.id.date4);
        final TextView t5 = findViewById(R.id.title5);
        final TextView d5 = findViewById(R.id.date5);
        final TextView l1 = findViewById(R.id.more1);
        final TextView l2 = findViewById(R.id.more2);
        final TextView l3 = findViewById(R.id.more3);
        final TextView l4 = findViewById(R.id.more4);
        final TextView l5 = findViewById(R.id.more5);
        final TextView topStates = findViewById(R.id.topStatesViewMore);
        final ImageView navButton = findViewById(R.id.navigationBarButton);
        final ImageView close = findViewById(R.id.close);
        nav = findViewById(R.id.navigationDrawer);
        final TextView faq = findViewById(R.id.faq);
        final TextView state = findViewById(R.id.list);
        final TextView map = findViewById(R.id.mapView);
        final TextView viewMap = findViewById(R.id.map);
        final TextView myths = findViewById(R.id.myths);

        myths.setOnClickListener(this);
        viewMap.setOnClickListener(this);
        map.setOnClickListener(this);
        state.setOnClickListener(this);
        faq.setOnClickListener(this);
        nav.setVisibility(View.GONE);
        navButton.setOnClickListener(this);
        close.setOnClickListener(this);

        /*@SuppressLint("StaticFieldLeak")
        class Login extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("name", "Archit");

                return requestHandler.sendPostRequest(URL, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONObject obj;
                try {
                    obj = new JSONObject(s);
                    if (!obj.getString("S1").equals("")) {
                        s1h.setText(obj.getString("S1"));
                        s2h.setText(obj.getString("S2"));
                        s3h.setText(obj.getString("S3"));
                        s1c.setText(obj.getString("C1"));
                        s2c.setText(obj.getString("C2"));
                        s3c.setText(obj.getString("C3"));
                        Log.d(TAG, "onPostExecute: Setting top states");
                        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("S1", obj.getString("S1"));
                        editor.putString("S2", obj.getString("S2"));
                        editor.putString("S3", obj.getString("S3"));
                        editor.putString("C1", obj.getString("C1"));
                        editor.putString("C2", obj.getString("C2"));
                        editor.putString("C3", obj.getString("C3"));
                        editor.putString("dd3", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                        editor.putString("mm3", String.valueOf(calendar.get(Calendar.MONTH)));
                        editor.apply();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(dashboard.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                }
            }
        }
        if(!String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).equals(sharedPreferences.getString("dd3", "00")) && !String.valueOf(calendar.get(Calendar.MONTH)).equals(sharedPreferences.getString("mm3", "00"))) {
            Log.d(TAG, "Current- " + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH));
            Log.d(TAG, "Stored- " + sharedPreferences.getString("dd3", "00") + "/" + sharedPreferences.getString("mm3", "00"));
            Login login = new Login();
            login.execute();
        }
        else{
            Log.d(TAG, "onCreate: Fetching top states from SP");
            s1h.setText(sharedPreferences.getString("S1", ""));
            s2h.setText(sharedPreferences.getString("S2", ""));
            s3h.setText(sharedPreferences.getString("S3", ""));
            s1c.setText(sharedPreferences.getString("C1", ""));
            s2c.setText(sharedPreferences.getString("C2", ""));
            s3c.setText(sharedPreferences.getString("C3", ""));
        }*/

        count.setText(sharedPreferences.getString("confi", "0"));
        cases.setText(sharedPreferences.getString("cases", "0"));
        recov.setText(sharedPreferences.getString("recov", "0"));
        death.setText(sharedPreferences.getString("death", "0"));
        t1.setText(sharedPreferences.getString("t1", ""));
        t2.setText(sharedPreferences.getString("t2", ""));
        t3.setText(sharedPreferences.getString("t3", ""));
        t4.setText(sharedPreferences.getString("t4", ""));
        t5.setText(sharedPreferences.getString("t5", ""));
        d1.setText(sharedPreferences.getString("d1", ""));
        d2.setText(sharedPreferences.getString("d2", ""));
        d3.setText(sharedPreferences.getString("d3", ""));
        d4.setText(sharedPreferences.getString("d4", ""));
        d5.setText(sharedPreferences.getString("d5", ""));

        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);
        topStates.setOnClickListener(this);

        final LineChart casesChart = findViewById(R.id.casesChart);
        final List<Entry> casesList = new ArrayList<>();

        final LineChart recoveriesChart = findViewById(R.id.recoveriesChart);
        final List<Entry> recoveries = new ArrayList<>();

        final LineChart deathsChart = findViewById(R.id.deathsChart);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final List<Entry> finalDeaths =new ArrayList<>();
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GRAPH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.substring(0, response.indexOf("statewise")-4);
                        response += "}";
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(jsonObject!=null){
                            JSONArray days = null;
                            try {
                                days = jsonObject.getJSONArray("cases_time_series");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(days!=null){
                                int init = 0;
                                for(int i = days.length()-14; i < days.length(); i++) {
                                    if(init == 0)
                                        init = i;
                                    JSONObject day = null;
                                    try {
                                        day = (JSONObject) days.get(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (day != null) {
                                        try {
                                            finalDeaths.add(new Entry((i-init)+1, day.getInt("dailydeceased")));
                                            recoveries.add(new Entry((i-init)+1, day.getInt("dailyrecovered")));
                                            casesList.add(new Entry((i-init)+1, day.getInt("dailyconfirmed")));
                                            Log.d(TAG, "onResponse: Updated Size: " + finalDeaths.size());

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                int color = ContextCompat.getColor(getBaseContext(), R.color.colorPrimary);
                                LineDataSet deathDataSet = new LineDataSet(finalDeaths, "");
                                deathDataSet.setValueTextColor(R.color.colorDarkGray);
                                deathDataSet.setDrawValues(false);
                                deathDataSet.setDrawCircles(false);
                                deathDataSet.setDrawFilled(false);
                                deathDataSet.setLineWidth(3);
                                deathDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                                deathDataSet.setColor(color);
                                deathDataSet.setDrawFilled(true);
                                deathDataSet.setFillDrawable(getDrawable(R.drawable.grad_white));
                                deathDataSet.setDrawIcons(false);

                                LineData deathData = new LineData(deathDataSet);
                                deathsChart.setData(deathData);
                                deathsChart.getLegend().setEnabled(false);
                                deathsChart.setDescription(null);
                                deathsChart.getAxisLeft().setDrawLabels(false);
                                deathsChart.getAxisRight().setDrawLabels(false);
                                deathsChart.getAxisLeft().setDrawGridLines(false);
                                deathsChart.getXAxis().setDrawGridLines(false);
                                deathsChart.getAxisLeft().setEnabled(false);
                                deathsChart.getXAxis().setEnabled(false);
                                deathsChart.getAxisRight().setEnabled(false);
                                deathsChart.getAxisRight().setGridColor(R.color.colorPrimary);
                                deathsChart.invalidate();

                                LineDataSet recoveryDataSet = new LineDataSet(recoveries, "");
                                recoveryDataSet.setValueTextColor(R.color.colorDarkGray);
                                recoveryDataSet.setDrawValues(false);
                                recoveryDataSet.setDrawCircles(false);
                                recoveryDataSet.setDrawFilled(false);
                                recoveryDataSet.setLineWidth(3);
                                recoveryDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                                recoveryDataSet.setColor(color);
                                recoveryDataSet.setDrawFilled(true);
                                recoveryDataSet.setFillDrawable(getDrawable(R.drawable.grad_white));
                                recoveryDataSet.setDrawIcons(false);

                                LineData recoveryData = new LineData(recoveryDataSet);
                                recoveriesChart.setData(recoveryData);
                                recoveriesChart.getLegend().setEnabled(false);
                                recoveriesChart.setDescription(null);
                                recoveriesChart.getAxisLeft().setDrawLabels(false);
                                recoveriesChart.getAxisRight().setDrawLabels(false);
                                recoveriesChart.getAxisLeft().setDrawGridLines(false);
                                recoveriesChart.getXAxis().setDrawGridLines(false);
                                recoveriesChart.getAxisLeft().setEnabled(false);
                                recoveriesChart.getXAxis().setEnabled(false);
                                recoveriesChart.getAxisRight().setEnabled(false);
                                recoveriesChart.getAxisRight().setGridColor(R.color.colorPrimary);
                                recoveriesChart.invalidate();

                                LineDataSet caseDataSet = new LineDataSet(casesList, "");
                                caseDataSet.setValueTextColor(R.color.colorDarkGray);
                                caseDataSet.setDrawValues(false);
                                caseDataSet.setDrawCircles(false);
                                caseDataSet.setDrawFilled(false);
                                caseDataSet.setLineWidth(3);
                                caseDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                                caseDataSet.setColor(color);
                                caseDataSet.setDrawFilled(true);
                                caseDataSet.setFillDrawable(getDrawable(R.drawable.grad_white));
                                caseDataSet.setDrawIcons(false);

                                LineData caseData = new LineData(caseDataSet);
                                casesChart.setData(caseData);
                                casesChart.getLegend().setEnabled(false);
                                casesChart.setDescription(null);
                                casesChart.getAxisLeft().setDrawLabels(false);
                                casesChart.getAxisRight().setDrawLabels(false);
                                casesChart.getAxisLeft().setDrawGridLines(false);
                                casesChart.getXAxis().setDrawGridLines(false);
                                casesChart.getAxisLeft().setEnabled(false);
                                casesChart.getXAxis().setEnabled(false);
                                casesChart.getAxisRight().setEnabled(false);
                                casesChart.getAxisRight().setGridColor(R.color.colorPrimary);
                                casesChart.invalidate();

                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed(){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.myths:
                startActivity(new Intent(dashboard.this, MythBusters.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.map:
            case R.id.mapView:
                startActivity(new Intent(dashboard.this, MapsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.list:
            case R.id.topStatesViewMore:
                startActivity(new Intent(dashboard.this, statewise.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.faq:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mohfw.gov.in/pdf/FAQ.pdf")));
                break;
            case R.id.navigationBarButton:
                nav.setVisibility(View.VISIBLE);
                break;
            case R.id.close:
                nav.setVisibility(View.GONE);
                break;
            case R.id.more1:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("l1", "error"))));
                break;
            case R.id.more2:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("l2", "error"))));
                break;
            case R.id.more3:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("l3", "error"))));
                break;
            case R.id.more4:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("l4", "error"))));
                break;
            case R.id.more5:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("l5", "error"))));
                break;
        }
    }
}
