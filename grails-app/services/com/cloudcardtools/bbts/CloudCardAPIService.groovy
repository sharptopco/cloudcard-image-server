package com.cloudcardtools.bbts

import grails.transaction.Transactional

import org.codehaus.groovy.grails.commons.GrailsApplication

    import grails.converters.JSON
    import grails.plugins.rest.client.RestBuilder
    import grails.plugins.rest.client.RestResponse

@Transactional
class CloudCardAPIService {

    GrailsApplication grailsApplication
    RestBuilder restBuilder = new RestBuilder()
    String apiURL = grailsApplication.config.cloudcard.apiURL
    String accessToken = grailsApplication.config.cloudcard.accessToken

    String getDescription() {
        "CloudCard API ($apiURL) using Access Token [$accessToken]"
    }

    List fetchApprovedPhotos() {
        RestResponse response = restBuilder.get("$apiURL/api/photos?status=APPROVED") {
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

    void markPhotoAsDownloaded(Long id) {
        RestResponse response = restBuilder.put("$apiURL/api/photos/$id") {
            header "X-Auth-Token", accessToken
            contentType "application/json"
            body([ status: "DOWNLOADED" ] as JSON)
        }

        if (response.status != 200) {
            throw new Exception("Error marking photo #$id as DOWNLOADED.")
        }
    }

}
