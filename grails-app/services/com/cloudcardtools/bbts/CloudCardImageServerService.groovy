package com.cloudcardtools.bbts

import org.codehaus.groovy.grails.commons.GrailsApplication

class CloudCardImageServerService {

    GrailsApplication grailsApplication

    boolean getPollingEnabled() {
        grailsApplication.config.imageserver.pollingEnabled
    }

}
