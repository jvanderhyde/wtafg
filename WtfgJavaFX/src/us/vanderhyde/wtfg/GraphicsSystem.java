//World's tiniest fighting game graphics system
//Created by James Vanderhyde, 6 July 2019

package us.vanderhyde.wtfg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;

public class GraphicsSystem
{
    public static void render(Game g, GraphicsContext gc)
    {
        //Blank screen
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        
        //Draw everything
        for (Entity e:g.getEntities(FighterPosition.class))
        {
            double x = g.get(e, FighterPosition.class).x;
            double y = 600;
            double w = 20;
            double h = 50;
            double dir = 1;
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
                    gc.strokeLine(x+dir*w/2, y, 
                                  x+dir*(w/2+3*4), y-4*4);
                if (c.pose==CombatSystem.Pose.attack)
                    gc.strokeLine(x+dir*w/2, y+h/2, 
                                  x+dir*(w/2+5*4), y+h/2+0*4);
                if (c.pose==CombatSystem.Pose.recoverAttack)
                    gc.strokeLine(x+dir*w/2, y+h/2, 
                                  x+dir*(w/2+4*4), y+h/2+3*4);
                if (c.pose==CombatSystem.Pose.prepareThrow)
                    gc.strokeRect(x-3*4/2+dir*(w+3*4)/2, y+h-4*4, 
                                  3*4, 4*4);
                if (c.pose==CombatSystem.Pose.doThrow)
                    gc.strokeRect(x-5*4/2+dir*(w+5*4)/2, y+h/2, 
                                  5*4, 1*4);
                if (c.pose==CombatSystem.Pose.recoverThrow)
                    gc.strokeRect(x-4*4/2+dir*(w+4*4)/2, y, 
                                  4*4, 3*4);
                if (c.pose==CombatSystem.Pose.block)
                    gc.strokeRect(x-1*4/2+dir*(w+1*4)/2, y+2*4, 
                                  1*4, 5*4);
                if (c.pose==CombatSystem.Pose.recoverBlock)
                    gc.strokeRect(x-1*4/2+dir*(w+1*4)/2, y+1*4, 
                                  1*4, 5*4);
                if (c.pose==CombatSystem.Pose.thrown)
                    gc.strokeRect(x+1*4/2+dir*(w-1*4)/2, y+2*4, 
                                  1*4, 5*4);
                if (c.pose==CombatSystem.Pose.blocked)
                    gc.strokeLine(x+dir*w/2, y+0, 
                                  x+dir*(w/2+0*4), y+0-5*4);
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
            
            gc.strokeRect(x-w/2, y, w, h);
            gc.strokeOval(x+dir*8-2, y+8, 4, 4);
        }
    }
}
