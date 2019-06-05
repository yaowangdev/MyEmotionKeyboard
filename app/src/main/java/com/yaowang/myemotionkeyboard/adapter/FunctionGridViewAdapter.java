package com.yaowang.myemotionkeyboard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaowang.myemotionkeyboard.R;
import com.yaowang.myemotionkeyboard.utils.EmotionUtils;

import java.util.List;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.yaowang.myemotionkeyboard.adapter
 * @createTime 创建时间 ：2019/5/23
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class FunctionGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> functionNames;
    private int itemWidth;
    private int emotion_map_type;
    private LayoutInflater inflater;

    public FunctionGridViewAdapter(Context context, List<String> functionNames, int itemWidth,int emotion_map_type) {
        this.context = context;
        this.functionNames = functionNames;
        this.itemWidth = itemWidth;
        this.emotion_map_type = emotion_map_type;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return functionNames.size();
    }

    @Override
    public Object getItem(int position) {
        return functionNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemWidth, itemWidth);
        View inflate = inflater.inflate(R.layout.item_function, parent,false);
        ImageView image = inflate.findViewById(R.id.iv_image);
        TextView name = inflate.findViewById(R.id.tv_name);
        String emotionName = functionNames.get(position);
        image.setImageResource(EmotionUtils.getImgByName(emotion_map_type,emotionName));
        name.setText(emotionName);
        relativeLayout.addView(inflate,params);
        return relativeLayout;
//        ViewHolder viewHolder;
//        if(convertView == null){
//            convertView = inflater.inflate(R.layout.item_function,parent,false);
//            viewHolder = new ViewHolder();
//            viewHolder.img = convertView.findViewById(R.id.iv_image);
//            viewHolder.tv = convertView.findViewById(R.id.tv_name);
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        String emotionName = functionNames.get(position);
//        viewHolder.img.setImageResource(EmotionUtils.getImgByName(emotion_map_type,emotionName));
//        viewHolder.tv.setText(emotionName);
//        return convertView;
    }

    class ViewHolder{
        ImageView img;
        TextView tv;
    }
}
