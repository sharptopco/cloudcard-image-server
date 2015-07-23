package com.cloudcardtools.bbts

class Role {

    static final String READER = "ROLE_READER"
    static final String WRITER = "ROLE_WRITER"
    static final String ADMIN = "ROLE_ADMIN"

    String authority

    static mapping = {
        cache true
    }

    static constraints = {
        authority blank: false, unique: true
    }
}
