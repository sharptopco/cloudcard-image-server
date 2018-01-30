package com.cloudcardtools.bbts

import grails.transaction.Transactional

@Transactional
class CustomerPhotoService {

    CloudCardAPIService cloudCardAPIService

    void downloadPhoto(Map photo) {
        downloadPhoto(photo.person.identifier, photo.id, photo.publicKey, photo.person.username)
    }

    void downloadPhoto(String custnum, Integer photoId, String photoPublicKey, String username) {
        if (!custnum) {
            throw new Exception("User $username does not have an identifier defined")
        }

        Long custId = Customer.findByCustnumLike("%$custnum")?.custId
        if (!custId) {
            throw new Exception("No Customer record found for user $username with custnum=='$custnum'")
        }

        new CustomerPhoto(
            custId: custId,
            photo: cloudCardAPIService.fetchPhotoBytes(photoPublicKey)
        ).save(failOnError: true, flush: true)

        cloudCardAPIService.markPhotoAsDownloaded(photoId)
    }

}
