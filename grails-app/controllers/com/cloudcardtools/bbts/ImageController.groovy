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

import static org.springframework.http.HttpStatus.*

class ImageController {

    def grailsApplication

    def show(String id) {
        if (!grailsApplication.config.imageServer.enabled.toBoolean()) {
            render "The image server is disabled.", status: FORBIDDEN
            return
        }

        byte[] image = CustomerPhoto.findByImageKey(id)?.photo

        if (!image) {
            render "${NOT_FOUND.value()} ${NOT_FOUND.name()}", status: NOT_FOUND
            return
        }

        render file: image, contentType: 'image/jpeg', status: OK

    }
}
