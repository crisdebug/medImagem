package com.example.samuel.medimagem;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.samuel.medimagem.R;

public class RecyclerSectionItemDecoration extends RecyclerView.ItemDecoration {
    private final int headerOffset;
    private final boolean sticky;
    private final SectionCallback sectionCallback;

    private View headerView;
    private TextView header;

    public RecyclerSectionItemDecoration(int headerHeight, boolean sticky, @NonNull SectionCallback sectionCallback){
        headerOffset = headerHeight;
        this.sticky = sticky;
        this.sectionCallback = sectionCallback;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);
        if (sectionCallback.isSection(pos)){
            outRect.top = headerOffset;
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (headerView == null){
            headerView = inflateHeaderView(parent);
            header = (TextView) headerView.findViewById(R.id.data_exame_header);
            fixLayoutSize(headerView, parent);
        }

        CharSequence previousHeader = "";
        for (int i = 0; i<parent.getChildCount(); i++){
            View child = parent.getChildAt(i);
        }
    }

    public interface SectionCallback {
        boolean isSection(int position);

        CharSequence getSectionHeader(int position);
    }
}
