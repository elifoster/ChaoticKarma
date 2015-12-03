package santa.karma.api;

import net.minecraft.entity.Entity;

import java.util.ArrayList;

public class MobIgnoranceRegistry {
    public static ArrayList<Class> levelOneMobs;
    public static ArrayList<Class> levelTwoMobs;
    public static ArrayList<Class> levelThreeMobs;
    public static ArrayList<Class> levelFourMobs;

    /**
     * Adds an Entity class to the list of level 1 (karma 750) mobs to ignore.
     * @param entity The Entity class to add.
     */
    public static void registerLevelOne(Class<? extends Entity> entity) {
        levelOneMobs.add(entity);
    }

    /**
     * Adds an Entity class to the list of level 2 (karma 500) mobs to ignore.
     * @param entity The Entity class to add.
     */
    public static void registerLevelTwo(Class<? extends Entity> entity) {
        levelTwoMobs.add(entity);
    }

    /**
     * Adds an Entity class to the list of level 3 (karma 250) mobs to ignore.
     * @param entity The Entity class to add.
     */
    public static void registerLevelThree(Class<? extends Entity> entity) {
        levelThreeMobs.add(entity);
    }

    /**
     * Adds an Entity class to the list of level 4 (maximum, karma 0) mobs to ignore.
     * @param entity The Entity class to add.
     */
    public static void registerLevelFour(Class<? extends Entity> entity) {
        levelFourMobs.add(entity);
    }
}
