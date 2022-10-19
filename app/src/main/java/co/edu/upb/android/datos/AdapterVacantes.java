package co.edu.upb.android.datos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.edu.upb.android.R;
import co.edu.upb.android.modelo.Vacantes;

public class AdapterVacantes extends RecyclerView.Adapter<AdapterVacantes.ViewHolder> {
    private ArrayList<Vacantes> mDatos; //lista de vacantes

    //El viewHolder
    public AdapterVacantes(ArrayList<Vacantes> myDataSet) {
        mDatos = myDataSet;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, detalle, profesiones_asociadas, salario;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            nombre = view.findViewById(R.id.item_nombre);
            detalle = view.findViewById(R.id.item_detalle);
            profesiones_asociadas = view.findViewById(R.id.item_profesiones_asociadas);
            salario = view.findViewById(R.id.item_salario);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listavacantes, parent, false);
        return new ViewHolder(view);
    }

    @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.nombre.setText(mDatos.get(position).getNombre());
            holder.detalle.setText(mDatos.get(position).getDetalle());
            holder.profesiones_asociadas.setText(mDatos.get(position).getProfesiones_asociadas());
            holder.salario.setText(mDatos.get(position).getSalario());
        }

        @Override
        public int getItemCount() {
            return mDatos.size();
        }
}


//String nom = mDatos.get(position).getNombre();