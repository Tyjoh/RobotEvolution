package com.tjgs.robotevolution.entity;

import com.tjgs.robotevolution.animation.Animation;
import com.tjgs.robotevolution.animation.Bone;
import com.tjgs.robotevolution.animation.BoneAnimation;
import com.tjgs.robotevolution.animation.Skeleton;
import com.tjgs.robotevolution.components.SkeletonControllerComponent;
import com.tjgs.robotevolution.components.model.AnimatedGraphicsModel;
import com.tjgs.robotevolution.components.model.ColliderComponentModel;
import com.tjgs.robotevolution.components.model.CollisionHandlerModel;
import com.tjgs.robotevolution.components.model.GraphicsComponentModel;
import com.tjgs.robotevolution.components.model.PhysicsComponentModel;
import com.tjgs.robotevolution.components.model.PositionComponentModel;
import com.tjgs.robotevolution.components.model.SimpleAIControllerComponentModel;
import com.tjgs.robotevolution.components.model.SkeletonControllerModel;
import com.tjgs.robotevolution.level.Level;

import java.util.HashMap;

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
        //posModel.angle = 45f;
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
        graphModel.width = width;
        graphModel.height = height;
//        graphModel.originX = (width / 2f);
//        graphModel.originY = (height / 2f);
        graphModel.tileId = 0;
        graphModel.tileSet.widthTiles = 1;
        graphModel.tileSet.heightTiles = 1;
        graphModel.tileSet.textureName = "metal";
        builder.addComponent(graphModel);

        ColliderComponentModel collModel = new ColliderComponentModel();
        collModel.width = width;
        collModel.height = height;
