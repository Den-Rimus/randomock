package me.den.randomocker;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import android.support.annotation.Nullable;

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

   private Gson mGson;

   public String fetch(Class<?> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException,
         JSONException {
      return fetch(clazz, null);
   }

   public String fetch(Class<?> clazz, @Nullable Integer arraySize) throws IllegalAccessException, InstantiationException,
         InvocationTargetException, JSONException {
      if (!clazz.isAnnotationPresent(RandoMock.class))
         throw new IllegalArgumentException(clazz.getCanonicalName() + " lacks @RandoMock annotation!");

      if (mRandom == null) mRandom = new Random();
      if (mGson == null) mGson = new Gson();

      if (arraySize == null) {
         Object instance = processInstance(clazz.newInstance());
         return mGson.toJson(instance, clazz);
      } else {
         List<Object> collection = new ArrayList<>();

         for (int i = 0; i < arraySize; i++) {
            collection.add(processInstance(clazz.newInstance()));
         }

         return mGson.toJson(collection);
      }
   }

   private Object processInstance(Object instance) throws IllegalAccessException, InstantiationException,
         InvocationTargetException, JSONException {
      for (Field field : instance.getClass().getDeclaredFields()) {
         if (field.isAnnotationPresent(RandoMock.class)) {
            if (Collection.class.isAssignableFrom(field.getType())) {
               processColletionField(field, instance);
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

   private void processColletionField(Field field, Object instance) throws IllegalAccessException, InstantiationException,
         InvocationTargetException, JSONException {
      Utils.patchAccessibility(field);

      RandoMock annotation = field.getAnnotation(RandoMock.class);

      int collectionSize = annotation.collectionSize();

      if (collectionSize == 0) {
         int min = annotation.collectionSizeMin(), max = annotation.collectionSizeMax();
         collectionSize = Utils.getRandomInt(mRandom, min, max);
      }

      ParameterizedType parameterizedCollectionType = (ParameterizedType) field.getGenericType();
      Class<?> collectionType = (Class<?>) parameterizedCollectionType.getActualTypeArguments()[0];

      List<Object> collection = new ArrayList<>();
      for (int i = 0; i < collectionSize; i++) {
         collection.add(processInstance(collectionType.newInstance()));
      }

      field.set(instance, collection);
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

   private void processStringField(Field field, Object instance) throws IllegalAccessException, JSONException {
      Utils.patchAccessibility(field);

      RandoMock annotation = field.getAnnotation(RandoMock.class);

      String[] stringKit = annotation.stringKit();
      String parselableStringKit = annotation.parselableStringKit();
      String result;
      if (stringKit.length == 0) {
         result = new NonsenseGenerator(mRandom).makeHeadline();
      } else if (parselableStringKit.isEmpty()) {
         JSONArray jsonKit = new JSONArray(parselableStringKit);
         result = jsonKit.getString(mRandom.nextInt(parselableStringKit.length()));
      } else {
         result = stringKit[mRandom.nextInt(stringKit.length)];
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

   void setGson(Gson gson) {
      mGson = gson;
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

      public Builder withGson(Gson gson) {
         mInstance.setGson(gson);
         return this;
      }

      public RandoMocker build() {
         return mInstance;
      }
   }
}
