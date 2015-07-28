/**
 * Copyright (c) 2015 Cloud Card LLC
 *
 * This file is part of CloudCard Image Server.
 *
 * CloudCard Image Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CloudCard Image Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CloudCard Image Server.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.cloudcardtools.bbts

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.http.HttpStatus

@Secured(['ROLE_ADMIN'])
class UserController {

    static scaffold = true

    @Transactional
    def save(User userInstance) {
        userInstance.password = params.newPassword
        if (userInstance.password != params.confirmPassword) {
            flash.warning = "Sorry. The passwords do not match."
            render(view: "create", model: [userInstance: new User(params)])
            return
        }

        //create and save userInstance
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

        addOrRemoveRoles(userInstance)

        flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])
        redirect(action: "show", id: userInstance.id)
    }

    @Transactional
    def update(User userInstance) {
        log.error params
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "index")
            return
        }

        if (userInstance.hasErrors()) {
            respond userInstance.errors, view: 'edit'
            return
        }

        if (params.newPassword) {
            if (params.newPassword != params.confirmPassword) {
                flash.warning = "Sorry. The passwords do not match."
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
            userInstance.password = params.newPassword
        }

        userInstance.save flush: true

        addOrRemoveRoles(userInstance)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'User.label', default: 'User'), userInstance.id])
                redirect userInstance
            }
            '*' { respond userInstance, [status: HttpStatus.OK] }
        }
    }

    def delete(User userInstance) {
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
            redirect(action: "index")
            return
        }

        try {
            UserRole.findAllByUser(userInstance).each { it.delete(flush: true) }
            userInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
            redirect(action: "index")
        }
        catch (Exception e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
            redirect(action: "show", id: userInstance.id)
        }
    }

    /*** Private Helper Methods ***/

    /**
     * Adds roles to a user based on the params map
     *
     * @param userInstance
     */
    private void addOrRemoveRoles(User userInstance) {
        log.error params
        Role.list().each { Role role ->
            log.error "checking $role.authority"
            //add role
            if (params[role.authority] && !UserRole.exists(userInstance.id, role.id)) {
                log.error "adding $role.authority"
                new UserRole(user: userInstance, role: role).save(failOnError: true)
            }

            //remove role
            if (!params[role.authority] && UserRole.exists(userInstance.id, role.id)) {
                UserRole.findByUserAndRole(userInstance, role).delete()
            }
        }
    }

}
