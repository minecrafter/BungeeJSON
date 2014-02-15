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

/**
 * This interface specifies a request handler.
 */
public interface RequestHandler {
    /**
     * Handle the request.
     * @param request an {@link com.imaginarycode.minecraft.bungeejson.api.ApiRequest}
     * @return an Object that can be serialized by Gson
     */
    Object handle(ApiRequest request);

    /**
     * Return true or false if this request requires authentication.
     * @return whether or not this handler requires authentication
     */
    boolean requiresAuthentication();
}
