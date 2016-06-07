package com.tjgs.robotevolution.entity;

import com.tjgs.robotevolution.components.model.ColliderComponentModel;
import com.tjgs.robotevolution.components.model.CollisionHandlerModel;
import com.tjgs.robotevolution.components.model.GraphicsComponentModel;
import com.tjgs.robotevolution.components.model.PhysicsComponentModel;
import com.tjgs.robotevolution.components.model.PositionComponentModel;
import com.tjgs.robotevolution.level.Level;

/**
 * Created by Tyler Johnson on 6/6/2016.
 *
 * Factory for creating common types of entities
 */
public class EntityFactory {

    private static int uniqueId = 2;

    public static final int COLLECTABLE = 1;
    public static final int MOVEABLE = 2;
    public static final int TRIGGER = 3;
    public static final int RIDEABLE = 4;
    public static final int CHARACTER = 5;

    public static Entity createCollectable(Level level, float x, float y, int worth) {
        EntityBuilder builder = createCollectableBuilder(x, y, worth);
        return builder.build(level);
    }

    public static EntityBuilder createCollectableBuilder(float x, float y, int worth) {
        EntityBuilder builder = new EntityBuilder();
        builder.id = uniqueId++;

        PositionComponentModel posModel = new PositionComponentModel();
        posModel.x = x;
        posModel.y = y;
        posModel.angle = 45f;
        builder.addComponent(posModel);

        PhysicsComponentModel physModel = new PhysicsComponentModel();
        physModel.gravity = 0f;
        physModel.xAccel = 0f;
        physModel.yAccel = 0f;
        physModel.maxSpeed = 0f;
        builder.addComponent(physModel);

        float width = 0.5f;
        float height = 0.5f;

        GraphicsComponentModel graphModel = new GraphicsComponentModel();
        graphModel.scaleX = width;
        graphModel.scaleY = height;
        graphModel.tileId = 0;
        graphModel.tileSet.widthTiles = 1;
        graphModel.tileSet.heightTiles = 1;
        graphModel.tileSet.textureName = "metal";
        builder.addComponent(graphModel);

        ColliderComponentModel collModel = new ColliderComponentModel();
        collModel.width = width;
        collModel.height = height;
        builder.addComponent(collModel);

        return builder;
    }

    public static Entity createPlayer(Level level, float x, float y){
        EntityBuilder builder = createPlayerBuilder(x, y);
        return builder.build(level);
    }

    public static EntityBuilder createPlayerBuilder(float x, float y){
        EntityBuilder playerBuilder = new EntityBuilder();
        playerBuilder.id = 0;

        PositionComponentModel posModel = new PositionComponentModel();
        posModel.x = x;
        posModel.y = y;
        playerBuilder.addComponent(posModel);

        PhysicsComponentModel physModel = new PhysicsComponentModel();
        playerBuilder.addComponent(physModel);

        GraphicsComponentModel graphModel = new GraphicsComponentModel();
        graphModel.scaleX = 1;
        graphModel.scaleY = 2;
        graphModel.tileId = 6;
        graphModel.tileSet.widthTiles = 8;
        graphModel.tileSet.heightTiles = 4;
        graphModel.tileSet.textureName = "texture1";
        playerBuilder.addComponent(graphModel);

        ColliderComponentModel collModel = new ColliderComponentModel();
        collModel.width = 1f;
        collModel.height = 2f;
        playerBuilder.addComponent(collModel);

        CollisionHandlerModel handlerModel = new CollisionHandlerModel();
        playerBuilder.addComponent(handlerModel);

        return playerBuilder;
    }

}
