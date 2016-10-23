package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fernando.alert.R;

import java.util.List;

import model.Contato;

/**
 * Created by fernando on 23/10/16.
 */

public class ContatoListAdapter extends ArrayAdapter<Contato> {
    private Context context;
    private int layout;
    private List<Contato> contatos;


    public ContatoListAdapter(Context context, int layout, List<Contato> contatos) {
        super(context, layout,contatos);
        this.context = context;
        this.layout = layout;
        this.contatos = contatos;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(layout,null);

        TextView tvNome = (TextView)view.findViewById(R.id.list_item_contato_tv_nome);
        TextView tvTelefone = (TextView) view.findViewById(R.id.lint_item_contato_tv_telefone);

        Contato contato = contatos.get(position);
        tvNome.setText(contato.getNome());
        tvTelefone.setText(contato.getTelefone());

        return view;
    }
}
