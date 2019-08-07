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
    private final Game game = new Game();
    
    public FightingGame()
    {
        Entity player1 = new Entity();
        game.addEntity(player1);
        game.add(player1, "Player 1");
        game.add(player1, new Rectangle(10,600,20,50));
        game.add(player1, new PlayerControlComponent("LEFT","RIGHT","UP","DOWN"));
        game.add(player1, new CombatPoseComponent(CombatSystem.Pose.block));

        Entity player2 = new Entity();
        game.addEntity(player2);
        game.add(player2, "Player 2");
        game.add(player2, new Rectangle(200,600,20,50));
        game.add(player2, new PlayerControlComponent("A","D","W","S"));

        Entity dummy = new Entity();
        game.addEntity(dummy);
        game.add(dummy, "Dummy player");
        game.add(dummy, new Rectangle(450,600,20,50));
        game.add(dummy, new AIControlComponent());
        
        System.out.println(game);
    }
    
    public void update(long delta, Collection<String> input, GraphicsContext gc)
    {
        ControlSystem.update(game, input);
        CombatSystem.update(game);
        GraphicsSystem.render(game, gc);
    }
}
