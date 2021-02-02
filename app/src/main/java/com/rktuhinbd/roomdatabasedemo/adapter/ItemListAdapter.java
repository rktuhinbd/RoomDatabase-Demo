package com.rktuhinbd.roomdatabasedemo.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rktuhinbd.roomdatabasedemo.R;
import com.rktuhinbd.roomdatabasedemo.database.MyData;
import com.rktuhinbd.roomdatabasedemo.database.RoomDB;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private Context context;
    private RoomDB roomDB;
    private List<MyData> myDataList;

    public ItemListAdapter(Context context, List<MyData> myDataList) {
        this.context = context;
        this.myDataList = myDataList;
        notifyDataSetChanged();
    }

    private OnRecyclerItemClickListener onItemClickListener;

    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClickListener(Boolean status, MyData account);
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyData myData = myDataList.get(position);

        roomDB = RoomDB.getInstance(context);

        holder.textView.setText(myData.getText());

        holder.ibEdit.setOnClickListener(v -> {

            int updateID = myData.getID();
            String updateText = myData.getText();

            // = = = Create custom dialog to get input for data update = = = //

            // - - - Defining dialog height, width and show dialog - - - //
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_update);
            int dialogWidth = WindowManager.LayoutParams.MATCH_PARENT;
            int dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(dialogWidth, dialogHeight);
            dialog.show();

            // - - - Initialize and assign view properties of dialog - - - //
            EditText editText = dialog.findViewById(R.id.editText);
            Button buttonUpdate = dialog.findViewById(R.id.button_update);

            // - - - Set existing text to dialog's editText - - - //
            editText.setText(updateText);

            // - - - Update data on button click - - - //
            buttonUpdate.setOnClickListener(v1 -> {
                String updatedText = editText.getText().toString();
                roomDB.dao().update(updateID, updatedText);
                myDataList.clear();
                myDataList.addAll(roomDB.dao().getAllData());
                notifyDataSetChanged();
                dialog.dismiss();
            });
        });

        // = = =  Delete data = = = //
        holder.ibDelete.setOnClickListener(v -> {
            roomDB.dao().delete(myData);
            myDataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, myDataList.size());
        });

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return myDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageButton ibEdit;
        ImageButton ibDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_text);
            ibEdit = itemView.findViewById(R.id.ib_edit);
            ibDelete = itemView.findViewById(R.id.ib_delete);

        }
    }
}
