package fct.sreolid.appbiblioteca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by sreolid on 31/03/2016.
 */
public abstract class Adaptador extends BaseAdapter {

    private Context context;
    private int idLayout;
    private ArrayList<?> datosEntrada;

    public Adaptador(Context context, int idLayout, ArrayList<?> datos){
        super();
        this.context = context;
        this.idLayout = idLayout;
        this.datosEntrada = datos;
    }

    @Override
    public int getCount() {
        return this.datosEntrada.size();
    }

    @Override
    public Object getItem(int position) {
        return this.datosEntrada.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(idLayout, null);
        }

        onEntrada (datosEntrada.get(position), convertView);
        return convertView;
    }

    public abstract void onEntrada(Object objeto, View v);
}
