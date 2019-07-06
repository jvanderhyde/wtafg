//World's tiniest fighting game logic
//Created by James Vanderhyde, 5 July 2019

package us.vanderhyde.wtfg;

import java.util.Collection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;

public class FightingGame
{
    public static enum Component {
        hitbox,
        name
    };
    private final Game<Component> game = new Game<>();
    
    public FightingGame()
    {
        Entity<Component> player = new Entity<>();
        game.addEntity(player);
        game.addComponent(player, Component.name, "Player");
        game.addComponent(player, Component.hitbox, new Rectangle(10,600,20,50));
    }
    
    public void update(long delta, Collection<String> input, GraphicsContext gc)
    {
        //Update physics
        System.out.println(game);
        
        //Draw everything
        for (Entity e:game.getEntities(Component.hitbox))
        {
            System.out.println("update");
            Rectangle r = (Rectangle)game.getComponent(e, Component.hitbox);
            gc.strokeRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        }
    }
}
