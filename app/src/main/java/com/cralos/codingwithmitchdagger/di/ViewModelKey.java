package com.cralos.codingwithmitchdagger.di;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

/**     like i said this is gonna be the same out of any project do ever you creating a custom key t
 *      he only difference would be the key type and then the key name all of these
 *      other things (documented, target, etc) are always goint to be the same             */

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKey {
    Class< ? extends ViewModel> value();
}
