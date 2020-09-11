package com.example.leaderboard_app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.example.leaderboard_app.databinding.ActivityFormBinding;
import com.example.leaderboard_app.databinding.ConfirmationDialogDesignBinding;
import com.example.leaderboard_app.databinding.DialogDesignBinding;
import com.example.leaderboard_app.utils.ApiUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Submit_Form extends AppCompatActivity {
    private ActivityFormBinding mFormBinding;
    public static final String BASE_GOOGLE_FORM_URL ="https://docs.google.com/forms/d/e/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_form);

        //tool bar with the back arrow button
        Toolbar toolbar = findViewById(R.id.mtoolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //set on click listener for arrow back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //set on click listener for button
        mFormBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isFieldNotEmpty()){
                    showConfirmDialog();
                }else {
                    Toast.makeText(Submit_Form.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void showConfirmDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        ConfirmationDialogDesignBinding dialogDesignBinding = ConfirmationDialogDesignBinding.inflate(inflater);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialogDesignBinding.getRoot())
                .create();

        alertDialog.show();

        //when the x button is clicked on dialog
        dialogDesignBinding.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showDialog(getString(R.string.submission_cancelled), R.drawable.ic_baseline_report_problem_24);
            }
        });

        dialogDesignBinding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mFormBinding.progressBar3.setVisibility(View.VISIBLE);
                executeSubmitProject(mFormBinding.firstName.getText().toString(),
                        mFormBinding.lastName.getText().toString(),
                        mFormBinding.emailAddress.getText().toString(),
                        mFormBinding.gitHubLink.getText().toString());

                alertDialog.dismiss();

            }
        });
    }

    public void showDialog(String message, Integer icon){
        LayoutInflater inflater = LayoutInflater.from(this);
        DialogDesignBinding dialogDesignBinding = DialogDesignBinding.inflate(inflater);

        dialogDesignBinding.textViewMessage.setText(message);
        dialogDesignBinding.imageViewInfo.setBackgroundResource(icon);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialogDesignBinding.getRoot())
                .create();

        alertDialog.show();
    }


    private void executeSubmitProject(String fname, String lname, String email, String githubLink) {
        Client client= ApiUtil.getClient(BASE_GOOGLE_FORM_URL).create(Client.class);

        Call<ResponseBody> call = client.submitProject(
                fname,
                lname,
                email,
                githubLink);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Log.d(String.valueOf(response.code()), "onResponse: Success ");
                    clearFields();
                    showDialog(getString(R.string.submission_successful), R.drawable.ic_baseline_check_circle_24);
                }
                else {
                    showDialog(getString(R.string.submission_not_successful), R.drawable.ic_baseline_report_problem_24);
                    Log.d("error code", String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.getMessage());
                showDialog(getString(R.string.submission_not_successful), R.drawable.ic_baseline_report_problem_24);
            }

        });

    }

    public void clearFields(){
        mFormBinding.firstName.setText("");
        mFormBinding.lastName.setText("");
        mFormBinding.emailAddress.setText("");
        mFormBinding.gitHubLink.setText("");
    }

    public Boolean isFieldNotEmpty(){
        boolean checkField = !mFormBinding.firstName.getText().toString().isEmpty() &&
                !mFormBinding.lastName.getText().toString().isEmpty() &&
                !mFormBinding.emailAddress.getText().toString().isEmpty() &&
                !mFormBinding.gitHubLink.getText().toString().isEmpty();

        return checkField;
    }

}