//        collModel.originX = (width / 2f);
//        collModel.originY = (height / 2f);
        builder.addComponent(collModel);

        //SimpleAIControllerComponentModel aiComp = new SimpleAIControllerComponentModel();
        //builder.addComponent(aiComp);

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
        graphModel.width = 1;
        graphModel.height = 2;
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

    public static EntityBuilder createAnimatedPlayerBuilder(float x, float y){
        EntityBuilder playerBuilder = new EntityBuilder();
        playerBuilder.id = 0;

        PositionComponentModel posModel = new PositionComponentModel();
        posModel.x = x;
        posModel.y = y;
        playerBuilder.addComponent(posModel);

        PhysicsComponentModel physModel = new PhysicsComponentModel();
        playerBuilder.addComponent(physModel);

        AnimatedGraphicsModel graphModel = new AnimatedGraphicsModel();
        graphModel.animation = createPlayerAnimation();
        graphModel.skeleton = createPlayerSkeleton();
        graphModel.tileSet.widthTiles = 2;
        graphModel.tileSet.heightTiles = 2;
        graphModel.tileSet.textureName = "character";
        playerBuilder.addComponent(graphModel);

        ColliderComponentModel collModel = new ColliderComponentModel();
        collModel.width = 1f;
        collModel.height = 2f;
        playerBuilder.addComponent(collModel);

        CollisionHandlerModel handlerModel = new CollisionHandlerModel();
        playerBuilder.addComponent(handlerModel);

        SkeletonControllerModel skeleModel = new SkeletonControllerModel();
        playerBuilder.addComponent(skeleModel);

        return playerBuilder;
    }

    public static Entity createTestSkeletonEntity(Level level, float x, float y){
        EntityBuilder builder = createTestSkeletonEntityBuilder(x, y);
        return builder.build(level);
    }

    public static EntityBuilder createTestSkeletonEntityBuilder(float x, float y){
        EntityBuilder skeletonBuilder = new EntityBuilder();
        skeletonBuilder.id = uniqueId++;

        PositionComponentModel posModel = new PositionComponentModel();
        posModel.x = x;
        posModel.y = y;
        skeletonBuilder.addComponent(posModel);

        // ======== Animation Model ========//
        AnimatedGraphicsModel aniModel = new AnimatedGraphicsModel();
        aniModel.tileSet.widthTiles = 2;
        aniModel.tileSet.heightTiles = 2;
        aniModel.tileSet.textureName = "metal";
        aniModel.skeleton = createSkeleton();
        aniModel.animation = createAnimation();

        skeletonBuilder.addComponent(aniModel);
        // ======== Animation Model ========//

        return skeletonBuilder;
    }

    private static Skeleton createPlayerSkeleton(){

        Bone root = new Bone("Root", null, 0.3f, angle(90), angle(-90), 1f, 1.3f, 0, -0.3f);
        Bone hydraulic = new Bone("Hydraulic", root, 0.8f, angle(180), angle(-90), 0.2f, 0.6f, 0, -0.3f);
        Bone wheel = new Bone("Wheel", hydraulic, 0.51f, angle(0), angle(-90), 1.02f, 1.02f, 0f, 0);

        HashMap<String, Integer> boneTexMap = new HashMap<>();
        boneTexMap.put("Root", 1);
        boneTexMap.put("Hydraulic", -1);
        boneTexMap.put("Wheel", 0);

        Skeleton skeleton = new Skeleton(root, boneTexMap);
        return skeleton;
    }

    public static Animation createPlayerAnimation(){
        int frames = 1;

        Animation ani = new Animation(frames);
        ani.setFrameTime(0, 1000);

        return ani;
    }

    private static Skeleton createSkeleton(){
        Bone root = new Bone("Root", null, 1.0f, angle(90), angle(0), 1f, 0.25f, -0.5f, 0);
        Bone limb1 = new Bone("Limb1", root, 0.5f, angle(20), angle(0), 0.5f, 0.25f, -0.25f, 0);
        Bone limb2 = new Bone("Limb2", limb1, 0.5f, angle(20), angle(0), 0.5f, 0.25f, -0.25f, 0);
        Bone limb3 = new Bone("Limb3", root, 0.5f, angle(-20), angle(0), 0.5f, 0.25f, -0.25f, 0);
        Bone limb4 = new Bone("Limb4", limb3, 0.5f, angle(-20), angle(0), 0.5f, 0.25f, -0.25f, 0);

        HashMap<String, Integer> boneTexMap = new HashMap<>();
        boneTexMap.put("Root", 0);
        boneTexMap.put("Limb1", 1);
        boneTexMap.put("Limb2", 2);
        boneTexMap.put("Limb3", 3);
        boneTexMap.put("Limb4", 2);

        Skeleton skeleton = new Skeleton(root, boneTexMap);
        skeleton.update();
        return skeleton;
    }

    private static Animation createAnimation(){
        int frames = 2;

        Animation ani = new Animation(frames);
        ani.setFrameTime(0, 200).setFrameTime(1, 450);//.setFrameTime(2, 3500);

        BoneAnimation root = new BoneAnimation(frames);
        root.setFrame(0, angle(90), 0, 0.1f).setFrame(1, angle(90), 0, -0.1f);
        //root.setFrame(2, 0, 1, 1f);

        BoneAnimation limb1 = new BoneAnimation(frames);
        limb1.setFrame(0, angle(75), 1, 0f).setFrame(1, angle(95), -1, 0f);

        BoneAnimation limb3 = new BoneAnimation(frames);
        limb3.setFrame(0, angle(285), -1, 0f).setFrame(1, angle(265), 1, 0f);

        BoneAnimation limb2 = new BoneAnimation(frames);
        limb2.setFrame(0, angle(360 - 25), 1, 0f).setFrame(1, angle(25), -1, 0f);

        BoneAnimation limb4 = new BoneAnimation(frames);
        limb4.setFrame(0, angle(25), -1, 0f).setFrame(1, angle(360 - 25), 1, 0f);

        ani.addBoneAnimation("Root", root);
        ani.addBoneAnimation("Limb1", limb1);
        ani.addBoneAnimation("Limb3", limb3);
        ani.addBoneAnimation("Limb2", limb2);
        ani.addBoneAnimation("Limb4", limb4);

        return ani;
    }

    private static float angle(float f){
        return (float) Math.toRadians(f);
    }

}
