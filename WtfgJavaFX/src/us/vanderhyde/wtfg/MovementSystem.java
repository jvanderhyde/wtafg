//World's tiniest fighting game movement system
//Created by James Vanderhyde, 15 May 2021

package us.vanderhyde.wtfg;

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
            FighterPosition position = g.get(e, FighterPosition.class);
            if (position != null && f != null)
            {
                double dir = 1;
                if (((p.pose==CombatSystem.Pose.walkForward || p.pose==CombatSystem.Pose.attackedFromBehind) && 
                     f.direction==MovementSystem.Facing.left) || 
                    ((p.pose==CombatSystem.Pose.walkBackward || p.pose==CombatSystem.Pose.attackedFromFront) && 
                     f.direction==MovementSystem.Facing.right))
                    dir = -1;
                if (p.pose==CombatSystem.Pose.walkForward ||
                    p.pose==CombatSystem.Pose.attackedFromBehind ||
                    p.pose==CombatSystem.Pose.attackedFromFront)
                {
                    g.add(e, new FighterPosition(position.x+2*dir));
                }
                else if (p.pose==CombatSystem.Pose.walkBackward)
                {
                    g.add(e, new FighterPosition(position.x+1*dir));
                }
            }
        }
    }
}
