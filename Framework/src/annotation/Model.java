package annotation;

import java.lang.annotation.*;

/**
 * Model
 */
@Retention (RetentionPolicy.RUNTIME)
public @interface Model {
    String table() default "";
}