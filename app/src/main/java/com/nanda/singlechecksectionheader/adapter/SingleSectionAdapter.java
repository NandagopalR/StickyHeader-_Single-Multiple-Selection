package com.nanda.singlechecksectionheader.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nanda.singlechecksectionheader.R;
import com.nanda.singlechecksectionheader.model.ChildItem;
import com.nanda.singlechecksectionheader.model.HeaderItem;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nandagopal on 9/15/2017.
 */

public class SingleSectionAdapter extends SectioningAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<HeaderItem> stickyHeaderList;
    private ClickListener clickListener;
    private int mSelectedHeaderItem = -1;
    private int mSelectedChildItem = -1;

    public SingleSectionAdapter(Context context, ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        stickyHeaderList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setStickyItemList(List<HeaderItem> itemList) {
        if (itemList == null) {
            return;
        }
        stickyHeaderList.clear();
        stickyHeaderList.addAll(itemList);
        notifyAllSectionsDataSetChanged();
    }

    public interface ClickListener {
        void onItemSelected(HeaderItem item, int headerPosition, int childPosition);
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return !TextUtils.isEmpty(stickyHeaderList.get(sectionIndex).getTitle());
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerUserType) {
        View view = inflater.inflate(R.layout.item_header, parent, false);
        return new ItemHeaderViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemUserType) {
        View view = inflater.inflate(R.layout.item_child, parent, false);
        return new ItemChildViewHolder(view);
    }

    @Override
    public int getNumberOfSections() {
        return stickyHeaderList.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return stickyHeaderList.get(sectionIndex).getChildItemList().size();
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int sectionIndex,
                                       int headerUserType) {
        super.onBindHeaderViewHolder(viewHolder, sectionIndex, headerUserType);

        ItemHeaderViewHolder holder = (ItemHeaderViewHolder) viewHolder;

        HeaderItem stickyHeader = stickyHeaderList.get(sectionIndex);

        holder.setHeaderDataToView(stickyHeader);
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int sectionIndex, int itemIndex,
                                     int itemUserType) {
        super.onBindItemViewHolder(viewHolder, sectionIndex, itemIndex, itemUserType);

        ItemChildViewHolder holder = (ItemChildViewHolder) viewHolder;

        ChildItem stickyItem = stickyHeaderList.get(sectionIndex).getChildItemList().get(itemIndex);

        holder.setChildDataToView(stickyItem, sectionIndex, itemIndex);
    }

    class ItemHeaderViewHolder extends HeaderViewHolder {

        @BindView(R.id.tv_header)
        TextView tvHeader;

        public ItemHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setHeaderDataToView(HeaderItem item) {
            tvHeader.setText(item.getTitle());
        }
    }

    class ItemChildViewHolder extends SectioningAdapter.ItemViewHolder {

        @BindView(R.id.tv_child)
        TextView tvCild;
        @BindView(R.id.rb_selection)
        RadioButton rbSelection;

        private int childPosition;
        private int headerPosition;

        public ItemChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setChildDataToView(ChildItem item, int sectionPosition, int itemPosition) {
            this.headerPosition = sectionPosition;
            this.childPosition = itemPosition;

            rbSelection.setChecked(headerPosition == mSelectedHeaderItem && childPosition == mSelectedChildItem);

            tvCild.setText(item.getMessage());
        }

        @OnClick(R.id.layout_child)
        public void onChildItemClicked() {

            if (headerPosition < 0)
                return;
            if (childPosition < 0)
                return;

            mSelectedHeaderItem = headerPosition;
            mSelectedChildItem = childPosition;
            if (clickListener != null) {
                clickListener.onItemSelected(stickyHeaderList.get(headerPosition), headerPosition, childPosition);
            }
            notifyAllSectionsDataSetChanged();

        }
    }
}