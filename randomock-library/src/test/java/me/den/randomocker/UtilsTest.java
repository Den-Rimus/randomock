package me.den.randomocker;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.fail;

public class UtilsTest {

   private Random mRandom;

   @Before
   public void setUp() throws Exception {
      mRandom = new Random();
   }

   @Test
   public void testGetRandomInt() throws Exception {
      boolean isInBounds = true;
      int nextRandomInt, min = 50, max = 100;

      for (int i = 0; i < 2500; i++) {
         nextRandomInt = Utils.getRandomInt(mRandom, min, max);
         if (nextRandomInt < min || nextRandomInt > max) isInBounds = false;

         if (!isInBounds) fail("Test fails: i = " + i + "; nextRandomInt = " + nextRandomInt);
      }
   }

   @Test
   public void testGetRandomLong() throws Exception {
      boolean isInBounds = true;
      long nextRandomLong, min = 1438721223, max = 1438731223;

      for (int i = 0; i < 25000; i++) {
         nextRandomLong = Utils.getRandomLong(mRandom, min, max);
         if (nextRandomLong < min || nextRandomLong > max) isInBounds = false;

         if (!isInBounds) fail("Test fails: i = " + i + "; nextRandomLong = " + nextRandomLong);
      }
   }

   @Test
   public void testGetRandomDouble() throws Exception {
      boolean isInBounds = true;
      double nextRandomDouble, min = 42D, max = 442D;

      for (int i = 0; i < 2500; i++) {
         nextRandomDouble = Utils.getRandomDouble(mRandom, min, max);
         if (nextRandomDouble < min || nextRandomDouble > max) isInBounds = false;

         if (!isInBounds) fail("Test fails: i = " + i + "; nextRandomDouble = " + nextRandomDouble);
      }

      min = 0.0D;
      max = 1.0D;

      for (int i = 0; i < 2500; i++) {
         nextRandomDouble = Utils.getRandomDouble(mRandom, min, max);
         if (nextRandomDouble < min || nextRandomDouble > max) isInBounds = false;

         if (!isInBounds) fail("Test fails: i = " + i + "; nextRandomDouble = " + nextRandomDouble);
      }
   }
}
