package com.cloudcardtools.bbts

class Role {

    static final String READER = "ROLE_READER"
    static final String WRITER = "ROLE_WRITER"

    String authority

    static mapping = {
        cache true
    }

    static constraints = {
        authority blank: false, unique: true
    }
}
