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
    public static enum ComponentType {
        hitbox, name, playerControl, aiControl, combatPose
    };
    private final Game<ComponentType> game = new Game<>();
    
    public FightingGame()
    {
        Entity<ComponentType> player1 = new Entity<>();
        game.addEntity(player1);
        game.addComponent(player1, ComponentType.name, "Player 1");
        game.addComponent(player1, ComponentType.hitbox, new Rectangle(10,600,20,50));
        game.addComponent(player1, ComponentType.playerControl, new PlayerControlComponent("LEFT","RIGHT","UP","DOWN"));
        game.addComponent(player1, ComponentType.combatPose, new CombatPoseComponent(CombatSystem.Pose.block));

        Entity<ComponentType> player2 = new Entity<>();
        game.addEntity(player2);
        game.addComponent(player2, ComponentType.name, "Player 2");
        game.addComponent(player2, ComponentType.hitbox, new Rectangle(200,600,20,50));
        game.addComponent(player2, ComponentType.playerControl, new PlayerControlComponent("A","D","W","S"));

        Entity<ComponentType> dummy = new Entity<>();
        game.addEntity(dummy);
        game.addComponent(dummy, ComponentType.name, "Dummy player");
        game.addComponent(dummy, ComponentType.hitbox, new Rectangle(450,600,20,50));
        game.addComponent(dummy, ComponentType.aiControl, new AIControlComponent());
    }
    
    public void update(long delta, Collection<String> input, GraphicsContext gc)
    {
        ControlSystem.update(game, input);
        CombatSystem.update(game);
        GraphicsSystem.render(game, gc);
    }
}
