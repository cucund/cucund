package com.cucund.project.tool.utils.valid;


import javax.validation.Constraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Size()
public @interface DataInfo {

    String key();

    String value();

}
