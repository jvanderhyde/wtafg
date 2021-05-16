//World's tiniest fighting game movement system
//Created by James Vanderhyde, 15 May 2021

package us.vanderhyde.wtfg;

import javafx.scene.shape.Rectangle;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;

public class MovementSystem
{
    static enum Facing {
        left, right;
    }
    
    public static void update(Game g)
    {
        for (Entity e:g.getEntities(CombatPoseComponent.class))
        {
            CombatPoseComponent p = g.get(e,CombatPoseComponent.class);
            FacingDirection f = g.get(e, FacingDirection.class);
            Rectangle r = g.get(e,Rectangle.class);
            if (r != null && f != null)
            {
                if (p.pose==CombatSystem.Pose.walkForward)
                {
                    //Move the rectangle
                    if (f.direction==MovementSystem.Facing.left)
                        r.setX(r.getX()-2);
                    else
                        r.setX(r.getX()+2);
                }
                else if (p.pose==CombatSystem.Pose.walkBackward)
                {
                    //Move the rectangle
                    if (f.direction==MovementSystem.Facing.right)
                        r.setX(r.getX()-1);
                    else
                        r.setX(r.getX()+1);
                }
            }
        }
    }
}
