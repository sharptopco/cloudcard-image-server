package com.cloudcardtools.bbts

class ImagePullerJob {

    CustomerPhotoService customerPhotoService
    CloudCardAPIService cloudCardAPIService

    static triggers = {
        simple startDelay:10000, repeatInterval: 60000
    }

    def execute() {
        cloudCardAPIService.fetchPhotosReadyForDownload().each { photo ->
            try {
                log.info "Downloading Photo $photo.id from $cloudCardAPIService.description"
                customerPhotoService.downloadPhoto(photo)
                log.info "... Success"
            } catch (Exception e) {
                log.error e.message
            }
        }
    }

}
