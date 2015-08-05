package me.den.randomocker.entities;

import com.google.gson.annotations.SerializedName;

import me.den.randomocker.anno.RandoMock;

@RandoMock
public class TestMouse {

   @RandoMock(intKit = { 3, 7, 12 })
   @SerializedName("mouse_has_this_many_tails") private int tails;
}
