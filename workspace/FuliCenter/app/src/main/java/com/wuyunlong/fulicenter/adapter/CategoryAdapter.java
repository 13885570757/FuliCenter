package com.wuyunlong.fulicenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.CategoryChildBean;
import com.wuyunlong.fulicenter.bean.CategoryGroupBean;
import com.wuyunlong.fulicenter.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupList;//大类数量
    ArrayList<ArrayList<CategoryChildBean>> mChildList;//小类数量

    //构造方法
    public CategoryAdapter(Context mContext, ArrayList<CategoryGroupBean> groupList,
                           ArrayList<ArrayList<CategoryChildBean>> childList) {
        this.mContext = mContext;
        mGroupList = new ArrayList<>();
        mGroupList.addAll(groupList);
        mChildList = new ArrayList<>();
        mChildList.addAll(childList);

    }

    //大类的数量
    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    //小类的数量
    @Override
    public int getChildrenCount(int groupPosition) {

        return mChildList != null && mChildList.
                get(groupPosition) != null ? mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {

        return mGroupList != null ? mGroupList.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mChildList != null && mChildList.
                get(groupPosition) != null ? mChildList.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获取布局
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_group, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            convertView.getTag();
            holder = (GroupViewHolder) convertView.getTag();

        }
        CategoryGroupBean groupBean = getGroup(groupPosition);
        if (groupBean != null) {
            //下载图片
            ImageLoader.downloadImg(mContext, holder.mIvGroupImage, groupBean.getImageUrl());
            holder.mTvGroupName.setText(groupBean.getName());
            holder.mIvExpandImage.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ChildViewHolder) convertView.getTag();
        }CategoryChildBean childBean = getChild(groupPosition,childPosition);
        if (childBean!=null){
            ImageLoader.downloadImg(mContext,holder.mIvChildIamge,childBean.getImageUrl());
            holder.mTvChildName.setText(childBean.getName());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupList) {
        if (groupList!=null){
            groupList.clear();
            groupList.addAll(groupList);
        }
    }

    static class GroupViewHolder {
        @Bind(R.id.ivGroupImage)
        ImageView mIvGroupImage;
        @Bind(R.id.tvGroupName)
        TextView mTvGroupName;
        @Bind(R.id.ivExpandImage)
        ImageView mIvExpandImage;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @Bind(R.id.ivChildIamge)
        ImageView mIvChildIamge;
        @Bind(R.id.tvChildName)
        TextView mTvChildName;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
