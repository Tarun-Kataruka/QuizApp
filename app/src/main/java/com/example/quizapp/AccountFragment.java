package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    private LinearLayout logoutB;
    private TextView profile_img_text, name;
    private LinearLayout leaderB, profileB, bookmarkB;
    private BottomNavigationView bottomNavigationView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AccountFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View view =inflater.inflate(R.layout.fragment_account, container, false);
        logoutB = view.findViewById(R.id.logoutButton);

        initViews(view);
        String userName = DbQuery.myProfile.getName();
        profile_img_text.setText(userName.toUpperCase().substring(0,1));
        name.setText(userName);



      logoutB.setOnClickListener((v) -> {
              FirebaseAuth.getInstance().signOut();
                      Intent intent = new Intent(getContext(), LoginActivity.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      startActivity(intent);
                      getActivity().finish();
      });

      bookmarkB.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          }
      });

      profileB.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(getContext(), MyProfileActivity.class);
            startActivity(intent);
          }
      });

      leaderB.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              bottomNavigationView.setSelectedItemId(R.id.nav_leaderboard);
          }
      });

      return view;
    }


    private void initViews(View view)
    {
        logoutB = view.findViewById(R.id.logoutButton);
        profile_img_text=view.findViewById(R.id.profile_img_text);
        name=view.findViewById(R.id.name);
        leaderB=view.findViewById(R.id.leaderboardB);
        bookmarkB=view.findViewById(R.id.bookmarkB);
        profileB=view.findViewById(R.id.profileButton);
        bottomNavigationView= getActivity().findViewById(R.id.bottom_nav_bar);
    }
}