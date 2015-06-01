package com.cloudcardtools.bbts

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER'])
class UserController {

    static scaffold = true
}
