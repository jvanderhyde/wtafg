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
        //Set up mappers for components we will use
        Game<ComponentType>.ComponentMapper<Rectangle> hm;
        hm = game.new ComponentMapper<>(ComponentType.hitbox,Rectangle.class);
        Game<ComponentType>.ComponentMapper<String> nm;
        nm = game.new ComponentMapper<>(ComponentType.name,String.class);
        Game<ComponentType>.ComponentMapper<CombatPoseComponent> cm;
        cm = game.new ComponentMapper<>(ComponentType.combatPose,CombatPoseComponent.class);
        Game<ComponentType>.ComponentMapper<PlayerControlComponent> pm;
        pm = game.new ComponentMapper<>(ComponentType.playerControl,PlayerControlComponent.class);
        Game<ComponentType>.ComponentMapper<AIControlComponent> am;
        am = game.new ComponentMapper<>(ComponentType.aiControl,AIControlComponent.class);

        Entity<ComponentType> player1 = new Entity<>();
        game.addEntity(player1);
        nm.add(player1, "Player 1");
        hm.add(player1, new Rectangle(10,600,20,50));
        pm.add(player1, new PlayerControlComponent("LEFT","RIGHT","UP","DOWN"));
        cm.add(player1, new CombatPoseComponent(CombatSystem.Pose.block));

        Entity<ComponentType> player2 = new Entity<>();
        game.addEntity(player2);
        nm.add(player2, "Player 2");
        hm.add(player2, new Rectangle(200,600,20,50));
        pm.add(player2, new PlayerControlComponent("A","D","W","S"));

        Entity<ComponentType> dummy = new Entity<>();
        game.addEntity(dummy);
        nm.add(dummy, "Dummy player");
        hm.add(dummy, new Rectangle(450,600,20,50));
        am.add(dummy, new AIControlComponent());
    }
    
    public void update(long delta, Collection<String> input, GraphicsContext gc)
    {
        ControlSystem.update(game, input);
        CombatSystem.update(game);
        GraphicsSystem.render(game, gc);
    }
}
