package com.imaginarycode.minecraft.bungeejson.api;

public interface RestMatcher<T> {
    T match(String path);
}
