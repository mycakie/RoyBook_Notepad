package com.example.mymemory2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mymemory2.Bean.NotebookBean;
import com.example.mymemory2.Bean.Tally;
import com.example.mymemory2.ManageActivity;
import com.example.mymemory2.R;
import com.example.mymemory2.SearchRecordActivity;

import java.text.BreakIterator;
import java.util.List;

public class TallyAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Tally> list;
    private ViewHolder viewHolder;

    public TallyAdapter(ManageActivity manageActivity, List<Tally> list){
        layoutInflater = LayoutInflater.from(manageActivity);
        this.list = list;
    }
    public TallyAdapter(SearchRecordActivity searchRecordActivity, List<Tally> list){
        layoutInflater = LayoutInflater.from(searchRecordActivity);
        this.list = list;
    }

    @Override
    public int getCount() {
        // 返回列表中的项数
        return list == null? 0: list.size();
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
        if(view == null){
            // 如果视图为空，则加载项的布局
            view = layoutInflater.inflate(R.layout.record_item_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 获取当前位置的 Tally 对象
        Tally tally = (Tally) getItem(i);

        // 使用 ViewHolder 设置项的日期、类型、金额和状态
        viewHolder.list_date.setText(tally.getTallyTime());
        viewHolder.list_type.setText(tally.getTallyType());
        viewHolder.list_money.setText(String.valueOf(tally.getTallyMoney()));
        viewHolder.list_state.setText(tally.getTallyState());
        return view;
    }

    class ViewHolder{
        private final TextView list_date;
        private final TextView list_type;
        private final TextView list_money;
        private final TextView list_state;

        public ViewHolder(@NonNull View view){
            // 日期
            list_date = view.findViewById(R.id.list_date);
            // 类型
            list_type = view.findViewById(R.id.list_type);
            // 金额
            list_money = view.findViewById(R.id.list_money);
            // 状态
            list_state = view.findViewById(R.id.list_state);
        }
    }
}
