//World's tiniest fighting game combat system
//Created by James Vanderhyde, 6 July 2019

package us.vanderhyde.wtfg;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;

public class CombatSystem
{
    static enum Pose {
        block(1), recoverBlock(3), 
        prepareAttack(2), attack(3), recoverAttack(5),
        prepareThrow(2), doThrow(3), recoverThrow(5),
        thrown(10), blocked(15), attackedFromFront(10), attackedFromBehind(10),
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
            attackedFromFront.onExpire = block;
            attackedFromBehind.onExpire = block;
            walkForward.onExpire = block;
            walkBackward.onExpire = block;
            turn.onExpire = block;
        }
    };
    
    private static final Set<Pose> throwablePoses = new TreeSet<>();
    private static final Set<Pose> attackablePoses = new TreeSet<>();
    static
    {
        throwablePoses.addAll(Arrays.asList(Pose.block,Pose.walkBackward,Pose.walkForward));
        attackablePoses.addAll(Arrays.asList(Pose.recoverBlock,Pose.prepareThrow,Pose.doThrow,Pose.recoverThrow,Pose.blocked,Pose.turn));
    }
    
    static final double minHitDistance = 15;
    static final double maxHitDistance = 60;
    
    public static void update(Game g)
    {
        for (Entity e:g.getEntities(CombatPoseComponent.class))
        {
            CombatPoseComponent p = g.get(e,CombatPoseComponent.class);
            CombatInputComponent c = g.get(e,CombatInputComponent.class);
            MovementInputComponent m = g.get(e,MovementInputComponent.class);
            FacingDirection f = g.get(e, FacingDirection.class);
            
            //Resolve input cases
            if (c!=null && c.attack && p.pose==Pose.block)
                g.add(e, new CombatPoseComponent(Pose.prepareAttack));
            else if (c!=null && c.flip && p.pose==Pose.block)
                g.add(e, new CombatPoseComponent(Pose.prepareThrow));
            else if (m!=null && (m.left || m.right) && p.pose==Pose.block)
                moveOrTurn(g, e, m, f);
            else
                decrementPoseTime(g, e, p);
        }
        for (Entity e:g.getEntities(CombatPoseComponent.class))
        {
            CombatPoseComponent p = g.get(e,CombatPoseComponent.class);
            FacingDirection f = g.get(e, FacingDirection.class);

            //Resolve combat cases
            //This can modify the pose of an opponent.
            if (p.pose==Pose.doThrow)
                doThrow(g, e, f);
            else if (p.pose==Pose.attack)
                doAttack(g, e, f);
        }
    }

    private static void moveOrTurn(Game g, Entity e, MovementInputComponent m, FacingDirection f)
    {
        //Find closest opponent
        double x = g.get(e, FighterPosition.class).x;
        double min = Double.POSITIVE_INFINITY;
        for (Entity opp:g.getEntities(FighterPosition.class))
        {
            FighterPosition position = g.get(opp, FighterPosition.class);
            double d = position.x-x;
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

    private static void decrementPoseTime(Game g, Entity e, CombatPoseComponent p)
    {
        //Check for expiration of current pose
        p.timeLeft--;
        if (p.timeLeft <= 0)
            g.add(e, new CombatPoseComponent(p.pose.onExpire));
    }

    private static void doThrow(Game g, Entity e, FacingDirection f)
    {
        //Find opponents
        double x = g.get(e, FighterPosition.class).x;
        for (Entity opp:g.getEntities(FighterPosition.class))
        {
            FighterPosition position = g.get(opp, FighterPosition.class);
            double d = position.x-x;
            if ((f.direction==MovementSystem.Facing.right && minHitDistance<=d && d<=maxHitDistance) ||
                    (f.direction==MovementSystem.Facing.left && -minHitDistance>=d && d>=-maxHitDistance))
            {
                //opponent is in range
                CombatPoseComponent opponentPose = g.get(opp,CombatPoseComponent.class);
                if (throwablePoses.contains(opponentPose.pose))
                {
                    //throw opponent
                    g.add(opp, new CombatPoseComponent(Pose.thrown));
                    g.add(opp, new FacingDirection(f.direction));
                }
            }
        }
    }

    private static void doAttack(Game g, Entity e, FacingDirection f)
    {
        //Find opponents
        double x = g.get(e, FighterPosition.class).x;
        int numPoints = 0;
        for (Entity opp:g.getEntities(FighterPosition.class))
        {
            FighterPosition position = g.get(opp, FighterPosition.class);
            double d = position.x-x;
            if ((f.direction==MovementSystem.Facing.right && minHitDistance<=d && d<=maxHitDistance) ||
                    (f.direction==MovementSystem.Facing.left && -minHitDistance>=d && d>=-maxHitDistance))
            {
                //opponent is in range
                CombatPoseComponent opponentPose = g.get(opp,CombatPoseComponent.class);
                FacingDirection oppDir = g.get(opp, FacingDirection.class);
                if ((oppDir.direction==f.direction) && (opponentPose.pose != Pose.attackedFromBehind))
                {
                    //attack opponent from behind
                    g.add(opp, new CombatPoseComponent(Pose.attackedFromBehind));
                    numPoints++;
                }
                else if (attackablePoses.contains(opponentPose.pose))
                {
                    //attack opponent from the front
                    g.add(opp, new CombatPoseComponent(Pose.attackedFromFront));
                    numPoints++;
                }
                else if (opponentPose.pose==Pose.block)
                {
                    //attack was blocked
                    g.add(e, new CombatPoseComponent(Pose.blocked));
                    g.add(opp, new CombatPoseComponent(Pose.recoverBlock));
                }
            }
        }
        if (numPoints > 0)
            g.add(e, new PointComponent(numPoints));
    }

}
