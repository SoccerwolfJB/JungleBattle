package com.github.ForumDevGroup.JungleBattle.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Path {

    public String getPath();
}
