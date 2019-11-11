package example.technerd.com.contactsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.Calendar;

public class Register extends AppCompatActivity {
    public  static final String TAG="Register";
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    private TextView etDate;
    private  DatePickerDialog.OnDateSetListener mDateSetListner;
  //  private RadioGroup genderRadioGroup;


    EditText etName,etEmail,etPassword,etPhone,etConfirm;
    Button btnRegister;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mLoginFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etConfirm=findViewById(R.id.etConfirm);
        etDate=findViewById(R.id.etDate);
        etEmail=findViewById(R.id.etEmail);
        etName=findViewById(R.id.etName);
        etPassword=findViewById(R.id.etPassword);
        etPhone=findViewById(R.id.etPhone);
        btnRegister=findViewById(R.id.btnRegister);
        tvLogin=findViewById(R.id.tvLogin);


   etDate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar cal= Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month= cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog= new DatePickerDialog(Register.this,R.style.AppTheme,mDateSetListner,year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
          //  startActivity(new Intent(Register.this,DatePkr.class));
        }
    });

   mDateSetListner = new DatePickerDialog.OnDateSetListener() {
       @Override
       public void onDateSet(DatePicker datePicker, int day, int month, int year) {
           month= month+1;
           Log.d(TAG,"OnDateSet: dd/mm/yyyy"+day +"/"+month+ "/"+ year);
           String date= day +"/"+month+ "/"+ year;
          etDate.setText(date);

       }
   };



     //   genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etName.getText().toString().trim().isEmpty() || etEmail.getText().toString().trim().isEmpty()
                        || etDate.getText().toString().trim().isEmpty() || etPassword.getText().toString().trim().isEmpty()
                        || etPhone.getText().toString().trim().isEmpty() || etConfirm.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(Register.this,"Please Enter All Fields",Toast.LENGTH_LONG).show();
                }

                else
                {
                    if (etPassword.getText().toString().trim().equals(etConfirm.getText().toString().trim()))
                    {
                       // Register.this.finish();
                        String email = etEmail.getText().toString().trim();
                        String phone =etPhone.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();
                        //Date date = etDate.getText().toString().trim();
                        String date = etDate.getText().toString().trim();
                        String name= etName.getText().toString().trim();

                     BackendlessUser user = new BackendlessUser();
                     user.setEmail(email);
                     user.setPassword(password);
                     user.setProperty("name", name);
                        user.setProperty("name", date);
                        user.setProperty("phoneNumber", phone);

                        showProgress(true);
                        tvLoad.setText("Registering new user to our system");

                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {


                                showProgress(false);
                            Toast.makeText(Register.this,"Registered",Toast.LENGTH_LONG).show();
                                Register.this.finish();

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(Register.this, "Error:" + fault.getMessage(),Toast.LENGTH_LONG).show();
                                showProgress(false);
                            }
                        });



                    }
                    else
                        {
                            Toast.makeText(Register.this,"Password Does not Match",Toast.LENGTH_SHORT).show();
                        }
                }

            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Register.this,Login.class));

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
