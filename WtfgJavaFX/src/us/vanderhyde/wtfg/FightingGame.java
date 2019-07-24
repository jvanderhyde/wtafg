//World's tiniest fighting game logic
//Created by James Vanderhyde, 5 July 2019

package us.vanderhyde.wtfg;

import java.util.Collection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import us.vanderhyde.ecs.Entity;
import us.vanderhyde.ecs.Game;

/**
 * Keeps track of and updates the game state.
 * This class takes the input from the application framework,
 * the time delta from the animation framework, and the output
 * (graphics context) from the application framework, and it
 * uses these things to make a game.
 * 
 * This game uses the entity-component-system architecture
 * to organize the game state and behavior.
 * 
 * @author James Vanderhyde
 */
public class FightingGame
{
    public static enum Component {
        hitbox, name, playerControl, aiControl
    };
    private final Game<Component> game = new Game<>();
    
    public FightingGame()
    {
        Entity<Component> player1 = new Entity<>();
        game.addEntity(player1);
        game.addComponent(player1, Component.name, "Player 1");
        game.addComponent(player1, Component.hitbox, new Rectangle(10,600,20,50));
        game.addComponent(player1, Component.playerControl, new PlayerControlComponent("LEFT","RIGHT","UP"));

        Entity<Component> player2 = new Entity<>();
        game.addEntity(player2);
        game.addComponent(player2, Component.name, "Player 2");
        game.addComponent(player2, Component.hitbox, new Rectangle(200,600,20,50));
        game.addComponent(player2, Component.playerControl, new PlayerControlComponent("A","D","W"));
    }
    
    public void update(long delta, Collection<String> input, GraphicsContext gc)
    {
        ControlSystem.update(game, input);
        GraphicsSystem.render(game, gc);
    }
}
