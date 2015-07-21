package com.cloudcardtools.bbts

class ImageKey {

    Long custId
    String key

    static constraints = {
        custId blank: false
        key blank: false
    }
}
