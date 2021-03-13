package com.example.oo.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.oo.R;
import com.example.oo.util.POSTConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText editText_account;
    private EditText editText_password;
    private EditText editText_repassword;
    private Button button_register;
    private ImageView pass_clock;
    private ImageView repass_clock;
    private ImageView bt_back;
    private ImageView user_accont;
    private ImageView imageView_eye_password;
    private ImageView imageView_eye_repassword;
    private POSTConnection post_connection = new POSTConnection();
    private static int i = 2;
    private static int ii = 2;


    private String responseData = "1";


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String m = (String) msg.obj;
                    Toast.makeText(RegisterActivity.this, m, Toast.LENGTH_SHORT).show();
                    Log.e("错误信息",m);
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 3:
                    Toast.makeText(RegisterActivity.this, "请求超时", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editText_account = findViewById(R.id.edit_Register_account);
        editText_password = findViewById(R.id.edit_Register_password);
        editText_repassword = findViewById(R.id.edit_Register_repassword);
        button_register = findViewById(R.id.bt_Register);

        user_accont = findViewById(R.id.register_user_u);
        pass_clock = findViewById(R.id.register_pass_clock);
        repass_clock = findViewById(R.id.register_repass_clock);
        bt_back = findViewById(R.id.imageView2);
        imageView_eye_password = findViewById(R.id.password_eye);
        imageView_eye_repassword = findViewById(R.id.repassword_eye);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        editText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int mlongth = editText_password.length();
                if (mlongth == 0) {
                } else if (mlongth < 6 && mlongth > 0) {
                    editText_password.setHint("密码不能为空");//不能设置int，会闪退
                    editText_password.setHintTextColor(Color.parseColor("#FA1065"));
                    pass_clock.setImageResource(R.drawable.lock_red2);
                } else {
                    pass_clock.setImageResource(R.drawable.lock);
                }
            }
        });

        editText_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int mlongth = editText_account.length();
                if (mlongth == 0) {
                } else if (mlongth < 4 && mlongth > 0) {
                    editText_account.setHint("账号不能为空");//不能设置int，会闪退
                    editText_account.setHintTextColor(Color.parseColor("#FA1065"));
                    user_accont.setImageResource(R.drawable.user_red);
                } else {
                    user_accont.setImageResource(R.drawable.user);
                }
            }
        });
        editText_repassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int mlongth = editText_repassword.length();
                if (mlongth == 0) {
                } else if (mlongth < 6 && mlongth > 0) {
                    editText_repassword.setHint("密码不能为空");//不能设置int，会闪退
                    editText_repassword.setHintTextColor(Color.parseColor("#FA1065"));
                    repass_clock.setImageResource(R.drawable.lock_red2);
                } else {
                    repass_clock.setImageResource(R.drawable.lock);
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText_account.getText().toString().trim();
                String password = editText_password.getText().toString().trim();
                String repassword = editText_repassword.getText().toString().trim();
                int mlongth = editText_password.length();
                int mlongth_u = editText_account.length();
                Log.e("点击", "进入");


                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
                    Toast.makeText(RegisterActivity.this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (mlongth < 6) {
                    Toast.makeText(RegisterActivity.this, "密码长度必须大于6", Toast.LENGTH_SHORT).show();
                } else if (mlongth_u < 4) {
                    Toast.makeText(RegisterActivity.this, "账号长度必须大于4", Toast.LENGTH_SHORT).show();
                } else if (password.equals(repassword)) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("username", username);
                    map.put("password", password);
                    map.put("repassword", repassword);
                    Log.e("分支", "进入");

                    ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setTitle("正在注册...");
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new Thread(() -> {
                        try {
                            Thread.sleep(1200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                  //      responseData = post_connection.sendGetNetRequest("     ", map);
                        Log.e("返回值", responseData);
                        try {
                            if (responseData.equals("1")) {
                                progressDialog.dismiss();
                                showResponse(null, 3);
                            } else {
                                JSONObject jsonObject = new JSONObject(responseData);
                                int jsonerrorCode = jsonObject.getInt("errorCode");

                                if (jsonerrorCode == -1) {
                                    progressDialog.dismiss();
                                    String jsonerrorMsg = jsonObject.getString("errorMsg");
                                    showResponse(jsonerrorMsg, 1);
                                    Log.e("错误", "信息");
                                } else {
                                    progressDialog.dismiss();
                                    Log.e("注册", "成功");
                                    showResponse(null, 2);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
                }

            }
        });

        editText_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        imageView_eye_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i % 2 == 0) {
                    //如果选中，显示密码
                    imageView_eye_password.setImageResource(R.drawable.eye);
                    editText_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    i++;
                } else {
                    //否则隐藏密码
                    imageView_eye_password.setImageResource(R.drawable.eye_close);
                    editText_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    i++;
                }
            }
        });

        editText_repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        imageView_eye_repassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ii % 2 == 0) {
                    //如果选中，显示密码
                    imageView_eye_repassword.setImageResource(R.drawable.eye);
                    editText_repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ii++;
                } else {
                    //否则隐藏密码
                    imageView_eye_repassword.setImageResource(R.drawable.eye_close);
                    editText_repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ii++;
                }
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showResponse(String st, int in) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = in;
                message.obj = st;
                handler.sendMessage(message);
            }
        }).start();
    }

}