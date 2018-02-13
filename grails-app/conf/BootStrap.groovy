import com.cloudcardtools.bbts.CloudCardImageServerService

class BootStrap {

    CloudCardImageServerService cloudCardImageServerService

    def init = { servletContext ->
        println "Starting up with polling ${cloudCardImageServerService.pollingEnabled ? "enabled" : "disabled"}."
    }
    def destroy = {
    }
}
