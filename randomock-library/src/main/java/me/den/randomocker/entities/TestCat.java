package me.den.randomocker.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.den.randomocker.anno.RandoMock;

@RandoMock
public class TestCat {

   @RandoMock
   @SerializedName("number_of_legs") private int legsNumber;

   @RandoMock(minInt = 3, maxInt = 14)
   @SerializedName("number_of_eyes") private int eyesNumber;

   @RandoMock
   @SerializedName("cat_says_sentence") private String sentence;

   @RandoMock(stringKit = { "meow", "meow2", "meow3" })
   @SerializedName("cat_says_a_word") private String oneOfWords;

   @RandoMock(collectionSize = 4)
   @SerializedName("cat_ate_these_mice") private List<TestMouse> ateMice;
}
