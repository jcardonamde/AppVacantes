package co.edu.upb.android.datos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.edu.upb.android.R;
import co.edu.upb.android.modelo.Empresa;

public class AdapterEmpresas extends RecyclerView.Adapter<AdapterEmpresas.ViewHolder> {
    //lista de empresas
    private ArrayList<Empresa> mDatos;

    //El viewHolder
    public AdapterEmpresas(ArrayList<Empresa> myDataSet) {
        mDatos = myDataSet;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView razon,nit,direccion;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            razon = view.findViewById(R.id.item_razon);
            nit = view.findViewById(R.id.item_nit);
            direccion = view.findViewById(R.id.item_direccion);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.razon.setText(mDatos.get(position).getRazon());
        String nom = mDatos.get(position).getRazon();
        holder.nit.setText(mDatos.get(position).getNit());
        holder.direccion.setText(mDatos.get(position).getDireccion());
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }
}
