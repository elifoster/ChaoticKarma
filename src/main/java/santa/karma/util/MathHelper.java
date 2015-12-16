package santa.karma.util;

import java.util.Random;

/**
 * Just a class with some helper methods relating to math.
 */
public class MathHelper {
    /**
     * Gets a random integer either negative or positive. It substracts the initial random
     * integer by half of the bounding integer, in order to allow for results ranging from -bound
     * to +bound. For example: randomNegOrPos(5, new Random()); would give results ranging from
     * -5 to +5.
     * @param bound The cap.
     * @param random An instance of Java's Random class.
     * @return A random integer between -/+ bound.
     */
    public static int randomNegOrPos(int bound, Random random) {
        return random.nextInt(bound) - (bound / 2);
    }
}
