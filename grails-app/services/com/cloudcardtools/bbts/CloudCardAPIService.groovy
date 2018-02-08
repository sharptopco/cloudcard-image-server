package com.cloudcardtools.bbts

import grails.transaction.Transactional

import org.codehaus.groovy.grails.commons.GrailsApplication

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class CloudCardAPIService {

    static final String READY_FOR_DOWNLOAD = "READY_FOR_DOWNLOAD"

    GrailsApplication grailsApplication
    RestBuilder restBuilder = new RestBuilder()

    String getApiURL() {
        grailsApplication.config.cloudcard.apiURL
    }
    String getAccessToken() {
        grailsApplication.config.cloudcard.accessToken
    }

    String getDescription() {
        "CloudCard API ($apiURL) using Access Token [$accessToken]"
    }

    List fetchPhotosReadyForDownload() {
        RestResponse response = restBuilder.get("$apiURL/api/photos?status=$READY_FOR_DOWNLOAD") {
            header "X-Auth-Token", accessToken
        }

        if (response.status != 200) {
            throw new Exception("Error polling for downloadable images: $response.text")
        }

        return response.json
    }

    byte[] fetchPhotoBytes(String publicKey) {
         new HTTPBuilder("$apiURL/api/photos/$publicKey/bytes").request(Method.GET) {
             response.success = { resp, binary ->
                 return binary.bytes
             }
         }
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
