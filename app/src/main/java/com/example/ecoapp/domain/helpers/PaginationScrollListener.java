package com.example.ecoapp.domain.helpers;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager;
    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotalItemCount = 5;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        if (totalItemCount != previousTotalItemCount) {
            currentPage = 0;
            previousTotalItemCount = totalItemCount;
        }

        if (!isLoading() && lastVisibleItemPosition + visibleThreshold >= totalItemCount) {
            currentPage++;
            loadMoreItems();
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLoading();

    public abstract boolean isLastPage();
}