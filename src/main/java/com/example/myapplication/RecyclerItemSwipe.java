package com.example.myapplication;

import android.animation.BidirectionalTypeConverter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ToDoAdapter;

public class RecyclerItemSwipe extends ItemTouchHelper.SimpleCallback {
    private ToDoAdapter adapter;
    public RecyclerItemSwipe(ToDoAdapter adapter){
        super(0,ItemTouchHelper.LEFT);
        this.adapter=adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder,int direction){
        final int position=viewHolder.getAdapterPosition();
        if(direction==ItemTouchHelper.LEFT){
            AlertDialog.Builder builder=new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete taks");
            builder.setMessage("Are you sure?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.deleteItem(position);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY,int actionState, boolean isCurrentlyActive  ){
        super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
        Drawable icon = null;
        ColorDrawable background;
        View itemView=viewHolder.itemView;
        int baclgroundCornerOffset=20;

        if(dX<0){
            icon= ContextCompat.getDrawable(adapter.getContext(),R.drawable.ic_baseline_delete);
            background=new ColorDrawable(Color.BLUE);
        }
        else{
            background=new ColorDrawable(Color.BLUE);
        }
        int iconMargin=(itemView.getHeight() - icon.getIntrinsicHeight())/2;
        int iconTop=itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight())/2;
        int iconBottom=iconTop + icon.getIntrinsicHeight();

        if(dX<0){
            int iconLeft=itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - baclgroundCornerOffset,itemView.getTop(),
                    itemView.getRight(),itemView.getBottom());
        }
        else
        {
            background.setBounds(0,0,0,0);
        }

        background.draw(c);
        icon.draw(c);
    }
}
