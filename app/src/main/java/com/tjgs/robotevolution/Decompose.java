package com.tjgs.robotevolution;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Tyler Johnson on 6/5/2016.
 *
 * Annotation used to mark whether an object should be further decomposed in the map editor
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Decompose {
}
