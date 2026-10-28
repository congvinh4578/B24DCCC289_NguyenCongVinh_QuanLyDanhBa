package com.example.quanlydanhba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/** Binds a list of Contact objects to the rows of item_contact.xml. */
public class ContactAdapter extends BaseAdapter {

    private final List<Contact> contacts;
    private final LayoutInflater inflater;

    public ContactAdapter(Context context, List<Contact> contacts) {
        this.contacts = contacts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_contact, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = getItem(position);
        holder.tvAvatar.setText(contact.getInitial());
        holder.tvName.setText(contact.getFullName());
        holder.tvPhone.setText(contact.getPhone());
        holder.tvGroup.setText(contact.getGroup());
        // INVISIBLE (not GONE) keeps the group tags aligned across rows
        holder.ivStar.setVisibility(contact.isFavorite() ? View.VISIBLE : View.INVISIBLE);

        return convertView;
    }

    private static class ViewHolder {
        final TextView tvAvatar;
        final TextView tvName;
        final TextView tvPhone;
        final TextView tvGroup;
        final ImageView ivStar;

        ViewHolder(View root) {
            tvAvatar = root.findViewById(R.id.tv_avatar);
            tvName = root.findViewById(R.id.tv_name);
            tvPhone = root.findViewById(R.id.tv_phone);
            tvGroup = root.findViewById(R.id.tv_group);
            ivStar = root.findViewById(R.id.iv_star);
        }
    }
}
