package annotation;

import java.lang.annotation.*;

/**
 * Model
 */
@Retention (RetentionPolicy.RUNTIME)
public @interface Field {
    String name () default "";
}