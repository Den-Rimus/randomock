package me.den.randomocker;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import me.den.randomocker.anno.RandoMock;

public class RandoMocker {

   private boolean mSilentlySkipUnknownField = true;

   private Random mRandom = null;

   public String fetch(Class<?> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
      if (!clazz.isAnnotationPresent(RandoMock.class))
         throw new IllegalArgumentException(clazz.getCanonicalName() + " lacks @RandoMock annotation!");

      if (mRandom == null) mRandom = new Random();

      Object instance = processInstance(clazz.getConstructors()[0].newInstance());

      return new Gson().toJson(instance, clazz);
   }

   private Object processInstance(Object instance) throws IllegalAccessException, InstantiationException, InvocationTargetException {
      for (Field field : instance.getClass().getDeclaredFields()) {
         if (field.isAnnotationPresent(RandoMock.class)) {
            if (Collection.class.isAssignableFrom(field.getType())) {
               Utils.patchAccessibility(field);

               RandoMock annotation = field.getAnnotation(RandoMock.class);

               int collectionSize = annotation.collectionSize();

               ParameterizedType parameterizedCollectionType = (ParameterizedType) field.getGenericType();
               Class<?> collectionType = (Class<?>) parameterizedCollectionType.getActualTypeArguments()[0];

               List<Object> collection = new ArrayList<>();
               for (int i = 0; i < collectionSize; i++) {
                  collection.add(processInstance(collectionType.getConstructors()[0].newInstance()));
               }

               field.set(instance, collection);
            } else {
               switch (field.getType().toString()) {
                  case "int": {
                     processIntField(field, instance);
                     break;
                  }
                  case "class java.lang.String": { // TODO : looks dirty to me, works so far though. Re-do later
                     processStringField(field, instance);
                     break;
                  }
                  case "long": {
                     processLongField(field, instance);
                     break;
                  }
                  case "double": {
                     processDoubleField(field, instance);
                     break;
                  }
                  default:
                     if (!mSilentlySkipUnknownField)
                        throw new UnsupportedOperationException("Can't handle field of type " + field.getType().toString());
               }
            }
         }
      }

      return instance;
   }

   private void processIntField(Field field, Object instance) throws IllegalAccessException {
      Utils.patchAccessibility(field);

      RandoMock annotation = field.getAnnotation(RandoMock.class);

      int[] defaultKit = annotation.intKit();
      int result;

      if (defaultKit.length == 0) {
         int min, max;

         min = annotation.minInt();
         max = annotation.maxInt();

         result = Utils.getRandomInt(mRandom, min, max);
      } else {
         result = defaultKit[mRandom.nextInt(defaultKit.length)];
      }

      field.setInt(instance, result);
   }

   private void processStringField(Field field, Object instance) throws IllegalAccessException {
      Utils.patchAccessibility(field);

      RandoMock annotation = field.getAnnotation(RandoMock.class);

      String[] defaultKit = annotation.stringKit();
      String result;
      if (defaultKit.length == 0) {
         result = new NonsenseGenerator(mRandom).makeHeadline();
      } else {
         result = defaultKit[mRandom.nextInt(defaultKit.length)];
      }

      field.set(instance, result);
   }

   private void processLongField(Field field, Object instance) throws IllegalAccessException {
      Utils.patchAccessibility(field);

      RandoMock annotation = field.getAnnotation(RandoMock.class);

      long[] defaultKit = annotation.longKit();
      long result;
      if (defaultKit.length == 0) {
         long min, max;

         min = annotation.minLong();
         max = annotation.maxLong();

         result = Utils.getRandomLong(mRandom, min, max);
      } else {
         result = defaultKit[mRandom.nextInt(defaultKit.length)];
      }

      field.setLong(instance, result);
   }

   private void processDoubleField(Field field, Object instance) throws IllegalAccessException {
      Utils.patchAccessibility(field);

      RandoMock annotation = field.getAnnotation(RandoMock.class);

      double[] defaultKit = annotation.doubleKit();
      double result;
      if (defaultKit.length == 0) {
         double min, max;

         min = annotation.minDouble();
         max = annotation.maxDouble();

         result = Utils.getRandomDouble(mRandom, min, max);
      } else {
         result = defaultKit[mRandom.nextInt(defaultKit.length)];
      }

      field.setDouble(instance, result);
   }

   void setSilentlySkipUnknownField(boolean silentlySkipUnknownField) {
      mSilentlySkipUnknownField = silentlySkipUnknownField;
   }

   void setRandom(Random random) {
      mRandom = random;
   }

   public static class Builder {

      private RandoMocker mInstance;

      public Builder() {
         mInstance = new RandoMocker();
      }

      public Builder withRandom(Random random) {
         mInstance.setRandom(random);
         return this;
      }

      public Builder skipUnknownField(boolean skip) {
         mInstance.setSilentlySkipUnknownField(skip);
         return this;
      }

      public RandoMocker build() {
         return mInstance;
      }
   }
}
