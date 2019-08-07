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
        //Set up mappers for components we will use
        Game<ComponentType>.CompMap<Rectangle> hm;
        hm = g.new CompMap<>(ComponentType.hitbox,Rectangle.class);
        Game<ComponentType>.CompMap<CombatPoseComponent> cm;
        cm = g.new CompMap<>(ComponentType.combatPose,CombatPoseComponent.class);
        Game<ComponentType>.CompMap<PlayerControlComponent> pm;
        pm = g.new CompMap<>(ComponentType.playerControl,PlayerControlComponent.class);
        Game<ComponentType>.CompMap<AIControlComponent> am;
        am = g.new CompMap<>(ComponentType.aiControl,AIControlComponent.class);
        
        //Update physics based on user input
        for (Entity e:pm.getEntities())
        {
            PlayerControlComponent control = pm.get(e);
            
            //Move the rectangle
            Rectangle r = hm.get(e);
            if (r != null)
            {
                if (input.contains(control.left))
                    r.setX(r.getX()-2);
                if (input.contains(control.right))
                    r.setX(r.getX()+2);
            }
            
            //Add attack controls
            CombatPoseComponent pose = cm.get(e);
            if (pose != null)
            {
                pose.setInput(input.contains(control.left), input.contains(control.right), input.contains(control.attack), input.contains(control.flip));
            }
        }  
        
        //Update physics based on AI control
        for (Entity e:am.getEntities())
        {
            AIControlComponent control = am.get(e);
            Rectangle r = hm.get(e);
            control.update();
            if (control.getState()==AIControlComponent.State.movingLeft)
                r.setX(r.getX()-2);
            if (control.getState()==AIControlComponent.State.movingRight)
                r.setX(r.getX()+2);
        }
    }
    
}
