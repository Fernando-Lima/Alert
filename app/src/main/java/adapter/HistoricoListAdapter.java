package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fernando.alert.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Usuario;

/**
 * Created by fernando on 23/10/16.
 */

public class HistoricoListAdapter extends ArrayAdapter<Usuario> {
    private Context context;
    private int layout;
    private List<Usuario> usuarios;


    public HistoricoListAdapter(Context context, int layout, List<Usuario> usuarios) {
        super(context, layout,usuarios);
        this.context = context;
        this.layout = layout;
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(layout,null);

        TextView tvNome = (TextView)view.findViewById(R.id.list_item_historico_tv_nome);
        TextView tvData = (TextView) view.findViewById(R.id.list_item_historico_tv_data);
        TextView tvTelefone = (TextView)view.findViewById(R.id.list_item_historico_tv_telefone);

        Usuario usuario = usuarios.get(position);
        tvNome.setText(usuario.getNome());
        tvData.setText(usuario.getData());
        tvTelefone.setText(usuario.getTelefoneContato());


        return view;
    }
}
