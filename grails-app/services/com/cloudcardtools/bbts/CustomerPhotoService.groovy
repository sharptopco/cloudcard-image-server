package com.cloudcardtools.bbts

import grails.transaction.Transactional

@Transactional
class CustomerPhotoService {

    CloudCardAPIService cloudCardAPIService

    void downloadPhoto(String custnum, Integer photoId, String photoPublicKey) {
        if (!custnum) {
            //TODO: make some way of flagging persons without identifiers.
            throw new Exception("User does not have an identifier defined")
        }

        Long custId = Customer.findByCustnumLike("%$custnum")?.custId
        if (!custId) {
            throw new Exception("No customer found with Custnum == '$custnum'")
        }

        new CustomerPhoto(
            custId: custId,
            photo: cloudCardAPIService.fetchPhotoBytes(photoPublicKey)
        ).save(failOnError: true, flush: true)

        cloudCardAPIService.markPhotoAsDownloaded(photoId)
    }

}
