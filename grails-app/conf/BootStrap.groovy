import com.cloudcardtools.bbts.Role
import com.cloudcardtools.bbts.User
import com.cloudcardtools.bbts.UserRole

class BootStrap {

    def init = { servletContext ->
        if (!User.count()) {
            def readerRole = Role.findByAuthority(Role.READER) ?: new Role(authority: Role.READER).save(flush: true)
            def writerRole = Role.findByAuthority(Role.WRITER) ?: new Role(authority: Role.WRITER).save(flush: true)
            def user = new User(username: "test", password: ".", enabled: true).save(flush: true)
            UserRole.create(user, readerRole, true)
            UserRole.create(user, writerRole, true)
        }
    }
    def destroy = {
    }
}
