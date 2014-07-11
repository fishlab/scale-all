package org.fishlab.util.persistence.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface Constraint {
	
	
	@Target( { FIELD })
	@Retention( RUNTIME )
	public static @interface Primary{
	}
	@Target( { FIELD })
	@Retention( RUNTIME )
	public static @interface Unique{
	}
	@Target( { FIELD })
	@Retention( RUNTIME )
	public static @interface Foreign{
	}
}
