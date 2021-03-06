//World's tiniest fighting game graphics system
//Created by James Vanderhyde, 6 July 2019

package us.vanderhyde.wtfg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;

public class GraphicsSystem
{
    public static void render(Game g, GraphicsContext gc)
    {
        //Blank screen
        double canvasHeight = gc.getCanvas().getHeight();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), canvasHeight);
        
        //Draw all the fighters
        for (Entity e:g.getEntities(FighterPosition.class))
        {
            double x = g.get(e, FighterPosition.class).x;
            double size = 6;
            double w = 5*size;
            double h = 12.5*size;
            double y = canvasHeight-10-h;
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
                                  x+dir*(w/2+3*size), y-4*size);
                if (c.pose==CombatSystem.Pose.attack)
                    gc.strokeLine(x+dir*w/2, y+h/2, 
                                  x+dir*(w/2+5*size), y+h/2+0*size);
                if (c.pose==CombatSystem.Pose.recoverAttack)
                    gc.strokeLine(x+dir*w/2, y+h/2, 
                                  x+dir*(w/2+4*size), y+h/2+3*size);
                if (c.pose==CombatSystem.Pose.prepareThrow)
                    gc.strokeRect(x-3*size/2+dir*(w+3*size)/2, y+h-4*size, 
                                  3*size, 4*size);
                if (c.pose==CombatSystem.Pose.doThrow)
                    gc.strokeRect(x-5*size/2+dir*(w+5*size)/2, y+h/2, 
                                  5*size, 1*size);
                if (c.pose==CombatSystem.Pose.recoverThrow)
                    gc.strokeRect(x-4*size/2+dir*(w+4*size)/2, y, 
                                  4*size, 3*size);
                if (c.pose==CombatSystem.Pose.block)
                    gc.strokeRect(x-1*size/2+dir*(w+1*size)/2, y+2*size, 
                                  1*size, 5*size);
                if (c.pose==CombatSystem.Pose.recoverBlock)
                    gc.strokeRect(x-1*size/2+dir*(w+1*size)/2, y+3*size, 
                                  1*size, 5*size);
                if (c.pose==CombatSystem.Pose.thrown)
                    gc.strokeRect(x+1*size/2+dir*(w-1*size)/2, y+2*size, 
                                  1*size, 5*size);
                if (c.pose==CombatSystem.Pose.blocked)
                    gc.strokeLine(x+dir*w/2, y+0, 
                                  x+dir*(w/2+0*size), y+0-5*size);
                if (c.pose==CombatSystem.Pose.attackedFromBehind)
                    gc.setStroke(Color.DARKRED);
                if (c.pose==CombatSystem.Pose.attackedFromFront)
                    gc.setStroke(Color.DARKRED);
                if (c.pose==CombatSystem.Pose.walkForward || c.pose==CombatSystem.Pose.walkBackward)
                {
                    gc.setStroke(Color.DARKGREEN);
                    gc.strokeRect(x-1*size/2+dir*(w+1*size)/2, y+2*size, 
                                  1*size, 5*size);
                }
                if (c.pose==CombatSystem.Pose.turn)
                {
                    gc.setStroke(Color.DARKGREEN);
                    dir = 0;
                }
                
                //Hitbox graphics
                gc.save();
                gc.setStroke(Color.LIGHTBLUE);
                gc.strokeLine(x, y, x, y+h);
                if (c.pose==CombatSystem.Pose.doThrow || c.pose==CombatSystem.Pose.attack)
                {
                    gc.strokeLine(x+dir*CombatSystem.minHitDistance, y+h/2,
                                  x+dir*CombatSystem.maxHitDistance, y+h/2);
                }
                gc.restore();
            }
            else
                gc.setStroke(Color.BLACK);
            
            gc.strokeRect(x-w/2, y, w, h);
            gc.strokeOval(x+dir*2*size-size/2, y+2*size, size, size);
            TeamName team = g.get(e, TeamName.class);
            if (team != null)
                gc.fillText(team.name, x-size, y+8*size);
        }
        
        //Draw the scores
        double x = 0;
        int teamNumber = 1;
        gc.setFont(Font.font(36));
        for (Entity e:g.getEntities(ScoreComponent.class))
        {
            ScoreComponent score = g.get(e, ScoreComponent.class);
            String name = ""+teamNumber;
            TeamName team = g.get(e, TeamName.class);
            if (team != null) name = team.name;
            for (int i=0; i<score.score; i++)
            {
                gc.fillText(name, x, 50);
                x+=15;
            }
            x+=30;
            teamNumber++;
        }
    }
}
