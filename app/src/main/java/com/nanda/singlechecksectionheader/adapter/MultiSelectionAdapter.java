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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiSelectionAdapter extends SectioningAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<HeaderItem> stickyHeaderList;
    private Set<String> selectedItemSet;

    public MultiSelectionAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        stickyHeaderList = new ArrayList<>();
        selectedItemSet = new HashSet<>();

    }

    public void setStickyItemList(List<HeaderItem> itemList) {
        if (itemList == null) {
            return;
        }
        stickyHeaderList.clear();
        stickyHeaderList.addAll(itemList);
        notifyAllSectionsDataSetChanged();
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

    public List<HeaderItem> fetchSelectedItemsInList() {

        List<HeaderItem> selectedHeaderList = new ArrayList<>();
        List<ChildItem> selectedChildItemList = new ArrayList<>();
        for (int i = 0, stickyHeaderListSize = stickyHeaderList.size(); i < stickyHeaderListSize; i++) {
            HeaderItem headerItem = stickyHeaderList.get(i);
            if (headerItem.isMultiSelect()) {
                if (headerItem.getChildItemList() != null && headerItem.getChildItemList().size() > 0) {
                    List<ChildItem> childItemList = headerItem.getChildItemList();
                    for (int i1 = 0, childItemListSize = childItemList.size(); i1 < childItemListSize; i1++) {
                        ChildItem childItem = childItemList.get(i1);
                        if (childItem.isMultiSelect()) {
                            selectedChildItemList.add(childItem);
                        }
                    }
                    selectedHeaderList.add(new HeaderItem(headerItem.getTitle(), selectedChildItemList));
                }
            }
        }
        return selectedHeaderList;
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

            rbSelection.setChecked(item.isMultiSelect());

            tvCild.setText(item.getMessage());
        }

        @OnClick(R.id.layout_child)
        public void onChildItemClicked() {

            if (headerPosition < 0)
                return;
            if (childPosition < 0)
                return;

            String clickedPosition = String.format("&d-&d", headerPosition, childPosition);

            if (selectedItemSet.contains(clickedPosition)) {
                selectedItemSet.remove(clickedPosition);
                stickyHeaderList.get(headerPosition).setMultiSelect(false);
                stickyHeaderList.get(headerPosition).getChildItemList().get(childPosition).setMultiSelect(false);
            } else {
                selectedItemSet.add(clickedPosition);
                stickyHeaderList.get(headerPosition).setMultiSelect(true);
                stickyHeaderList.get(headerPosition).getChildItemList().get(childPosition).setMultiSelect(true);
            }

            notifyAllSectionsDataSetChanged();

        }
    }
}