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
package com.imaginarycode.minecraft.bungeejson.api;

import com.google.common.collect.ListMultimap;

import java.net.InetAddress;

/**
 * This interface represents an API request, regardless of request medium.
 */
public interface ApiRequest {
    /**
     * Get the remote IP associated with this request. This can be null depending on request medium.
     * @return the remote IP address
     */
    public InetAddress getRemoteIp();

    /**
     * Returns a <b>immutable</b> {@link com.google.common.collect.ListMultimap}, with all the parameters passed to this request.
     * <p/>
     * Most of time, you want to check for the existence of the key in the multimap, and then use <code>.get(key).get(0)</code>, as usually one parameter is passed.
     * @return a {@link com.google.common.collect.ListMultimap}
     */
    public ListMultimap<String, String> getParams();
}
