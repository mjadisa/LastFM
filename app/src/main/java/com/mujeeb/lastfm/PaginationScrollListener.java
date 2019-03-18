package com.mujeeb.lastfm;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class PaginationScrollListener extends RecyclerView.OnScrollListener {
    private final LinearLayoutManager linearLayoutManager;
    private final PaginationStateListener listener;

    public PaginationScrollListener(@NonNull LinearLayoutManager linearLayoutManager,
                                    @NonNull PaginationStateListener paginationStateListener) {
        this.linearLayoutManager = linearLayoutManager;
        this.listener = paginationStateListener;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = linearLayoutManager.getChildCount();
        int totalCount = linearLayoutManager.getItemCount();
        int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (!listener.isLastPage() && !listener.isLoading()) {
            if ((visibleItemCount + firstVisiblePosition) >= totalCount &&
                    firstVisiblePosition >= 0 &&
                    totalCount >= ConstantString.RESULTS_PER_PAGE) {
                listener.loadMoreItems();
            }
        }
    }

    public interface PaginationStateListener {
        boolean isLoading();

        boolean isLastPage();

        void loadMoreItems();
    }
}
