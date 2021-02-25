/*
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.codelab.friendlychat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.codelab.friendlychat.databinding.ActivityMainBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {

    // Unused constants (required for Grow-Friendly-Chat)
    /*
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    private static final int REQUEST_INVITE = 1;
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private static final String MESSAGE_URL = "http://friendlychat.firebase.google.com/message/";
    */

    public static final String MESSAGES_CHILD = "messages";
    public static final String ANONYMOUS = "anonymous";
    private static final String TAG = "MainActivity";
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";
    private static final int REQUEST_IMAGE = 2;

    private GoogleSignInClient mSignInClient;
    private ActivityMainBinding mBinding;
    private LinearLayoutManager mLinearLayoutManager;

    // TODO: Firebase instance variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate with ViewBinding
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        // Set the root view from ViewBinding instance
        setContentView(mBinding.getRoot());

        // TODO: Initialize Firebase Auth and check if user is signed in

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this, gso);

        // TODO: Get the Query to the "messages" child node to be observed for changes

        // TODO: Configure the options required for FirebaseRecyclerAdapter with the above Query reference

        // TODO: Construct the FirebaseRecyclerAdapter with the options set

        // TODO: To be removed and placed in onBindViewHolder of the above FirebaseRecyclerAdapter
        mBinding.progressBar.setVisibility(ProgressBar.INVISIBLE);

        // Initialize LinearLayoutManager and RecyclerView
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mBinding.messageRecyclerView.setLayoutManager(mLinearLayoutManager);
        // TODO: Set Adapter on RecyclerView

        // TODO: Register ScrollToBottomObserver as Adapter Data Observer
        //  to initiate scroll to bottom of the list when the user is at the bottom of the list
        //  in order to show newly added messages

        // Disable the send button when there is no text in this input message field
        mBinding.messageEditText.addTextChangedListener(new ButtonObserver(mBinding.sendButton));

        mBinding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click.
            }
        });

        mBinding.addMessageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Select image for image message on click. Use IntentUtility's launchGallery()
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_out_menu) {
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        // TODO: Sign-Out user
    }

    /**
     * Returns the URL to the User's profile picture as stored in Firebase Project's user database.
     * Can be {@code null} when not present or if user is not authenticated.
     */
    @Nullable
    private String getUserPhotoUrl() {
        // TODO: Implement
        return null;
    }

    /**
     * Returns the display name of the User as stored in Firebase Project's user database.
     * Can be {@link this.ANONYMOUS} if the user is not authenticated.
     */
    private String getUserName() {
        // TODO: Implement
        return ANONYMOUS;
    }

}
