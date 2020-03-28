package com.android2019.spaceinvaderchinesebootleg.Data;

import com.android2019.spaceinvaderchinesebootleg.Metier.Score;

import java.util.List;

public abstract class Save {

    public abstract void Serialize(String path,List<Score> scores);
    public abstract List<Score> Deserialize();
}
