package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import model.Usuario;

/**
 * Created by fernando on 25/09/15.
 */
public class UsuarioListAdapter extends ArrayAdapter<Usuario> {
    Context context;
    int layout;
    List<Usuario> usuarios;

    TextView tvNome;

    public UsuarioListAdapter(Context context, int layout, List<Usuario> usuarios){
        super(context,layout,usuarios);
        this.context = context;
        this.layout = layout;
        this.usuarios = usuarios;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(layout,null);


        Usuario usuario = usuarios.get(position);
        tvNome.setText(usuario.getNome());

        return view;
    }
}
