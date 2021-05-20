//Keeps track of player scores
//Created by James Vanderhyde, 19 May 2021

package us.vanderhyde.wtfg;

import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;

public class ScoreSystem
{
    public static void update(Game g)
    {
        //Update the score when points are scored in combat
        for (Entity e:g.getEntities(PointComponent.class))
        {
            PointComponent p = g.get(e,PointComponent.class);
            ScoreComponent score = g.get(e,ScoreComponent.class);
            if (score != null)
                g.add(e, new ScoreComponent(score.score+p.numPointsScored));
            g.remove(e, PointComponent.class);
        }
    }
}
