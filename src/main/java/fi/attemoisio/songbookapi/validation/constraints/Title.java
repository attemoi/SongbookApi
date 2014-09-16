package fi.attemoisio.songbookapi.validation.constraints;

/*
 * ###################################################################=
 * Laulukirja API
 * %%
 * Copyright (C) 2014 Atte Moisio
 * %%
 * DO WHAT YOU WANT TO PUBLIC LICENSE
 * 
 *  Copyright (C) 2014 Atte Moisio
 * 
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 * 
 *  DO WHAT YOU WANT TO PUBLIC LICENSE
 *  TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 * 
 *  0. You just DO WHAT YOU WANT TO.
 * ###################################################################-
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Documented
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@NotBlank
@Size(min = 1, max = 255)
public @interface Title {
	String message() default "{fi.attemoisio.songbookapi.validation.constraints.Title.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
