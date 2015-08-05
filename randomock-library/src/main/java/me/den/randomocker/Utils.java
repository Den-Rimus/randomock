package me.den.randomocker;

import java.lang.reflect.Field;
import java.util.Random;

class Utils {

   public static void patchAccessibility(Field field) {
      if (!field.isAccessible()) field.setAccessible(true);
   }

   /**
    * Returns a pseudo-random number between min and max, inclusive.<br />
    * The difference between min and max can be at most
    * <code>Integer.MAX_VALUE - 1</code>.
    *
    * @param min Minimum value
    * @param max Maximum value.  Must be greater than min.
    *
    * @return Integer between min and max, both inclusive.
    *
    * @throws IllegalStateException when difference between min and max is out of bounds of <code>Integer</code><br />
    *                               or min is greater or equal min
    * @see java.util.Random#nextInt(int)
    */
   public static int getRandomInt(Random random, int min, int max) {
      if ((max - min) > Integer.MAX_VALUE || min >= max)
         throw new IllegalStateException("Incorrect parameters: min = " + min + ", max = " + max);

      // nextInt is normally exclusive of the top value,
      // so add 1 to make it inclusive
      return random.nextInt((max - min) + 1) + min;
   }

   /**
    * Returns a pseudo-random long number between min and max, inclusive.<br />
    * The difference between min and max can be at most
    * <code>Long.MAX_VALUE - 1</code>.
    *
    * @param min Minimum value
    * @param max Maximum value. Must be greater than min.
    *
    * @return long between min and max, both inclusive.
    *
    * @throws IllegalStateException when difference between min and max is out of bounds of <code>Integer</code><br />
    *                               or min is greater or equal min
    * @see #getRandomInt(Random, int, int)
    */
   public static long getRandomLong(Random random, long min, long max) {
      if ((max - min) > Long.MAX_VALUE || min >= max)
         throw new IllegalStateException("Incorrect parameters: min = " + min + ", max = " + max);

      return nextLong(random, (max - min) + 1) + min;
   }

   /**
    * Returns a pseudo-random double number between min and max, inclusive.<br />
    *
    * @param min Minimum value
    * @param max Maximum value. Must be greater than min.
    *
    * @return double between min and max, both inclusive.
    *
    * @throws IllegalStateException when difference between min and max is out of bounds of <code>Integer</code><br />
    *                               or min is greater or equal min
    * @see #getRandomInt(Random, int, int)
    */
   public static double getRandomDouble(Random random, double min, double max) {
      if (Double.valueOf(max - min).isInfinite() || min >= max)
         throw new IllegalStateException("Incorrect parameters: min = " + min + ", max = " + max);

      return min + (max - min) * random.nextDouble();
   }

   /**
    * From here as-is: http://stackoverflow.com/a/2546186/2957893
    */
   private static long nextLong(Random rng, long n) {
      // error checking and 2^x checking removed for simplicity.
      long bits, val;

      do {
         bits = (rng.nextLong() << 1) >>> 1;
         val = bits % n;
      } while (bits - val + (n - 1) < 0L);
      return val;
   }
}
