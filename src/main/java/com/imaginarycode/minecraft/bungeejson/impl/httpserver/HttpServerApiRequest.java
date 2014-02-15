/**
 * This file is part of BungeeJSON.
 *
 * BungeeJSON is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BungeeJSON is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BungeeJSON.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.imaginarycode.minecraft.bungeejson.impl.httpserver;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.imaginarycode.minecraft.bungeejson.api.ApiRequest;

import java.net.InetAddress;

public final class HttpServerApiRequest implements ApiRequest {
    private final InetAddress remoteIp;
    private final ListMultimap<String, String> params;
    private final String data;

    public HttpServerApiRequest(InetAddress ia, Multimap<String, String> params, String body) {
        this.remoteIp = ia;
        this.data = body;
        this.params = ImmutableListMultimap.copyOf(params);
    }

    @Override
    public InetAddress getRemoteIp() {
        return remoteIp;
    }

    @Override
    public ListMultimap<String, String> getParams() {
        return params;
    }

    @Override
    public String getData() {
        return data;
    }
}
