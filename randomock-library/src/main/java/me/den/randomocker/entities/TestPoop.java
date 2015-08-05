package me.den.randomocker.entities;

import me.den.randomocker.anno.RandoMock;

@RandoMock
public class TestPoop {

   @RandoMock(minInt = 1, maxInt = 10)
   private int poopNumber;
}
