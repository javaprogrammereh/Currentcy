package com.example.currencyconvertnew;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    String url = "https://www.megaweb.ir/api/money";
    Button btn_return, btn_calculate;
    EditText et_value;
    Spinner spin;
    ArrayList<StoreList> arraySpin;
    public int spinnerId;
    String namespinner;
    public static int priceUsd;
    public static int priceEuro;
    public static String titleUsd, jdateUsd, gdateUsd;
    public static String titleEuro, jdateEuro, gdateEuro;
    splitDigits digits = new splitDigits();
    SizeLengthText sizeLengthText = new SizeLengthText();
    SplitTextValueToCalc splitTextValueToCalc = new SplitTextValueToCalc();
    CalculatePrice calculatePrice = new CalculatePrice();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        btn_calculate = findViewById(R.id.btn_convert);
        btn_return = findViewById(R.id.btn_return);
        et_value = findViewById(R.id.edt_price);
        spin = findViewById(R.id.spin_first);

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et_value.getText().toString();
                if (text.matches("\\d+") ||
                        text.matches("^\\d{3}(,\\d{3})?$") ||
                        text.matches("^\\d{3}(,\\d{2})?$") ||
                        text.matches("^\\d{3}(,\\d{1})?$") ||
                        text.matches("^\\d{3}(,\\d{3})(,\\d{3})?$") ||
                        text.matches("^\\d{3}(,\\d{3})(,\\d{2})?$") ||
                        text.matches("^\\d{3}(,\\d{3})(,\\d{1})?$") ||
                        text.matches("^\\d{3}(,\\d{3})(,\\d{3})(,\\d{2})?$") ||
                        text.matches("^\\d{3}(,\\d{3})(,\\d{3})(,\\d{1})?$") ||
                        text.matches("^\\d{3}(,\\d{3})(,\\d{3})(,\\d{3})?$") ||
                        text.matches("^\\d{2}(,\\d{3})(,\\d{3})?$") ||
                        text.matches("^\\d{2}(,\\d{3})?$") ||
                        text.matches("^\\d{2}(,\\d{3})(,\\d{3})(,\\d{3})?$") ||
                        text.matches("^\\d{1}(,\\d{3})?$") ||
                        text.matches("^\\d{1}(,\\d{3})(,\\d{3})?$") ||
                        text.matches("^\\d{1}(,\\d{3})(,\\d{3})(,\\d{3})?$")) {


                    if (et_value.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this, "لطفا مقداری برای تبدیل وارد نمایید", Toast.LENGTH_SHORT).show();
                    } else if (et_value.getText().toString().equals("ریال") || equals("یورو") || equals("دلار")) {
                        Toast.makeText(MainActivity.this, "لطفا رقم دلخواه را وارد نمایید", Toast.LENGTH_SHORT).show();
                    } else if (spinnerId == 0) {
                        Toast.makeText(MainActivity.this, "لطفا ابتدا واحد های تبدیل مورد نظر را انتخاب نمایید", Toast.LENGTH_SHORT).show();
                    }
                     else if(priceEuro==0 && priceUsd==0){
                        Toast.makeText(MainActivity.this, "لطفا اینترنت خود رابررسی نمایید", Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(MainActivity.this, " انتخاب درست", Toast.LENGTH_SHORT).show();
                        if (namespinner.equals("انتخاب کنید")) {
                            et_value.setText("");
                        } else if (namespinner.equals("ریال به دلار")) {
                            Long getSplitVal = splitTextValueToCalc.splitToCalc(et_value);
                            String val = String.valueOf(calculatePrice.calcRialToUsd(getSplitVal, priceUsd));
                            String b = digits.splitDigitsText(Long.parseLong(val));
                            btn_return.setText(b);
                        } else if (namespinner.equals("ریال به یورو")) {
                            Long getSplitVal = splitTextValueToCalc.splitToCalc(et_value);
                            String val = String.valueOf(calculatePrice.calcRialToEuro(getSplitVal, priceEuro));
                            String b = digits.splitDigitsText(Long.parseLong(val));
                            btn_return.setText(b);
                        } else if (namespinner.equals("دلار به ریال")) {
                            Long getSplitVal = splitTextValueToCalc.splitToCalc(et_value);
                            String val = String.valueOf(calculatePrice.calcUsdToRial(getSplitVal, priceUsd));
                            String b = digits.splitDigitsText(Long.parseLong(val));
                            btn_return.setText(b);
                        } else if (namespinner.equals("دلار به یورو")) {
                            Long getSplitVal = splitTextValueToCalc.splitToCalc(et_value);
                            String val = String.valueOf(calculatePrice.calcUsdToEuro(getSplitVal, priceUsd, priceEuro));
                            String b = digits.splitDigitsText(Long.parseLong(val));
                            btn_return.setText(b);
                        } else if (namespinner.equals("یورو به ریال")) {
                            Long getSplitVal = splitTextValueToCalc.splitToCalc(et_value);
                            String val = String.valueOf(calculatePrice.calcEuroToRial(getSplitVal, priceEuro));
                            String b = digits.splitDigitsText(Long.parseLong(val));
                            btn_return.setText(b);
                        } else if (namespinner.equals("یورو به دلار")) {
                            Long getSplitVal = splitTextValueToCalc.splitToCalc(et_value);
                            String val = String.valueOf(calculatePrice.calcEuroToUsd(getSplitVal, priceUsd, priceEuro));
                            String b = digits.splitDigitsText(Long.parseLong(val));
                            btn_return.setText(b);
                        }
                    }

                } else if (text.matches("\\D+")) {
                    Toast.makeText(MainActivity.this, "لطفا رقم وارد کنید!", Toast.LENGTH_SHORT).show();
                    et_value.setText("");
                    et_value.setTextSize(30);
                    btn_return.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "ارقام غیر مجاز!", Toast.LENGTH_SHORT).show();
                    et_value.setText("");
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_value.getWindowToken(), 0);
            }
        });
        arraySpin = new ArrayList<>();
        arraySpin.add(new StoreList("0", "انتخاب کنید"));
        arraySpin.add(new StoreList("1", "ریال به دلار"));
        arraySpin.add(new StoreList("2", "ریال به یورو"));
        arraySpin.add(new StoreList("3", "دلار به ریال"));
        arraySpin.add(new StoreList("4", "دلار به یورو"));
        arraySpin.add(new StoreList("5", "یورو به ریال"));
        arraySpin.add(new StoreList("6", "یورو به دلار"));
        ArrayAdapter<StoreList> arrayAdapter = new ArrayAdapter<StoreList>(this, R.layout.sppiner, arraySpin);
        spin.setAdapter(arrayAdapter);
        spin.setDropDownVerticalOffset(100);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spin);
            popupWindow.setHeight(350);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        }
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView2, View selectedItemView2, int position2, long id2) {
                et_value.setTextSize(30);
                StoreList list2 = (StoreList) parentView2.getSelectedItem();
                spinnerId = Integer.parseInt(list2.getId());
                namespinner = list2.getName();
                if (namespinner.equals("انتخاب کنید")) {
                    Toast.makeText(MainActivity.this, "لطفا ابتدا واحد های تبدیل مورد نظر را انتخاب نمایید", Toast.LENGTH_SHORT).show();
                    et_value.setText("");
                    btn_return.setText("");
                } else if (namespinner.equals("ریال به دلار")) {
                    et_value.setText("ریال");
                    btn_return.setText("دلار");
                } else if (namespinner.equals("ریال به یورو")) {
                    et_value.setText("ریال");
                    btn_return.setText("یورو");
                } else if (namespinner.equals("دلار به ریال")) {
                    et_value.setText("دلار");
                    btn_return.setText("ریال");
                } else if (namespinner.equals("دلار به یورو")) {
                    et_value.setText("دلار");
                    btn_return.setText("یورو");
                } else if (namespinner.equals("یورو به ریال")) {
                    et_value.setText("یورو");
                    btn_return.setText("ریال");
                } else if (namespinner.equals("یورو به دلار")) {
                    et_value.setText("یورو");
                    btn_return.setText("دلار");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        et_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et_value.getText().toString();
                if (text.matches("\\d+")) {
                    Toast.makeText(MainActivity.this, "لطفا ارقام را کامل کنید!", Toast.LENGTH_SHORT).show();
                } else if (text.matches("^\\d{3}(,\\d{3})?$") || text.matches("^\\d{3}(,\\d{2})?$")|| text.matches("^\\d{3}(,\\d{1})?$") ||
                        text.matches("^\\d{3}(,\\d{3})(,\\d{3})?$") || text.matches("^\\d{3}(,\\d{3})(,\\d{2})?$") ||
                        text.matches("^\\d{3}(,\\d{3})(,\\d{1})?$") || text.matches("^\\d{3}(,\\d{3})(,\\d{3})(,\\d{2})?$") || text.matches("^\\d{3}(,\\d{3})(,\\d{3})(,\\d{1})?$")) {
                    Toast.makeText(MainActivity.this, "ادامه ارقام را کامل کنید!", Toast.LENGTH_SHORT).show();
                } else if (text.matches("^\\d{3}(,\\d{3})(,\\d{3})(,\\d{3})?$")) {
                    Toast.makeText(MainActivity.this, "ارقام کامل شد!", Toast.LENGTH_SHORT).show();
                } else if (text.matches("\\D+")) {
                    Toast.makeText(MainActivity.this, "لطفا رقم وارد کنید!", Toast.LENGTH_SHORT).show();
                    et_value.setText("");
                    et_value.setTextSize(30);
                } else if (text.matches("^\\d{2}(,\\d{3})(,\\d{3})?$") || text.matches("^\\d{2}(,\\d{3})?$") || text.matches("^\\d{2}(,\\d{3})(,\\d{3})(,\\d{3})?$")) {
                    Toast.makeText(MainActivity.this, "ادامه ارقام را کامل کنید!", Toast.LENGTH_SHORT).show();
                } else if (text.matches("^\\d{1}(,\\d{3})?$") || text.matches("^\\d{1}(,\\d{3})(,\\d{3})?$") || text.matches("^\\d{1}(,\\d{3})(,\\d{3})(,\\d{3})?$")) {
                    Toast.makeText(MainActivity.this, "ادامه ارقام را کامل کنید!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "ارقام غیر مجاز!", Toast.LENGTH_SHORT).show();
                    et_value.setText("");
                }

            }
        });
        et_value.addTextChangedListener(new TextWatcher() {
            boolean isEdiging;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text=et_value.getText().toString();
                if (text.matches("\\d+\\W+")) {
                    Toast.makeText(MainActivity.this, "غیرمجاز!", Toast.LENGTH_SHORT).show();
                    et_value.setText("");
                    et_value.setTextSize(30);
                }
                if (text.matches("^(\\d{1,3})(,(\\d{3}))(\\W+)$")||text.matches("^(\\d{1,3})(,(\\d{3}))(,(\\d{3}))(\\W+)$")||
                        text.matches("^(\\d{1,3})(,(\\d{3}))(,(\\d{3}))(,(\\d{3}))(\\W+)$")) {
                    Toast.makeText(MainActivity.this, "رقم غیر مجاز!", Toast.LENGTH_SHORT).show();
                    et_value.setText("");
                    et_value.setTextSize(30);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String q = s.toString();
                if (q.matches("[^\\d]+")) {
                    Toast.makeText(MainActivity.this, "لطفا رقم وارد کنید!", Toast.LENGTH_SHORT).show();
                } else {
                    if (isEdiging) return;
                    isEdiging = true;
                    String str = s.toString().replaceAll("[^\\d]", "");
                    double s1 = 0;
                    try {
                        s1 = Double.parseDouble(str);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
                    ((DecimalFormat) nf2).applyPattern("###,###.###");
                    s.replace(0, s.length(), nf2.format(s1));

                    if (s.toString().equals("0")) {
                        et_value.setText("");
                    }
                    isEdiging = false;
                }
                sizeLengthText.lenghtSisze(et_value, MainActivity.this);


            }
        });
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            parsData(jsonObject);
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(MainActivity.this, "لطفا اینترنت خود رابررسی نمایید", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(MainActivity.this, "خطایی در سمت سرور اتفاق افتاد", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(MainActivity.this, "خطای شبکه", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(MainActivity.this, "خطا در دریافت اطلاعات", Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        if (Build.VERSION.SDK_INT < 28) {
            HttpStack stack = null;
            try {
                stack = new HurlStack(null, new TLSSocketFactory());

            } catch (KeyManagementException e) {
                e.printStackTrace();
                Log.d("Your Wrapper Class", "Could not create new stack for TLS v1.2");
                stack = new HurlStack();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                Log.d("Your Wrapper Class", "Could not create new stack for TLS v1.2");
                stack = new HurlStack();
            }
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext(), stack);
            queue.add(stringRequest);
        }
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleTone.getmInstance(getApplicationContext()).addToRequestQue(stringRequest);
    }

    public void parsData(JSONObject jsonObject) {
        try {
            JSONObject buy_usdObject = jsonObject.getJSONObject("buy_usd");
            JSONObject buy_eurObject = jsonObject.getJSONObject("buy_eur");
            String[] priceAraaayUsd = buy_usdObject.getString("price").split(",");
            String priceStringUsd = priceAraaayUsd[0] + priceAraaayUsd[1];
            priceUsd = Integer.parseInt(priceStringUsd);
            titleUsd = buy_usdObject.getString("title");
            jdateUsd = buy_usdObject.getString("jdate");
            gdateUsd = buy_usdObject.getString("gdate");
            String[] priceArarayEuro = buy_eurObject.getString("price").split(",");
            String priceStringEro = priceArarayEuro[0] + priceArarayEuro[1];
            priceEuro = Integer.parseInt(priceStringEro);
            titleEuro = buy_eurObject.getString("title");
            jdateEuro = buy_eurObject.getString("jdate");
            gdateEuro = buy_eurObject.getString("gdate");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
