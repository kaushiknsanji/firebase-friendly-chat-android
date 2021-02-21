package com.google.firebase.codelab.friendlychat;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.codelab.friendlychat.databinding.ItemMessageBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    // Constant used for Logs
    public static final String TAG = MessageViewHolder.class.getSimpleName();

    private final ItemMessageBinding mBinding;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        mBinding = ItemMessageBinding.bind(itemView);
    }

    public void bindMessage(FriendlyMessage friendlyMessage) {
        if (friendlyMessage.getText() != null && !TextUtils.isEmpty(friendlyMessage.getText())) {
            // If it contains a text message
            mBinding.messageTextView.setText(friendlyMessage.getText());
            mBinding.messageTextView.setVisibility(View.VISIBLE);
        } else {
            // If it does not contain any text message
            mBinding.messageTextView.setText("");
            mBinding.messageTextView.setVisibility(View.GONE);
        }

        if (friendlyMessage.getImageUrl() != null) {
            // If it contains an Image

            // Read the Image URL
            String imageUrl = friendlyMessage.getImageUrl();

            if (imageUrl.startsWith("gs://")) {
                // If the Image URL is pointing to an Image stored in Firebase Cloud Storage

                // Get the Storage Reference pointing to the Image URL
                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);

                // Asynchronously retrieve the downloadable URL to Image via its task
                storageReference.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // On Success, load the Image into View using Glide
                                GlideApp.with(mBinding.messageImageView.getContext())
                                        .load(uri)
                                        .into(mBinding.messageImageView);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // On Failure, log the exception as warning
                                Log.w(TAG, "Reading Image from URL was unsuccessful", e);
                            }
                        });
            } else {
                // If the Image URL is not from Firebase Cloud Storage,
                // then load the Image from URL into ImageView directly using Glide
                GlideApp.with(mBinding.messageImageView.getContext())
                        .load(friendlyMessage.getImageUrl())
                        .into(mBinding.messageImageView);
            }

            mBinding.messageImageView.setVisibility(View.VISIBLE);

        } else {
            // If it does not contain any Image
            mBinding.messageImageView.setVisibility(View.GONE);
        }

        // On both messages

        // Set the messenger's profile picture
        GlideApp.with(mBinding.getRoot().getContext())
                .load(friendlyMessage.getPhotoUrl())
                .fallback(R.drawable.ic_account_circle_black_36dp)
                .into(mBinding.messengerImageView);

        // Set the messenger's name
        mBinding.messengerTextView.setText(friendlyMessage.getName());
    }
}
