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
        for (Entity e:g.getEntities(MovementInputComponent.class))
        {
            MovementInputComponent c = g.get(e,MovementInputComponent.class);
            //Move the rectangle
            Rectangle r = g.get(e,Rectangle.class);
            if (r != null)
            {
                if (c.left && !c.right)
                {
                    r.setX(r.getX()-2);
                    g.add(e, new FacingDirection(Facing.left));
                }
                if (c.right && !c.left)
                {
                    r.setX(r.getX()+2);
                    g.add(e, new FacingDirection(Facing.right));
                }
            }
        }
    }
}
