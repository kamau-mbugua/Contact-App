package example.technerd.com.contactsapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
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
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.Calendar;

public class NewContact extends AppCompatActivity {
    private  EditText etDate;
  EditText etName,etEmail,etPhone;
   Button btnNew;
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    private  DatePickerDialog.OnDateSetListener mDateSetListner;
    public  static final String TAG="NewContact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);


        etDate=findViewById(R.id.etDate);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPhone=findViewById(R.id.etPhone);
        btnNew=findViewById(R.id.btnNew);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().isEmpty() || etName.getText().toString().isEmpty()
                || etPhone.getText().toString().isEmpty() || etDate.getText().toString().isEmpty())
                {
                    Toast.makeText(NewContact.this, "Error: Enter all fields",Toast.LENGTH_LONG).show();

                }
                else
                    {
                        //Toast.makeText(NewContact.this, "Error"+fou)
                     //   Toast.makeText(NewContact.this, "Error"+fault.getMessage(),Toast.LENGTH_LONG).show();

                        String name = etName.getText().toString().trim();
                        String email = etEmail.getText().toString().trim();
                        String phone = etPhone.getText().toString().trim();
                      //  Date dob = etDate.getText().toString().trim();

                        String dob = etDate.getText().toString().trim();

                        Contact contact = new Contact();
                                contact.setName(name);
                                contact.setEmail(email);
                                contact.setNumber(phone);
                                contact.setDob(dob);
                                contact.setUserEmail(ApplicationClass.user.getEmail());


                        showProgress(true);
                        tvLoad.setText(R.string.tvloadnewtxt);

                        Backendless.Persistence.save(contact, new AsyncCallback<Contact>() {
                            @Override
                            public void handleResponse(Contact response) {
                                Toast.makeText(NewContact.this,"Saved",Toast.LENGTH_LONG).show();
                                showProgress(false);
                                etName.setText("");
                                etEmail.setText("");
                                etDate.setText("");
                                etPhone.setText("");


                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                                Toast.makeText(NewContact.this,"Error"+fault.getMessage(),Toast.LENGTH_LONG).show();

                                showProgress(false);
                            }
                        });
                    }

            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(NewContact.this,R.style.AppTheme,mDateSetListner,year,month,day);
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
