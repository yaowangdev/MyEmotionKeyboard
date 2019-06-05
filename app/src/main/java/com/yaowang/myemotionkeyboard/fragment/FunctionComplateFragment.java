package com.yaowang.myemotionkeyboard.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.yaowang.myemotionkeyboard.R;
import com.yaowang.myemotionkeyboard.adapter.EmotionGridViewAdapter;
import com.yaowang.myemotionkeyboard.adapter.EmotionPagerAdapter;
import com.yaowang.myemotionkeyboard.adapter.FunctionGridViewAdapter;
import com.yaowang.myemotionkeyboard.emotionkeyboardview.EmojiIndicatorView;
import com.yaowang.myemotionkeyboard.utils.DisplayUtils;
import com.yaowang.myemotionkeyboard.utils.EmotionUtils;
import com.yaowang.myemotionkeyboard.utils.GlobalOnItemClickManagerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.yaowang.myemotionkeyboard.fragment
 * @createTime 创建时间 ：2019/5/23
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class FunctionComplateFragment extends BaseFragment {
    private EmotionPagerAdapter emotionPagerGvAdapter;
    private ViewPager vp_complate_emotion_layout;
    private EmojiIndicatorView ll_point_group;//表情面板对应的点列表
    private int emotion_map_type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_complate_emotion, container, false);
        initView(rootView);
        initListener();
        return rootView;
    }


    /**
     * 初始化view控件
     */
    protected void initView(View rootView) {
        vp_complate_emotion_layout = rootView.findViewById(R.id.vp_complate_emotion_layout);
        ll_point_group = rootView.findViewById(R.id.ll_point_group);
        //获取map的类型
        emotion_map_type = args.getInt(FragmentFactory.EMOTION_MAP_TYPE);
        initFunction();
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {
        vp_complate_emotion_layout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ll_point_group.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initFunction() {
        //两边间距14，中间间距0
        int leftSpace = DisplayUtils.dp2px(getActivity(), 20);
        // 获取屏幕宽度
        int screenWidth = DisplayUtils.getScreenWidthPixels(getActivity());
        // 动态计算item的宽度和高度
        int itemWidth = (screenWidth - leftSpace * 2) / 4;
        // 动态计算gridview的总高度
        int gvHeight = itemWidth * 2 + leftSpace/2;

        List<GridView> functionViews = new ArrayList<>();
        List<String> functionNames = new ArrayList<>();

        // 遍历所有的表情的key
        for (String functionName : EmotionUtils.getEmojiMap(emotion_map_type).keySet()) {
            functionNames.add(functionName);
            // 每8个功能作为一组,同时添加到ViewPager对应的view集合中
            if (functionNames.size() == 8) {
                GridView gv = createFunctionGridView(functionNames, screenWidth, leftSpace, itemWidth, gvHeight);
                functionViews.add(gv);
                // 添加完一组功能,重新创建一个功能名字集合
                functionNames = new ArrayList<>();
            }
        }

        // 判断最后是否有不足8个表情的剩余情况
        if (functionNames.size() > 0) {
            GridView gv = createFunctionGridView(functionNames, screenWidth, leftSpace, itemWidth, gvHeight);
            functionViews.add(gv);
        }

        //初始化指示器
        ll_point_group.initIndicator(functionViews.size());
        // 将多个GridView添加显示到ViewPager中
        emotionPagerGvAdapter = new EmotionPagerAdapter(functionViews);
        vp_complate_emotion_layout.setAdapter(emotionPagerGvAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        vp_complate_emotion_layout.setLayoutParams(params);
    }

    private GridView createFunctionGridView(List<String> functionNames, int screenWidth, int leftSpace, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(getActivity());
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        //设置7列
        gv.setNumColumns(4);
        gv.setPadding(leftSpace, 0, leftSpace, 0);
        gv.setHorizontalSpacing(0);
        gv.setVerticalSpacing(leftSpace/2);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth, gvHeight);
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        FunctionGridViewAdapter adapter = new FunctionGridViewAdapter(getActivity(), functionNames, itemWidth,emotion_map_type);
        gv.setAdapter(adapter);
        //设置全局点击事件
        gv.setOnItemClickListener(GlobalOnItemClickManagerUtils.getInstance(getActivity()).getOnItemClickListener(emotion_map_type));
        return gv;
    }
}
