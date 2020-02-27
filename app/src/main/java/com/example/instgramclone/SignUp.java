package com.example.instgramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button btnSave, btnGetAllData, btnTransition;
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private TextView txtGetData;
    private String allKickBoxer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSave = findViewById(R.id.btnSave);
        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnTransition = findViewById(R.id.btnNextActivity);

        btnSave.setOnClickListener(SignUp.this);
        txtGetData.setOnClickListener(SignUp.this);
        btnGetAllData.setOnClickListener(SignUp.this);
        btnTransition.setOnClickListener(SignUp.this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                try {
                    final ParseObject kickBoxer = new ParseObject("KickBoxer");
                    kickBoxer.put("name", edtName.getText().toString());
                    kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));
                    kickBoxer.put("punchPower", Integer.parseInt(edtPunchPower.getText().toString()));
                    kickBoxer.put("kickSpeed", Integer.parseInt(edtKickSpeed.getText().toString()));
                    kickBoxer.put("kickPower", Integer.parseInt(edtKickPower.getText().toString()));
                    kickBoxer.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " is saved to server.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    FancyToast.makeText(SignUp.this, "ERROR", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                break;
            case R.id.txtGetData:
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("LkJgFYQ7BV", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if( object != null && e == null){
                            txtGetData.setText(object.get("name") + " - " + "Punch Power: " + object.get("punchPower"));
                        }
                    }
                });
                break;
            case R.id.btnGetAllData:
                allKickBoxer = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                //set condition
                //queryAll.whereGreaterThan("punchPower", 1000);
                queryAll.whereGreaterThanOrEqualTo("punchPower", 400);
                queryAll.setLimit(1);
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null){
                            if(objects.size() > 0){
                                FancyToast.makeText(SignUp.this, "Success!",
                                        FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                for (ParseObject kickBoxer : objects){
                                    allKickBoxer = allKickBoxer + kickBoxer.get("name") + "\n";
                                }
                                FancyToast.makeText(SignUp.this, allKickBoxer,
                                        FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            }
                        }
                    }
                });
                break;
            case R.id.btnNextActivity:
                Intent intent = new Intent(SignUp.this, SignUpLoginActivity.class);
                startActivity(intent);
        }
    }
}
