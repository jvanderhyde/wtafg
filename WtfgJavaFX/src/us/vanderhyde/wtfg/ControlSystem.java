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
            PlayerControlComponent control = (PlayerControlComponent)g.getComponent(e, Component.playerControl);
            
            //Move the rectangle
            Rectangle r = (Rectangle)g.getComponent(e, Component.hitbox);
            if (r != null)
            {
                if (input.contains(control.left))
                    r.setX(r.getX()-2);
                if (input.contains(control.right))
                    r.setX(r.getX()+2);
            }
            
            //Add attack controls
            CombatPoseComponent pose = (CombatPoseComponent)g.getComponent(e, Component.combatPose);
            if (pose != null)
            {
                pose.setInput(input.contains(control.left), input.contains(control.right), input.contains(control.attack), input.contains(control.flip));
            }
        }  
        
        //Update physics based on AI control
        for (Entity e:g.getEntities(Component.aiControl))
        {
            Rectangle r = (Rectangle)g.getComponent(e, Component.hitbox);
            AIControlComponent control = (AIControlComponent)g.getComponent(e, Component.aiControl);
            control.update();
            if (control.getState()==AIControlComponent.State.movingLeft)
                r.setX(r.getX()-2);
            if (control.getState()==AIControlComponent.State.movingRight)
                r.setX(r.getX()+2);
        }
    }
    
}
