package com.google.firebase.codelab.friendlychat;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView {@link androidx.recyclerview.widget.RecyclerView.ItemDecoration} subclass
 * to add spacing between the items and its parent in the list managed by {@link LinearLayoutManager}.
 * <p>
 * NOTE: Applicable for List based layout in {@link RecyclerView#VERTICAL} orientation only.
 *
 * @author Kaushik N Sanji
 */
public class VerticalListItemSpacingDecoration extends RecyclerView.ItemDecoration {
    // Stores the spacing to be applied between the items in the List
    private final int mVerticalOffsetSize;
    // Stores the spacing to be applied between the items and its parent in the List
    private final int mHorizontalOffsetSize;

    /**
     * Constructor of {@link VerticalListItemSpacingDecoration}
     *
     * @param verticalOffsetSize   The spacing in Pixels to be applied between the items in the List
     * @param horizontalOffsetSize The spacing in Pixels to be applied between the items and its parent
     */
    public VerticalListItemSpacingDecoration(int verticalOffsetSize, int horizontalOffsetSize) {
        mVerticalOffsetSize = verticalOffsetSize;
        mHorizontalOffsetSize = horizontalOffsetSize;
    }

    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager
                && ((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == RecyclerView.VERTICAL) {
            // Proceed only when RecyclerView is managed by LinearLayoutManager and its orientation is vertical

            // Get the Child View position in the adapter
            int position = parent.getChildAdapterPosition(view);

            // Evaluates to first item when position is 0
            boolean isFirstItem = (position == 0);

            // Set full spacing to the top when the Item is the First Item in the list
            if (isFirstItem) {
                outRect.top = mVerticalOffsetSize;
            }

            // Set full spacing to bottom
            outRect.bottom = mVerticalOffsetSize;
            // Set full spacing to left
            outRect.left = mHorizontalOffsetSize;
            // Set full spacing to right
            outRect.right = mHorizontalOffsetSize;
        } else {
            // Else, delegate to super to handle
            super.getItemOffsets(outRect, view, parent, state);
        }
    }
}
