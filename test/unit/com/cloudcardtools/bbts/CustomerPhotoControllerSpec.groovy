package com.cloudcardtools.bbts

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND


/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(CustomerPhotoController)
@Mock([ImageKey, Customer, CustomerPhoto])
class CustomerPhotoControllerSpec extends Specification {

    CustomerPhoto customerPhoto
    long custId

    def setup() {
        custId = 123L
        customerPhoto = new CustomerPhoto(custId: custId, photo: [1, 2, 3]).save(validate: false)
        new Customer(custId: customerPhoto.custId, custnum: "eggs and bacon").save(validate: false, flush: true)
    }

    def cleanup() {
    }

    def "test customer photo resource"() {
        when:
        CustomerPhotoResource resource = new CustomerPhotoResource(customerPhoto)

        then:
        resource.custId == customerPhoto.custId
        resource.photo == customerPhoto.photo
        resource.key.length() == 32
    }

    def "test show by custId"() {
        when:
        controller.show(customerPhoto)

        then:
        model.customerPhotoResourceInstance.custId == customerPhoto.custId
        model.customerPhotoResourceInstance.photo == customerPhoto.photo
        model.customerPhotoResourceInstance.key.length() == 32
    }

    def "test show by custnum"() {
        when:
        params.id = "bacon"
        controller.show(null)

        then:
        model.customerPhotoResourceInstance.custId == customerPhoto.custId
        model.customerPhotoResourceInstance.photo == customerPhoto.photo
        model.customerPhotoResourceInstance.key.length() == 32
    }

    def "test save photo"() {
        setup:
        request.JSON = [id: "bacon", photo: [4, 5, 6]]
        request.method = "POST"
        assert CustomerPhoto.findByCustId(custId).photo != request.JSON.photo

        when:
        controller.save()

        then:
        CustomerPhoto.findByCustId(custId).photo == request.JSON.photo
        response.status == CREATED.value()
        response.header("Location")
    }

    def "test save photo for non-existent customer"() {
        setup:
        request.JSON = [id: "pancakes", photo: [4, 5, 6]]
        request.method = "POST"

        when:
        controller.save()

        then:
        response.status == NOT_FOUND.value()
    }
}
