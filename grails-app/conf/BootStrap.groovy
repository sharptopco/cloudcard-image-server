import com.cloudcardtools.bbts.Role
import com.cloudcardtools.bbts.User
import com.cloudcardtools.bbts.UserRole

class BootStrap {

    def init = { servletContext ->
        def userRole = Role.findByAuthority("ROLE_USER") ?: new Role(authority: "ROLE_USER").save(flush: true)
        def user = User.findByUsername("tst") ?: new User(username: "tst", password: "foo", enabled: true).save(flush: true)
        UserRole.create(user, userRole, true)
    }
    def destroy = {
    }
}
