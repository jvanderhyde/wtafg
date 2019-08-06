//Entity-component-system architecture, Entity class
//Created by James Vanderhyde, 5 July 2019

package us.vanderhyde.ecs;

import java.util.UUID;

public class Entity<Component>
{
    final UUID id = UUID.randomUUID();
}
