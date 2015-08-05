package me.den.randomocker.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>
 * TODO
 * </b> : write docs
 */
@Target( { ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RandoMock {

   int minInt() default 1;

   int maxInt() default 100;

   int[] intKit() default { };

   String[] stringKit() default { };

   String parselableStringKit() default "";

   long minLong() default 1L;

   long maxLong() default 2147471999L;

   long[] longKit() default { };

   double minDouble() default 0.1D;

   double maxDouble() default 1D;

   double[] doubleKit() default { };

   int collectionSize() default 0;

   int collectionSizeMin() default 1;

   int collectionSizeMax() default 8;
}
