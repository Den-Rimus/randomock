package me.den.randomocker;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import me.den.randomocker.entities.TestCat;

public class RandoMockerTest {

   private RandoMocker mRandoMocker;

   @Before
   public void setUp() throws Exception {
      mRandoMocker = new RandoMocker.Builder().skipUnknownField(false).withRandom(new Random()).build();
   }

   //   @Test(expected = Exception.class)
   @Test()
   public void testFetch() throws Exception {
      String deSerializedResult = mRandoMocker.fetch(TestCat.class);
      if (deSerializedResult != null) throw new Exception("This passes: " + deSerializedResult);
   }

   @Test()
   public void testFetchMultiple() throws Exception {
      String deSerializedResult = mRandoMocker.fetch(TestCat.class, 7);
      if (deSerializedResult != null) throw new Exception("This passes: " + deSerializedResult);
   }

//   @Test
//   public void testFetchMinInt() throws Exception {
//      assertTrue(new RandoMocker().fetch(TestCat.class).startsWith("legsNumber: "));
//   }
}
