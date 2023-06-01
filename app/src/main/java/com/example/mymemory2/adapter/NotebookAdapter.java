package com.example.mymemory2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mymemory2.NotebookActivity;
import com.example.mymemory2.R;
import com.example.mymemory2.Bean.*;

import java.util.List;

public class NotebookAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<NotebookBean> list;
    private ViewHolder viewHolder;

    public NotebookAdapter(NotebookActivity notebookActivity, List<NotebookBean> list) {
        layoutInflater = LayoutInflater.from(notebookActivity);
        this.list = list;
    }

    @Override
    public int getCount() {
        // 返回列表中的项数
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        // 返回指定位置的项
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        // 返回指定位置的项的ID
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            // 如果视图为空，则加载项的布局
            view = layoutInflater.inflate(R.layout.notebook_item_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 获取当前位置的NotebookBean对象
        NotebookBean notebookBean = (NotebookBean) getItem(i);

        // 使用ViewHolder设置项的内容和时间
        viewHolder.tv_item_content.setText(notebookBean.getNotebookContent());
        viewHolder.tv_item_time.setText(notebookBean.getNotebookTime());
        return view;
    }

    class ViewHolder {

        private final TextView tv_item_content;
        private final TextView tv_item_time;

        public ViewHolder(View view) {
            // 笔记内容
            tv_item_content = view.findViewById(R.id.item_content);
            // 创建时间
            tv_item_time = view.findViewById(R.id.item_time);
        }
    }
}
