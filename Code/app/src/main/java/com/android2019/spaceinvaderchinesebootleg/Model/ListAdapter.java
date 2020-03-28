package com.android2019.spaceinvaderchinesebootleg.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android2019.spaceinvaderchinesebootleg.Metier.Score;
import com.android2019.spaceinvaderchinesebootleg.R;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Score> {

    private int res;

    public ListAdapter(Context context, int ressourceId, List<Score> scores) {
        super(context, ressourceId, scores);
        res = ressourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(res,parent,false); //inflate le menu in Game

        TextView tv =  v.findViewById(R.id.score_name);
        tv.setText(getItem(position).getName());
        tv = v.findViewById(R.id.score_number);
        tv.setText(String.valueOf(getItem(position).getNumber()));

        return v;
    }
}
