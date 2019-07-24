//System for control of a character
//Created by James Vanderhyde, 24 July 2019

package us.vanderhyde.wtfg;

import java.util.Collection;
import javafx.scene.shape.Rectangle;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;
import us.vanderhyde.wtfg.FightingGame.Component;

public class ControlSystem
{
    public static void update(Game<Component> g, Collection<String> input)
    {
        //Update physics based on user input
        for (Entity e:g.getEntities(Component.playerControl))
        {
            Rectangle r = (Rectangle)g.getComponent(e, Component.hitbox);
            PlayerControlComponent control = (PlayerControlComponent)g.getComponent(e, Component.playerControl);
            if (input.contains(control.left))
                r.setX(r.getX()-2);
            if (input.contains(control.right))
                r.setX(r.getX()+2);
        }  
        
        //Update physics based on AI control
        for (Entity e:g.getEntities(Component.aiControl))
        {
        }        
    }
    
}
