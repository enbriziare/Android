package com.android2019.spaceinvaderchinesebootleg.Data;

import android.content.Context;
import com.android2019.spaceinvaderchinesebootleg.Metier.Score;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SaveIntoFile extends Save {

    private Context context;
    private String filename;

    public SaveIntoFile(Context context, String filename, List<Score> scores){
        this.context = context;
        this.filename = filename;
    }

    public void Serialize(String path, List<Score> scores){
        FileOutputStream outputStream;
        StringBuilder sb = new StringBuilder();
        for(Score s : scores){
            sb.append(s.getName());
            sb.append(":");
            sb.append(s.getNumber());
            sb.append("/");
        }
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(sb.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Score> Deserialize(){
        List<Score> scores = new ArrayList<>();

        if(readFile() == null || readFile().length() == 0) return null;

        String[] firstSplit = readFile().split("/"); //On split la sortie selon le délimiteur ' / '
        for(String sc : firstSplit){
            String[] parts = sc.split(":");
            scores.add(new Score(parts[0],Integer.parseInt(parts[1])));
        }
        return scores;
    }

    private String readFile(){
        try{
            FileInputStream fileInputStream = context.openFileInput(filename);
            BufferedReader br = new BufferedReader( new InputStreamReader(fileInputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while(( line = br.readLine()) != null ) {
                sb.append( line );
            }
            fileInputStream.close();
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
