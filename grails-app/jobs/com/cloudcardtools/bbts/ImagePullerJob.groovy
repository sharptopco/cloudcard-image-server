package com.cloudcardtools.bbts

class ImagePullerJob {

    CustomerPhotoService customerPhotoService
    CloudCardAPIService cloudCardAPIService

    static triggers = {
        simple startDelay:10000, repeatInterval: 600000
    }

    def execute() {
        log.info "Pulling images from $cloudCardAPIService.description"

        try {
            cloudCardAPIService.fetchApprovedPhotos().each { customerPhotoService.downloadPhoto(it) }
        } catch (Exception e) {
            log.error e.message
        }
    }

}
