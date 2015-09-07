# randomock

A library to free you from tiresome process of thinking up mocked server responces for your android client app.
Assuming you already have model classes that represent your entities - all you need is to feed them to Randomocker and voila - the mocked server responce is here.

Like this:
```java
String deSerializedResult = mRandoMocker.fetch(TestCat.class);
```

And this will give you a json-array with up to 7 TestCat entities, filled with randomly generated data:

```java
String deSerializedResult = mRandoMocker.fetch(TestCat.class);
```

Of course, you need to specify some boundaries for your class's fields, not to get too abstract:

```java
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
```

This is all documentation so far. Stay tuned!
