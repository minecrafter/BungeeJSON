package com.imaginarycode.minecraft.bungeejson.api;

import io.netty.handler.codec.http.HttpMethod;

import java.util.List;

public interface RestObject {
    List<RestAction> applicableActions();
    Object handleDirect(HttpMethod method);
}
