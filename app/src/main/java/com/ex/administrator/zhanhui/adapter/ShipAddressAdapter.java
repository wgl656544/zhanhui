package com.ex.administrator.zhanhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.entity.ShipAddressBean;
import com.ex.administrator.zhanhui.model.UserModel;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class ShipAddressAdapter extends BaseAdapter {
    private ShipAddressBean data;
    private Context context;
    private LayoutInflater inflater;
    public boolean isEditor = false;
    private UserModel model;


    public ShipAddressAdapter(ShipAddressBean data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        model = new UserModel();
    }

    @Override
    public int getCount() {
        return data.getData().size();
    }

    @Override
    public ShipAddressBean.Data getItem(int position) {
        return data.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_ship_address_list, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_ship_address_name);
            holder.tvStreet = (TextView) convertView.findViewById(R.id.tv_ship_address_street);
            holder.ivSelectorDelete = (ImageView) convertView.findViewById(R.id.iv_ship_address_selector);
            holder.ivDefault = (ImageView) convertView.findViewById(R.id.iv_ship_address_default);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ShipAddressBean.Data bean = data.getData().get(position);
        if (bean.getAddress() != null) {
            holder.tvName.setText("收货人:" + bean.getAddress().getContactUserName() + "");
            holder.tvStreet.setText("地址:" + bean.getAddress().getProvince() + " "
                    + bean.getAddress().getCity() + " "
                    + bean.getAddress().getArea() + " "
                    + bean.getAddress().getStreet() + " "
                    + bean.getAddress().getContactTel());
            if (bean.getTagType() == 1) {
                holder.ivDefault.setImageResource(R.drawable.ship_selector);
            }
        }
        if (isEditor) {
            holder.ivSelectorDelete.setVisibility(View.VISIBLE);
            holder.ivDefault.setVisibility(View.GONE);
        } else {
            holder.ivSelectorDelete.setVisibility(View.GONE);
            holder.ivDefault.setVisibility(View.VISIBLE);
        }
        holder.ivSelectorDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDeteleClickListener != null) {
                    mOnDeteleClickListener.onClick(bean.getId() + "");
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView tvName;
        private TextView tvStreet;
        private ImageView ivSelectorDelete;
        private ImageView ivDefault;
    }

    private onDeteleClickListener mOnDeteleClickListener;

    public interface onDeteleClickListener {
        void onClick(String id);
    }

    public void setOnDeteleClickListener(onDeteleClickListener mOnDeteleClickListener) {
        this.mOnDeteleClickListener = mOnDeteleClickListener;
    }

}
