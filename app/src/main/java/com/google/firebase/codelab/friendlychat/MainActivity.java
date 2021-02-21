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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.codelab.friendlychat.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final String MESSAGES_CHILD = "messages";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";
    private static final int REQUEST_IMAGE = 2;

    private static final int REQUEST_INVITE = 1;
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private static final String MESSAGE_URL = "http://friendlychat.firebase.google.com/message/";

    private SharedPreferences mSharedPreferences;
    private GoogleSignInClient mSignInClient;

    private ActivityMainBinding mBinding;
    private LinearLayoutManager mLinearLayoutManager;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mFirebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate with ViewBinding
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        // Set the root view from ViewBinding instance
        setContentView(mBinding.getRoot());

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() == null) {
            // User is Not signed-in, launch the Sign In Activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize Realtime Database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        // Get the reference to the "messages" child node to be observed for changes
        DatabaseReference messagesRef = mFirebaseDatabase.getReference().child(MESSAGES_CHILD);

        // Configure the options required for FirebaseRecyclerAdapter with the above Query reference
        FirebaseRecyclerOptions<FriendlyMessage> options = new FirebaseRecyclerOptions.Builder<FriendlyMessage>()
                .setQuery(messagesRef, FriendlyMessage.class)
                // Listen to the changes in the Query and automatically update to the UI
                .setLifecycleOwner(this)
                .build();

        // Construct the FirebaseRecyclerAdapter with the options set
        mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(options) {
            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MessageViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_message, parent, false)
                );
            }

            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull FriendlyMessage message) {
                mBinding.progressBar.setVisibility(ProgressBar.INVISIBLE);
                holder.bindMessage(message);
            }
        };

        // Initialize LinearLayoutManager and RecyclerView
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mBinding.messageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mBinding.messageRecyclerView.setAdapter(mFirebaseRecyclerAdapter);
        mBinding.messageRecyclerView.addItemDecoration(new VerticalListItemSpacingDecoration(
                getResources().getDimensionPixelSize(R.dimen.main_item_list_spacing),
                getResources().getDimensionPixelSize(R.dimen.main_item_parent_spacing)
        ));

        // Register an observer for watching changes in the Adapter data in order to scroll
        // to the bottom of the list when the user is at the bottom of the list
        // in order to show newly added messages
        mFirebaseRecyclerAdapter.registerAdapterDataObserver(
                new ScrollToBottomObserver(
                        mBinding.messageRecyclerView,
                        mFirebaseRecyclerAdapter,
                        mLinearLayoutManager
                )
        );

        mBinding.messageEditText.addTextChangedListener(new ButtonObserver(mBinding.sendButton));

        mBinding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send messages on click.
            }
        });

        mBinding.addMessageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Select image for image message on click.
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
        // Sign-Out user from the backend and identity provider
        mFirebaseAuth.signOut();
        mSignInClient.signOut();
        // Start the SignInActivity and finish the current activity
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    @Nullable
    private String getUserPhotoUrl() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null && user.getPhotoUrl() != null) {
            return user.getPhotoUrl().toString();
        }
        return null;
    }

    private String getUserName() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }
        return ANONYMOUS;
    }

}