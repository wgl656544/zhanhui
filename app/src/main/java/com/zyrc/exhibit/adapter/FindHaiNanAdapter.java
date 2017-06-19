package com.zyrc.exhibit.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zyrc.exhibit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class FindHaiNanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_HEADER_ONE = 0;
    private static final int TYPE_HEADER_TWO = 1;
    private static final int TYPE_NORMAL = 2;


    private Context context;
    private List<String> datas;
    private RecyclerView.ViewHolder viewHolder;
    private List<Button> buttons;
    private int btnClickIndex;
    private int btnCurrentIndex = 0;

    public FindHaiNanAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_HEADER_ONE) {
            view = LayoutInflater.from(context).inflate(R.layout.item_find_hainan_advert, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == TYPE_HEADER_TWO) {
            view = LayoutInflater.from(context).inflate(R.layout.item_find_hainan_button_channel, parent, false);
            return new ButtonViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_find_hainan_normal, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (position == 1) {
            this.viewHolder = holder;
            ((ButtonViewHolder) holder).btnFindHNChoiceness.setOnClickListener(this);
            ((ButtonViewHolder) holder).btnFindHNTravel.setOnClickListener(this);
            ((ButtonViewHolder) holder).btnFindHNCate.setOnClickListener(this);
            ((ButtonViewHolder) holder).btnFindHNScenery.setOnClickListener(this);
            ((ButtonViewHolder) holder).btnFindHNRelaxation.setOnClickListener(this);
        }
        if (position > 1) {
            ((MyViewHolder) holder).textView.setText(datas.get(position - 2));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER_ONE;
        } else if (position == 1) {
            return TYPE_HEADER_TWO;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_find_hn_choiceness:
                btnClickIndex = 0;
                break;

            case R.id.btn_find_hn_travel:
                btnClickIndex = 1;
                break;

            case R.id.btn_find_hn_cate:
                btnClickIndex = 2;
                break;

            case R.id.btn_find_hn_scenery:
                btnClickIndex = 3;
                break;

            case R.id.btn_find_hn_relaxation:
                btnClickIndex = 4;
                break;

        }
        if (btnClickIndex != btnCurrentIndex) {
            buttons.get(btnClickIndex).setBackgroundResource(R.drawable.bg_orange);
            buttons.get(btnCurrentIndex).setBackgroundColor(ContextCompat.getColor(context, R.color.color_white_1));
            btnCurrentIndex = btnClickIndex;
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_find_hn);

        }
    }


    class ButtonViewHolder extends RecyclerView.ViewHolder {
        private Button btnFindHNChoiceness;
        private Button btnFindHNTravel;
        private Button btnFindHNCate;
        private Button btnFindHNScenery;
        private Button btnFindHNRelaxation;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            btnFindHNChoiceness = (Button) itemView.findViewById(R.id.btn_find_hn_choiceness);
            btnFindHNTravel = (Button) itemView.findViewById(R.id.btn_find_hn_travel);
            btnFindHNCate = (Button) itemView.findViewById(R.id.btn_find_hn_cate);
            btnFindHNScenery = (Button) itemView.findViewById(R.id.btn_find_hn_scenery);
            btnFindHNRelaxation = (Button) itemView.findViewById(R.id.btn_find_hn_relaxation);
            buttons = new ArrayList<>();
            buttons.add(btnFindHNChoiceness);
            buttons.add(btnFindHNTravel);
            buttons.add(btnFindHNCate);
            buttons.add(btnFindHNScenery);
            buttons.add(btnFindHNRelaxation);
        }
    }

}
