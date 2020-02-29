package com.example.instgramclone;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession, edtProfileHobbies,
            edtProfileFavorSport;
    private Button btnProfileUpdateInfo;
    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavorSport = view.findViewById(R.id.edtProfileFavorSport);
        btnProfileUpdateInfo = view.findViewById(R.id.btnProfileUpdateInfo);
        final ParseUser parseUser = ParseUser.getCurrentUser();


        //use equal method that compare the value of objects.
        //get user data from server.
        //use +"" instead of toString, because toString maybe return null that cannot be set as Text.
        if(parseUser.get("profileName") == null){
            edtProfileName.setText("");
        } else {
            edtProfileName.setText(parseUser.get("profileName").toString());
        }
        edtProfileBio.setText(parseUser.get("profileBio") + "");
        edtProfileProfession.setText(parseUser.get("profileProfession") + "");
        edtProfileHobbies.setText(parseUser.get("profileHobbies") + "");
        edtProfileFavorSport.setText(parseUser.get("profileFavSport") + "");

        //push our user data to server.
        btnProfileUpdateInfo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               parseUser.put("profileName", edtProfileName.getText().toString());
               parseUser.put("profileBio", edtProfileBio.getText().toString());
               parseUser.put("profileProfession", edtProfileProfession.getText().toString());
               parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
               parseUser.put("profileFavSport", edtProfileFavorSport.getText().toString());
               parseUser.saveInBackground(new SaveCallback() {
                   @Override
                   public void done(ParseException e) {
                       if (e == null){
                           FancyToast.makeText(getContext(), parseUser.getUsername()+"'s profile info is updated.",
                                   FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                       } else {
                           FancyToast.makeText(getContext(), "Error: " + e.getMessage(),
                                   FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                       }
                   }
               });
           }
       });


       return view;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
