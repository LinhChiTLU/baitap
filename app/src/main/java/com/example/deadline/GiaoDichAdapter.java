package com.example.deadline;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GiaoDichAdapter extends RecyclerView.Adapter<GiaoDichAdapter.ViewHolder> {

    private final List<Transaction> giaoDichList;
    private final OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEdit(int position);
        void onDelete(int position);
    }

    public GiaoDichAdapter(List<Transaction> giaoDichList, OnItemActionListener listener) {
        this.giaoDichList = giaoDichList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GiaoDichAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giaodich, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiaoDichAdapter.ViewHolder holder, int position) {
        Transaction giaoDich = giaoDichList.get(position);

        holder.tvTitle.setText(giaoDich.getName());
        holder.tvDate.setText(giaoDich.getDate());

        // Định dạng số tiền và đổi màu
        String amountFormatted = NumberFormat.getNumberInstance(Locale.US)
                .format(Math.abs(giaoDich.getAmount())) + " VND";
        boolean isExpense = "expense".equals(giaoDich.getType());
        holder.tvAmount.setText((isExpense ? "- " : "+ ") + amountFormatted);
        holder.tvAmount.setTextColor(isExpense ? Color.RED : Color.parseColor("#43A047"));

        // Lấy icon từ tên icon lưu trong Firebase
        int iconResId = holder.itemView.getContext()
                .getResources()
                .getIdentifier(giaoDich.getIcon(), "drawable", holder.itemView.getContext().getPackageName());
        if (iconResId != 0) {
            holder.imgIcon.setImageResource(iconResId);
        } else {
            holder.imgIcon.setImageResource(R.drawable.ic_default);
        }

        // Hiển thị PopupMenu cho mỗi item
        holder.imgMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), holder.imgMenu);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_giaodich, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_edit) {
                    listener.onEdit(holder.getAdapterPosition());
                    return true;
                } else if (item.getItemId() == R.id.action_delete) {
                    listener.onDelete(holder.getAdapterPosition());
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return giaoDichList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvAmount;
        ImageView imgMenu, imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            imgMenu = itemView.findViewById(R.id.imgMenu);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
