//System for control of a character
//Created by James Vanderhyde, 24 July 2019

package us.vanderhyde.wtfg;

import java.util.Collection;
import java.util.Map;
import javafx.scene.shape.Rectangle;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;
import us.vanderhyde.wtfg.FightingGame.ComponentType;

public class ControlSystem
{
    public static void update(Game<ComponentType> g, Collection<String> input)
    {
        //Update physics based on user input
        for (Map.Entry<Entity,Object> e:g.getEntities(ComponentType.playerControl))
        {
            PlayerControlComponent control = (PlayerControlComponent)e.getValue();
            
            //Move the rectangle
            Rectangle r = (Rectangle)g.getComponent(e.getKey(), ComponentType.hitbox);
            if (r != null)
            {
                if (input.contains(control.left))
                    r.setX(r.getX()-2);
                if (input.contains(control.right))
                    r.setX(r.getX()+2);
            }
            
            //Add attack controls
            CombatPoseComponent pose = (CombatPoseComponent)g.getComponent(e.getKey(), ComponentType.combatPose);
            if (pose != null)
            {
                pose.setInput(input.contains(control.left), input.contains(control.right), input.contains(control.attack), input.contains(control.flip));
            }
        }  
        
        //Update physics based on AI control
        for (Map.Entry<Entity,Object> e:g.getEntities(ComponentType.aiControl))
        {
            AIControlComponent control = (AIControlComponent)e.getValue();
            Rectangle r = (Rectangle)g.getComponent(e.getKey(), ComponentType.hitbox);
            control.update();
            if (control.getState()==AIControlComponent.State.movingLeft)
                r.setX(r.getX()-2);
            if (control.getState()==AIControlComponent.State.movingRight)
                r.setX(r.getX()+2);
        }
    }
    
}
