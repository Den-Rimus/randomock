package me.den.randomocker.entities;

import me.den.randomocker.anno.RandoMock;

@RandoMock
public class Cheese {

   @RandoMock(stringKit = { "cheese1", "cheese2", "cheese3", "cheese4", "cheese5", "cheese6" })
   private String name;
}
