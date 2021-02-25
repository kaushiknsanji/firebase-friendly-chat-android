package com.google.firebase.codelab.friendlychat;

import android.view.View;

import com.google.firebase.codelab.friendlychat.databinding.ItemMessageBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link RecyclerView.ViewHolder} subclass for the message items displayed
 * in the RecyclerView of {@link MainActivity}
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {

    // Constant used for Logs
    public static final String TAG = MessageViewHolder.class.getSimpleName();

    private final ItemMessageBinding mBinding;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        mBinding = ItemMessageBinding.bind(itemView);
    }

    public void bindMessage(FriendlyMessage friendlyMessage) {
        // TODO: Bind ItemView with FriendlyMessage data
    }

}