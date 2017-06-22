package com.zyrc.exhibit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.order.OrderContent;
import com.zyrc.exhibit.entity.order.OrderFoot;
import com.zyrc.exhibit.entity.order.OrderHeader;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class AllOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int ITEM_HEADER = 1;
    private int ITEM_CONTENT = 2;
    private int ITEM_FOOT = 3;
    private Context context;
    private List<Object> data;

    public AllOrderAdapter(Context context, List<Object> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_allorder_header, parent, false);
            return new AllOrderAdapter.MyViewHolderHeader(view);
        } else if (viewType == ITEM_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_allorder_content, parent, false);
            return new AllOrderAdapter.MyViewHolderContent(view);
        } else if (viewType == ITEM_FOOT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_allorder_footer, parent, false);
            return new AllOrderAdapter.MyViewHolderFooter(view);
        }
        if (data.size() == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_no_data_layout, parent, false);
            return new AllOrderAdapter.MyViewHolderNoData(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolderHeader) {
            OrderHeader header = (OrderHeader) data.get(position);
            ((MyViewHolderHeader) holder).tvOrderNo.setText("订单号：" + header.getOrderNo() + "");
            ((MyViewHolderHeader) holder).tvOrderShopname.setVisibility(View.GONE);
            if (header.getStatus() >= 2) {
                ((MyViewHolderHeader) holder).tvOrderState.setText("已付款");
            } else {
                ((MyViewHolderHeader) holder).tvOrderState.setText("待付款");
            }
        } else if (holder instanceof MyViewHolderContent) {
            OrderContent content = (OrderContent) data.get(position);
            Glide.with(context).load(content.getProductPic()).into(((MyViewHolderContent) holder).ivPic);
            ((MyViewHolderContent) holder).tvTitle.setText(content.getName());
            ((MyViewHolderContent) holder).tvPrice.setText("￥：" + content.getPrice() + "");
            ((MyViewHolderContent) holder).tvNum.setText("共" + content.getQty() + "张");
        } else if (holder instanceof MyViewHolderFooter) {
            final OrderFoot foot = (OrderFoot) data.get(position);
            if (foot.getStatus() >= 2) {
                ((MyViewHolderFooter) holder).btnSub.setVisibility(View.GONE);
            }
            ((MyViewHolderFooter) holder).tvAmount.setText("￥：" + foot.getAmount() + "");
            ((MyViewHolderFooter) holder).btnSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(foot.getOrderId(), String.valueOf(foot.getAmount()));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (data.size() == 0 ? 1 : data.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (data.size() > 0) {
            if (data.get(position) instanceof OrderHeader) {
                return ITEM_HEADER;
            } else if (data.get(position) instanceof OrderContent) {
                return ITEM_CONTENT;
            } else if (data.get(position) instanceof OrderFoot) {
                return ITEM_FOOT;
            }
        } else {
            return 0;
        }
        return ITEM_CONTENT;
    }

    private class MyViewHolderHeader extends RecyclerView.ViewHolder {
        private TextView tvOrderNo;
        private TextView tvOrderState;
        private TextView tvOrderShopname;


        public MyViewHolderHeader(View itemView) {
            super(itemView);
            tvOrderNo = (TextView) itemView.findViewById(R.id.tv_item_allorder_orderNo);
            tvOrderState = (TextView) itemView.findViewById(R.id.tv_item_allorder_state);
            tvOrderShopname = (TextView) itemView.findViewById(R.id.tv_item_allorder_shopname);
        }
    }

    private class MyViewHolderContent extends RecyclerView.ViewHolder {
        private ImageView ivPic;
        private TextView tvTitle;
        private TextView tvNum;
        private TextView tvPrice;

        public MyViewHolderContent(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_item_allorder_pic);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_allorder_title);
            tvNum = (TextView) itemView.findViewById(R.id.tv_item_allorder_item_num);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_item_allorder_item_price);
        }
    }

    private class MyViewHolderFooter extends RecyclerView.ViewHolder {
        private TextView tvAmount;
        private Button btnSub;

        public MyViewHolderFooter(View itemView) {
            super(itemView);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_item_allorder_total);
            btnSub = (Button) itemView.findViewById(R.id.tv_item_allorder_submit);
        }
    }

    private class MyViewHolderNoData extends RecyclerView.ViewHolder {

        public MyViewHolderNoData(View itemView) {
            super(itemView);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String orderId, String total);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
