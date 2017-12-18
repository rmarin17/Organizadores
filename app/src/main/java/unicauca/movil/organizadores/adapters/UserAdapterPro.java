package unicauca.movil.organizadores.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import unicauca.movil.organizadores.R;
import unicauca.movil.organizadores.databinding.TemplateUserBinding;
import unicauca.movil.organizadores.models.UserRequest;
import unicauca.movil.organizadores.util.L;

/**
 * Created by RicardoM on 04/08/2017.
 */

public class UserAdapterPro extends RecyclerView.Adapter<UserAdapterPro.UserViewHolder> {


    public interface OnUserListener{
        void onUser(View v);
    }

    LayoutInflater inflater;
    OnUserListener onUserListener;
    List<UserRequest> data;

    public UserAdapterPro(LayoutInflater inflater, List<UserRequest> data, OnUserListener onUserListener) {
        this.onUserListener = onUserListener;
        this.data = data;
        this.inflater = inflater;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = inflater.inflate(R.layout.template_user, parent, false);
        UserViewHolder holder = new UserViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.binding.setUser(data.get(position));
        holder.binding.card.setTag(position);
        holder.binding.setHandler(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void onClick(View v){
        onUserListener.onUser(v);
    }

    //Retorna el tipo de view o Celda
    /*@Override
    public int getItemViewType(int position) { return 0;}*/

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        TemplateUserBinding binding;

        public UserViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

        }
    }
}
