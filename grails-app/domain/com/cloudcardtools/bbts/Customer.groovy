package com.cloudcardtools.bbts

class Customer {

    Long custId
    String custnum

    static constraints = {
    }

    static mapping = {
        datasource 'bbts'
        table name: 'v_cust_id_custnum', schema: 'envision'
        id name: 'custId', generator: 'assigned'
        version false
    }
}

