//System for control of a character
//Created by James Vanderhyde, 24 July 2019

package us.vanderhyde.wtfg;

import java.util.Collection;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;

public class ControlSystem
{
    public static void update(Game g, Collection<String> input)
    {
        //Update commands based on user input
        for (Entity e:g.getEntities(PlayerControlComponent.class))
        {
            PlayerControlComponent control = g.get(e,PlayerControlComponent.class);
            
            //Add movement commands
            g.add(e, new MovementInputComponent(input.contains(control.left), input.contains(control.right)));
            
            //Add attack commands
            CombatPoseComponent pose = g.get(e,CombatPoseComponent.class);
            if (pose != null)
            {
                boolean canAttack = false;
                boolean canFlip = false;
                if (pose.pose==CombatSystem.Pose.block)
                {
                    ButtonReleaseComponent rel = g.get(e,ButtonReleaseComponent.class);
                    if (rel != null)
                    {
                        if ((rel.attackReleased) && (input.contains(control.attack)))
                            canAttack = true;
                        if ((rel.flipReleased) && (input.contains(control.flip)))
                            canFlip = true;
                    }
                    g.add(e, new ButtonReleaseComponent(!input.contains(control.attack),!input.contains(control.flip)));
                }
                else
                    g.remove(e, ButtonReleaseComponent.class);
                g.add(e, new CombatInputComponent(canAttack, canFlip));
            }
        }  
        
        //Update commands based on AI control
        for (Entity e:g.getEntities(AIControlComponent.class))
        {
            AIControlComponent control = g.get(e,AIControlComponent.class);
            control.update();
            g.add(e, new MovementInputComponent(control.getState()==AIControlComponent.State.movingLeft, control.getState()==AIControlComponent.State.movingRight));
        }
    }
    
}
