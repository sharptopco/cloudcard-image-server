package com.cloudcardtools.bbts

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ImageController)
@Mock([ImageKey, CustomerPhoto])
class ImageControllerSpec extends Specification {

    Map mockGrailsApplication
    static final String BACON = "bacon"
    static final Long custId = 123L

    def setup() {
        mockGrailsApplication = [config: [imageServer: [enabled: "true"]]]
        controller.grailsApplication = mockGrailsApplication
    }

    def cleanup() {
    }

    def "test show"() {
        setup:
        new ImageKey(key: BACON, custId: custId).save(validate: false)
        new CustomerPhoto(custId: custId, photo: [1,2,3]).save(validate: false)

        when:
        controller.show(BACON)

        then:
        response.status == OK.value()
        response.contentAsByteArray == [1,2,3]
        response.contentType.contains('image/jpeg')
    }

    def "test show: not found"() {
        when:
        controller.show(BACON)

        then:
        response.status == NOT_FOUND.value()
    }

    def "test show: image server is disabled"() {
        setup:
        controller.grailsApplication.config.imageServer.enabled = "false"

        when:
        controller.show(BACON)

        then:
        response.status == FORBIDDEN.value()
    }
}
