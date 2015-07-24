/**
 * Copyright (c) 2015 Cloud Card LLC
 *
 * This file is part of CloudCard Image Server.
 *
 * CloudCard Image Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CloudCard Image Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CloudCard Image Server.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.cloudcardtools.bbts

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ["custId"])
class CustomerPhoto {

    Long custId
    byte[] photo

    static constraints = {
        photo maxSize: 16777216
    }

    static mapping = {
        datasource 'bbts'
        table schema: 'envision'
        id name: 'custId', generator: 'assigned'
        photo sqlType: 'blob'
        version false
    }

    static findByImageKey(String key) {
        Long custId = ImageKey.findByKey(key)?.custId
        CustomerPhoto.findByCustId(custId)
    }
}
