package example.technerd.com.contactsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

//import android.widget.AutoCompleteEditText;

public class Login extends AppCompatActivity {
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    EditText etEmail, etPassword;
    Button btnLogin,btnRegister;
    TextView tvReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        tvReset=findViewById(R.id.tvRest);

        showProgress(true);
        tvLoad.setText("Checking Credentials");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this,"Enter all fields",Toast.LENGTH_LONG).show();
                }
                else
                {
                    String email= etEmail.getText().toString().trim();
                    String password= etPassword.getText().toString().trim();

                    showProgress(true);

                    Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            ApplicationClass.user=response;

                            Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_LONG).show();

                            startActivity(new Intent(Login.this,MainActivity.class));
                            Login.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                         Toast.makeText(Login.this,"Error:" + fault.getMessage(),Toast.LENGTH_LONG).show();
                      showProgress(false);
                        }
                    }, true);
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this,Register.class));

            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this,"Enter Your Email to reset Password",Toast.LENGTH_LONG).show();

                }
                else
                {
                    String email = etEmail.getText().toString().trim();
                    showProgress(true);

                    Backendless.UserService.restorePassword(email, new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {


                            Toast.makeText(Login.this,"Succesfull. Check Your Email box",Toast.LENGTH_LONG).show();
                                showProgress(false);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                        Toast.makeText(Login.this,"Error" + fault.getMessage(),Toast.LENGTH_LONG).show();

                        showProgress(false);
                        }
                    });
                }

            }
        });
        tvLoad.setText("Checking credentials");

        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {
                if (response)
                    {
                        String userObectID = UserIdStorageFactory
                                .instance().getStorage().get();
                        tvLoad.setText("Logging you in,PLEASE WAIT!!");
                        Backendless.Data.of(BackendlessUser.class).findById(userObectID, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                ApplicationClass.user=response;

                                startActivity(new Intent(Login.this, MainActivity.class));
                                Login.this.finish();

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(Login.this, "Error"+fault.getMessage(),Toast.LENGTH_LONG).show();


                            }
                        });

                    }
                else
                    {
                        showProgress(false);

                    }


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(Login.this, "Error"+fault.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
