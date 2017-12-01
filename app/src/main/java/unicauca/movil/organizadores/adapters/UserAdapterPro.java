package unicauca.movil.organizadores.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unicauca.movil.organizadores.R;
import unicauca.movil.organizadores.databinding.TemplateUserBinding;
import unicauca.movil.organizadores.util.L;

/**
 * Created by RicardoM on 04/08/2017.
 */

public class UserAdapterPro extends RecyclerView.Adapter<UserAdapterPro.UserViewHolder> {


    public interface OnUserListener{
        void onAlarm(View v);
    }

    LayoutInflater inflater;
    OnUserListener onUserListener;

    public UserAdapterPro(LayoutInflater inflater, OnUserListener onUserListener) {
        this.onUserListener = onUserListener;
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
        holder.binding.setUser(L.data.get(position));
        holder.binding.setHandler(this);
    }

    @Override
    public int getItemCount() {
        return L.data.size();
    }

    public void onClickAlarm(View v){
        onUserListener.onAlarm(v);
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
