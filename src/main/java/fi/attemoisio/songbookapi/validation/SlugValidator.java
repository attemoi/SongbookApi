package fi.attemoisio.songbookapi.validation;

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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SlugValidator implements ConstraintValidator<Slug, String> {

	@Override
    public void initialize(Slug constraintAnnotation) {
    }

	@Override
    public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
        if (value == null)
            return true;
         
        return value.matches("^[a-z0-9-]+$");
    }
}
