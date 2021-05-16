//World's tiniest fighting game graphics system
//Created by James Vanderhyde, 6 July 2019

package us.vanderhyde.wtfg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;

public class GraphicsSystem
{
    public static void render(Game g, GraphicsContext gc)
    {
        //Blank screen
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        
        //Draw everything
        for (Entity e:g.getEntities(Rectangle.class))
        {
            Rectangle r = g.get(e,Rectangle.class);
            double mid = r.getX()+r.getWidth()/2.0;
            double dir = 1.0;
            FacingDirection fac = g.get(e,FacingDirection.class);
            if (fac != null)
                if (fac.direction==MovementSystem.Facing.left)
                    dir = -1;

            CombatPoseComponent c = g.get(e,CombatPoseComponent.class);
            if (c != null)
            {
                gc.setStroke(Color.BLUE);
                
                //draw some different shapes based on pose
                if (c.pose==CombatSystem.Pose.prepareAttack)
                    gc.strokeLine(mid+dir*r.getWidth()/2, r.getY(), 
                                  mid+dir*(r.getWidth()/2+3*4), r.getY()-4*4);
                if (c.pose==CombatSystem.Pose.attack)
                    gc.strokeLine(mid+dir*r.getWidth()/2, r.getY()+r.getHeight()/2, 
                                  mid+dir*(r.getWidth()/2+5*4), r.getY()+r.getHeight()/2+0*4);
                if (c.pose==CombatSystem.Pose.recoverAttack)
                    gc.strokeLine(mid+dir*r.getWidth()/2, r.getY()+r.getHeight()/2, 
                                  mid+dir*(r.getWidth()/2+4*4), r.getY()+r.getHeight()/2+3*4);
                if (c.pose==CombatSystem.Pose.prepareThrow)
                    gc.strokeRect(mid-3*4/2+dir*(r.getWidth()+3*4)/2, r.getY()+r.getHeight()-4*4, 
                                  3*4, 4*4);
                if (c.pose==CombatSystem.Pose.doThrow)
                    gc.strokeRect(mid-5*4/2+dir*(r.getWidth()+5*4)/2, r.getY()+r.getHeight()/2, 
                                  5*4, 1*4);
                if (c.pose==CombatSystem.Pose.recoverThrow)
                    gc.strokeRect(mid-4*4/2+dir*(r.getWidth()+4*4)/2, r.getY(), 
                                  4*4, 3*4);
                if (c.pose==CombatSystem.Pose.block)
                    gc.strokeRect(mid-1*4/2+dir*(r.getWidth()+1*4)/2, r.getY()+2*4, 
                                  1*4, 5*4);
                if (c.pose==CombatSystem.Pose.recoverBlock)
                    gc.strokeRect(mid-1*4/2+dir*(r.getWidth()+1*4)/2, r.getY()+1*4, 
                                  1*4, 5*4);
                if (c.pose==CombatSystem.Pose.thrown)
                    gc.strokeRect(mid+1*4/2+dir*(r.getWidth()-1*4)/2, r.getY()+2*4, 
                                  1*4, 5*4);
                if (c.pose==CombatSystem.Pose.blocked)
                    gc.strokeLine(mid+dir*r.getWidth()/2, r.getY()+0, 
                                  mid+dir*(r.getWidth()/2+0*4), r.getY()+0-5*4);
                if (c.pose==CombatSystem.Pose.attacked)
                    gc.setStroke(Color.DARKRED);
                if (c.pose==CombatSystem.Pose.walkForward)
                    gc.setStroke(Color.DARKGREEN);
                if (c.pose==CombatSystem.Pose.walkBackward)
                    gc.setStroke(Color.DARKGREEN);
                if (c.pose==CombatSystem.Pose.turn)
                {
                    gc.setStroke(Color.DARKGREEN);
                    dir = 0;
                }
                
            }
            else
                gc.setStroke(Color.BLACK);
            
            gc.strokeRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
            gc.strokeOval(mid+dir*8-2, r.getY()+8, 4, 4);
        }
    }
}
