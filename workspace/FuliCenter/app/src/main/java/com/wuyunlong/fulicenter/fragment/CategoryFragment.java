package com.wuyunlong.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.activity.MainActivity;
import com.wuyunlong.fulicenter.adapter.CategoryAdapter;
import com.wuyunlong.fulicenter.bean.CategoryChildBean;
import com.wuyunlong.fulicenter.bean.CategoryGroupBean;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.net.OkHttpUtils;
import com.wuyunlong.fulicenter.utils.ConvertUtils;
import com.wuyunlong.fulicenter.utils.L;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment {


    @Bind(R.id.elvCategory)
    ExpandableListView mElvCategory;

    MainActivity mContext;
    CategoryAdapter mAdapter;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    int groupCount;


    public CategoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getContext();
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();

        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initView() {
        mElvCategory.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                mElvCategory.expandGroup(groupPosition);
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        downloadGroup();
        mElvCategory.setGroupIndicator(null);//取消默认箭头
        mAdapter = new CategoryAdapter(mContext, mGroupList, mChildList);
        mElvCategory.setAdapter(mAdapter);

    }

    private void downloadGroup() {
        NetDao.downloadCategoryGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                L.e("=========>downloadGroup下载方法");
                if (result != null && result.length > 0) {
                    ArrayList<CategoryGroupBean> groupList = ConvertUtils.array2List(result);
                   L.e("groupList="+groupList.size());
                    mGroupList.addAll(groupList);
                    //根据大类的角标，知道大类下载顺序
                    for (int i = 0; i < groupList.size(); i++) {
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        CategoryGroupBean groupBean = groupList.get(i);
                        downloadChild(groupBean.getId(), i);
                    }
                }
            }


            @Override
            public void onError(String error) {
                L.e("downloadGroup" + error);
            }
        });
    }

    private void downloadChild(int id, final int index) {//index为指定小类的顺序
        NetDao.downloadCategoryChild(mContext, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                L.e("=========>downloadChild");
                if (result != null && result.length > 0) {
                    ArrayList<CategoryChildBean> childList = ConvertUtils.array2List(result);
                    mChildList.set(index,childList);
                    //L.e(childList.toString());
                    // mAdapter.initData(mGroupList,mChildList);
                }
                //添加判断，防止一直刷新界面。
                if (groupCount == mGroupList.size()) {
//                  mElvCategory.setGroupIndicator(null);//取消默认箭头
//                  mAdapter = new CategoryAdapter(mContext,mGroupList,mChildList);
//                  mElvCategory.setAdapter(mAdapter);
                    mAdapter.initData(mGroupList, mChildList);
                }
            }

            @Override
            public void onError(String error) {
                L.e("=======" + error);
            }
        });
    }

    @Override
    protected void setListener() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
