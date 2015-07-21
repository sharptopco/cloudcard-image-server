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
