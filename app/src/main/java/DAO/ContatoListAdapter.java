package DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fernando.alert.R;

import java.util.List;

import model.Contato;

/**
 * Created by fernando on 25/05/15.
 */
public class ContatoListAdapter extends ArrayAdapter<Contato> {
    Context context;
    int layout;
    List<Contato> contatos;

    public ContatoListAdapter(Context context, int layout, List<Contato> contatos){
        super(context,layout,contatos);
        this.context = context;
        this.layout = layout;
        this.contatos = contatos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(layout,null);

        Contato contato = contatos.get(position);

        return view;
    }
}
