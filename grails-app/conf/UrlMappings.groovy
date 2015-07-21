class UrlMappings {

    static mappings = {
        "/${controller}s" {
            action = [GET: "index", POST: "save"]
            format = "json"
        }

        "/${controller}s/$id" {
            action = [GET: "show", PUT: "update", DELETE: "delete"]
            format = "json"
        }

        "/$controller/$action?/$id?" {}

        "/"(view: "/index")
        "500"(view: '/error')
    }
}
