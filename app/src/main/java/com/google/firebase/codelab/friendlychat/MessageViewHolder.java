package com.google.firebase.codelab.friendlychat;

import android.net.Uri;
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
        if (friendlyMessage.getText() != null) {
            // If it is a Text based message
            mBinding.messageTextView.setText(friendlyMessage.getText());
            mBinding.messageTextView.setVisibility(View.VISIBLE);
            mBinding.messageImageView.setVisibility(View.GONE);

        } else if (friendlyMessage.getImageUrl() != null) {
            // If it is an Image based message

            // Read the Image URL
            String imageUrl = friendlyMessage.getImageUrl();

            if (imageUrl.startsWith("gs://")) {
                // If the Image URL is pointing to an Image stored in Firebase Cloud Storage

                // Get the Storage Reference pointing to the Image URL
                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);

                // Asynchronously retrieve the downloadable Image via its task
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

            mBinding.messageTextView.setVisibility(View.GONE);
            mBinding.messageImageView.setVisibility(View.VISIBLE);
        }
    }
}
