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
        mAdapter = new CategoryAdapter(mContext,mGroupList,mChildList);
        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    protected void initView() {
        mElvCategory.setGroupIndicator(null);//取消默认箭头
        mElvCategory.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        downloadGroup();

    }

    private void downloadGroup() {
        NetDao.downloadCategoruGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result!=null&&result.length>0){
                   ArrayList<CategoryGroupBean> groupList= ConvertUtils.array2List(result);
                    mAdapter.initData(groupList);

                }
            }

            @Override
            public void onError(String error) {
                L.e("downloadGroup"+error);
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
