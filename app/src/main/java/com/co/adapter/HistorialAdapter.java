package com.co.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.co.model.Registro;
import com.co.picoplaca.R;

import java.util.ArrayList;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> implements View.OnClickListener
{
    public ArrayList<Registro> historiales;
    private View.OnClickListener listener;

    public HistorialAdapter(ArrayList<Registro> historiales)
    {
        this.historiales = historiales;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_historial, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        viewHolder.txtPlaca.setText(historiales.get(i).getPlaca());
        viewHolder.txtCon.setText(historiales.get(i).getContravencion());
        viewHolder.txtFecha.setText(historiales.get(i).getFache());
    }

    @Override
    public int getItemCount()
    {
        return historiales.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v)
    {
        if (listener != null)
            listener.onClick(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtPlaca, txtCon, txtFecha;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtPlaca = itemView.findViewById(R.id.txtPlaca);
            txtCon = itemView.findViewById(R.id.txtCon);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }
}
