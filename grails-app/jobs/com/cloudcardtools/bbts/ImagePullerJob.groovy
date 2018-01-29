package com.cloudcardtools.bbts

import org.codehaus.groovy.grails.commons.GrailsApplication

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse

class ImagePullerJob {

    GrailsApplication grailsApplication
    //TODO: move the requests into a RestUtil...
//    RestUtil rest = new RestUtil()

    RestBuilder restBuilder = new RestBuilder()

    static triggers = {
//        simple startDelay:10000, repeatInterval: 600000
        simple startDelay:10000, repeatInterval: 30000
    }

    def execute() {
        log.info "Pulling images from CloudCard API ($grailsApplication.config.cloudcard.apiURL) using Access Token [$grailsApplication.config.cloudcard.accessToken]"

        fetchApprovedPhotos().each { photo ->
            try {
                downloadPhoto(photo)
            } catch (Exception e) {
                log.error e.message
            }
        }
    }

    void downloadPhoto(photo) {
        saveCustomerPhoto(
            findCustIdForCustnum(photo.person.identifier),
            fetchPhotoBytes(photo.publicKey)
        )

        markPhotoAsDownloaded(photo.id)
    }


    List fetchApprovedPhotos() {
        RestResponse response = restBuilder.get("$grailsApplication.config.cloudcard.apiURL/api/photos?status=APPROVED") {
            header "X-Auth-Token", grailsApplication.config.cloudcard.accessToken
        }

        if (response.status != 200) {
            throw new Exception("Error polling for approved images: $response.text")
        }

        return response.json
    }

    byte[] fetchPhotoBytes(String publicKey) {
            RestResponse response = restBuilder.get("$grailsApplication.config.cloudcard.apiURL/api/photos/$publicKey/bytes") {
                accept "image/jpeg"
            }

            if (response.status != 200) {
                throw new Exception("Error retrieving bytes for image with public key [$publicKey]: $response.text")
            }

            return response.body
    }

    Long findCustIdForCustnum(String custnum) {
        if (!custnum) {
            //TODO: make some way of flagging persons without identifiers.
            throw new Exception("User does not have an identifier defined")
        }

        Long custId = Customer.findByCustnumLike("%$custnum")?.custId
        if (!custId) {
            throw new Exception("No customer found with Custnum == '$custnum'")
        }

        return custId
    }

    CustomerPhoto saveCustomerPhoto(Long custId, byte[] photoBytes) {
        new CustomerPhoto(custId: custId, photo: photoBytes).save(failOnError: true, flush: true)
    }

    void markPhotoAsDownloaded(Long id) {

        def response = restBuilder.put("$grailsApplication.config.cloudcard.apiURL/api/photos/$id") {
            header "X-Auth-Token", grailsApplication.config.cloudcard.accessToken
            contentType "application/json"
            body([ status: "DOWNLOADED" ] as JSON)
        }

        if (response.status != 200) {
            throw new Exception("Error marking photo #$id as DOWNLOADED.")
        }
    }

}
