package com.google.firebase.codelab.friendlychat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link RecyclerView.AdapterDataObserver} subclass to initiate scroll to bottom of the list
 * when the user is at the bottom of the list in order to show newly added messages.
 */
public class ScrollToBottomObserver extends RecyclerView.AdapterDataObserver {

    private final RecyclerView mRecyclerView;
    private final RecyclerView.Adapter<?> mAdapter;
    private final LinearLayoutManager mLinearLayoutManager;

    public ScrollToBottomObserver(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter, LinearLayoutManager linearLayoutManager) {
        mRecyclerView = recyclerView;
        mAdapter = adapter;
        mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);

        final int totalItemCount = mAdapter.getItemCount();
        final int lastVisibleItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

        final boolean isBottomReached = (positionStart >= totalItemCount - 1) && (lastVisibleItemPosition == positionStart - 1);
        final boolean isLoading = lastVisibleItemPosition == RecyclerView.NO_POSITION;

        if (isLoading || isBottomReached) {
            // If the RecyclerView is still loading or the user is at the bottom of the list,
            // scroll to the bottom of the list to show the newly added message
            mRecyclerView.scrollToPosition(positionStart);
        }
    }
}