package com.cloudcardtools.bbts

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ["custId"])
class CustomerPhoto {

    Long custId
    byte[] photo

    static constraints = {
        photo maxSize: 16777216
    }

    static mapping = {
        datasource 'bbts'
        table schema: 'envision'
        id name: 'custId', generator: 'assigned'
        photo sqlType: 'blob'
        version false
    }
}
