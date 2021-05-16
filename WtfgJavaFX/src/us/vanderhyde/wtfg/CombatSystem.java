//World's tiniest fighting game combat system
//Created by James Vanderhyde, 6 July 2019

package us.vanderhyde.wtfg;

import javafx.scene.shape.Rectangle;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;

public class CombatSystem
{
    static enum Pose {
        block(1), recoverBlock(3), 
        prepareAttack(2), attack(3), recoverAttack(5),
        prepareThrow(2), doThrow(3), recoverThrow(5),
        thrown(10), blocked(6), attacked(10),
        walkForward(10), walkBackward(10), turn(10);
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
            walkForward.onExpire = block;
            walkBackward.onExpire = block;
            turn.onExpire = block;
        }
    };
    
    public static void update(Game g)
    {
        for (Entity e:g.getEntities(CombatPoseComponent.class))
        {
            CombatPoseComponent p = g.get(e,CombatPoseComponent.class);
            CombatInputComponent c = g.get(e,CombatInputComponent.class);
            MovementInputComponent m = g.get(e,MovementInputComponent.class);
            FacingDirection f = g.get(e, FacingDirection.class);
            if (c!=null && c.attack && p.pose==Pose.block)
                g.add(e, new CombatPoseComponent(Pose.prepareAttack));
            else if (c!=null && c.flip && p.pose==Pose.block)
                g.add(e, new CombatPoseComponent(Pose.prepareThrow));
            else if (m!=null && (m.left || m.right) && p.pose==Pose.block)
            {
                //Find closest opponent
                double x = g.get(e, Rectangle.class).getX();
                double min = Double.POSITIVE_INFINITY;
                for (Entity opp:g.getEntities(Rectangle.class))
                {
                    Rectangle r = g.get(opp, Rectangle.class);
                    double d = r.getX()-x;
                    if ((d != 0) && (Math.abs(d)<Math.abs(min)))
                        min = d;
                }
                
                //Move or turn
                if (m.left && !m.right && min<0)
                {
                    if (f.direction==MovementSystem.Facing.left)
                        g.add(e, new CombatPoseComponent(Pose.walkForward));
                    else
                    {
                        g.add(e, new CombatPoseComponent(Pose.turn));
                        g.add(e, new FacingDirection(MovementSystem.Facing.left));
                    }
                }
                else if (m.right && !m.left && min>0)
                {
                    if (f.direction==MovementSystem.Facing.right)
                        g.add(e, new CombatPoseComponent(Pose.walkForward));
                    else
                    {
                        g.add(e, new CombatPoseComponent(Pose.turn));
                        g.add(e, new FacingDirection(MovementSystem.Facing.right));
                    }
                }
                else if (m.left && !m.right && min>0)
                {
                    if (f.direction==MovementSystem.Facing.left)
                        g.add(e, new CombatPoseComponent(Pose.walkForward));
                    else
                        g.add(e, new CombatPoseComponent(Pose.walkBackward));
                }
                else if (m.right && !m.left && min<0)
                {
                    if (f.direction==MovementSystem.Facing.right)
                        g.add(e, new CombatPoseComponent(Pose.walkForward));
                    else
                        g.add(e, new CombatPoseComponent(Pose.walkBackward));
                }
            }
            else
            {
                //Check for expiration of current pose
                p.timeLeft--;
                if (p.timeLeft <= 0)
                    g.add(e, new CombatPoseComponent(p.pose.onExpire));
            }
        }
    }
}
