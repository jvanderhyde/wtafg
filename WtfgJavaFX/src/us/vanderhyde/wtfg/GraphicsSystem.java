//World's tiniest fighting game graphics system
//Created by James Vanderhyde, 6 July 2019

package us.vanderhyde.wtfg;

import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;
import us.vanderhyde.wtfg.FightingGame.ComponentType;

public class GraphicsSystem
{
    public static void render(Game<ComponentType> g, GraphicsContext gc)
    {
        //Set up mappers for components we will use
        Game<ComponentType>.ComponentMapper<Rectangle> hm;
        hm = g.new ComponentMapper<>(ComponentType.hitbox,Rectangle.class);
        Game<ComponentType>.ComponentMapper<CombatPoseComponent> cm;
        cm = g.new ComponentMapper<>(ComponentType.combatPose,CombatPoseComponent.class);

        //Blank screen
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        
        //Draw everything
        for (Entity e:hm.getEntities())
        {
            Rectangle r = hm.get(e);
            CombatPoseComponent c = cm.get(e);
            if (c != null)
            {
                gc.setStroke(Color.BLUE);
                //draw some different shapes based on pose
                if (c.pose==CombatSystem.Pose.prepareAttack)
                    gc.strokeLine(r.getX()+r.getWidth(), r.getY(), 
                                  r.getX()+r.getWidth()+3*4, r.getY()-4*4);
                if (c.pose==CombatSystem.Pose.attack)
                    gc.strokeLine(r.getX()+r.getWidth(), r.getY()+r.getHeight()/2, 
                                  r.getX()+r.getWidth()+5*4, r.getY()+r.getHeight()/2+0*4);
                if (c.pose==CombatSystem.Pose.recoverAttack)
                    gc.strokeLine(r.getX()+r.getWidth(), r.getY()+r.getHeight()/2, 
                                  r.getX()+r.getWidth()+4*4, r.getY()+r.getHeight()/2+3*4);
            }
            else
                gc.setStroke(Color.BLACK);
            
            gc.strokeRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        }
    }
}
