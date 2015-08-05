package me.den.randomocker.entities;

import me.den.randomocker.anno.RandoMock;

@RandoMock
public class TestCat {

   @RandoMock
   private int legsNumber;

   @RandoMock(minInt = 3, maxInt = 14)
   private int eyesNumber;

   @RandoMock
   private String sentence;

   @RandoMock(stringKit = { "meow", "meow2", "meow3" })
   private String oneOfWords;
}
