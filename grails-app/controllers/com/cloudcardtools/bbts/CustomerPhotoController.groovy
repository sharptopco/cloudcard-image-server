package com.cloudcardtools.bbts

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.apache.commons.lang.RandomStringUtils

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND

@Transactional(readOnly = true)
class CustomerPhotoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured("ROLE_READER")
    def show(CustomerPhoto customerPhotoInstance) {
        if (!customerPhotoInstance) {
            customerPhotoInstance = CustomerPhoto.findByCustId(Customer.findByCustnumLike("%$params.id")?.custId)
        }
        respond new CustomerPhotoResource(customerPhotoInstance)
    }

    @Transactional
    @Secured("ROLE_WRITER")
    def save() {
        String id = request.JSON.id

        Long custId = Customer.findByCustnumLike("%$id")?.custId
        if (!custId) {
            render "No customer exists with Custnum == '$id'", status: NOT_FOUND
            return
        }

        new CustomerPhoto(custId: custId, photo: request.JSON.photo).save(failOnError: true, flush: true)
        response.addHeader("Location", createLink(controller: "customerPhotos", action: custId))

        render "${CREATED.value()} ${CREATED.name()}", status: CREATED
    }

}

class CustomerPhotoResource {
    Long custId
    byte[] photo
    String key

    CustomerPhotoResource(CustomerPhoto photo) {
        this.custId = photo?.custId
        this.photo = photo?.photo
        this.key = findImageKey()
    }

    CustomerPhoto toCustomerPhoto() {
        new CustomerPhoto(custId: custId, photo: photo)
    }

    private String findImageKey() {
        ImageKey.findByCustId(custId)?.key ?: new ImageKey(custId: custId, key: RandomStringUtils.randomAlphanumeric(32)).save(failOnError: true, flush: true).key
    }
}