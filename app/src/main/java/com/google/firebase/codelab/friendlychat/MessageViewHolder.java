package com.google.firebase.codelab.friendlychat;

import android.view.View;

import com.google.firebase.codelab.friendlychat.databinding.ItemMessageBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    private ItemMessageBinding mBinding;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        mBinding = ItemMessageBinding.bind(itemView);
    }

    public void bindMessage(FriendlyMessage friendlyMessage) {

    }
}
