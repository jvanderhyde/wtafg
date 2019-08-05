//World's tiniest fighting game combat system
//Created by James Vanderhyde, 6 July 2019

package us.vanderhyde.wtfg;

import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;
import us.vanderhyde.wtfg.FightingGame.Component;

public class CombatSystem
{
    static enum Pose {
        block(1), recoverBlock(3), 
        prepareAttack(2), attack(3), recoverAttack(5),
        prepareThrow(2), doThrow(3), recoverThrow(5),
        thrown(10), blocked(6), attacked(10),
        walk(10), turn(10);
        public final int duration;//in frames
        
        Pose(int duration) {this.duration=duration;}
        
        private Pose onExpire;
        static
        {
            block.onExpire = block;
            recoverBlock.onExpire = block;
            prepareAttack.onExpire = attack;
            attack.onExpire = recoverAttack;
            recoverAttack.onExpire = block;
            prepareThrow.onExpire = doThrow;
            doThrow.onExpire = recoverThrow;
            recoverThrow.onExpire = block;
            thrown.onExpire = block;
            blocked.onExpire = block;
            attacked.onExpire = block;
            walk.onExpire = block;
            turn.onExpire = block;
        }
    };
    
    public static void update(Game<Component> g)
    {
        for (Entity e:g.getEntities(Component.combatPose))
        {
            CombatPoseComponent c = (CombatPoseComponent)g.getComponent(e, Component.combatPose);
            if (c.attack && c.pose==Pose.block)
                g.addComponent(e, Component.combatPose, new CombatPoseComponent(Pose.prepareAttack));
            else
            {
                //Check for expiration of current pose
                c.timeLeft--;
                if (c.timeLeft <= 0)
                    g.addComponent(e, Component.combatPose, new CombatPoseComponent(c.pose.onExpire));
            }
        }
    }
}
