//World's tiniest fighting game graphics system
//Created by James Vanderhyde, 6 July 2019

package us.vanderhyde.wtfg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;
import us.vanderhyde.wtfg.FightingGame.Component;

public class GraphicsSystem
{
    public static void render(Game<Component> g, GraphicsContext gc)
    {
        //Blank screen
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        
        //Draw everything
        for (Entity e:g.getEntities(Component.hitbox))
        {
            Rectangle r = (Rectangle)g.getComponent(e, Component.hitbox);
            gc.strokeRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        }
    }
}
