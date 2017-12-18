package unicauca.movil.organizadores.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import unicauca.movil.organizadores.R;
import unicauca.movil.organizadores.databinding.TemplateButtonBinding;
import unicauca.movil.organizadores.models.Boton;

/**
 * Created by RicardoM on 17/12/2017.
 */

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    public interface OnButtonListener{
        void onButton(int position);
    }

    LayoutInflater inflater;
   OnButtonListener onButtonListener;
    List<Boton> data;

    public ButtonAdapter(LayoutInflater inflater, List<Boton> data, OnButtonListener onButtonListener) {
        this.onButtonListener = onButtonListener;
        this.data = data;
        this.inflater = inflater;
    }

    @Override
    public ButtonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = inflater.inflate(R.layout.template_button, parent, false);
       ButtonViewHolder holder = new ButtonViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ButtonViewHolder holder, int position) {
        holder.binding.setBut(data.get(position));
        holder.binding.card.setTag(position);
        holder.binding.setHandler(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void onClick(int position){
        onButtonListener.onButton(position);
    }


    public static class ButtonViewHolder extends RecyclerView.ViewHolder{

        TemplateButtonBinding binding;

        public ButtonViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

        }
    }

}
