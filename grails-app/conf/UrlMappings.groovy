class UrlMappings {

    static mappings = {


        "/user/$id?"(controller: 'tekUser', action: 'show') {
            format = 'detailed'
            data = [1, 2, 3, 5, 9]
        }
        // or         "/user/$action?/$id?(.$format)?"(controller: 'tekUser')
//        or
//        "/grailsblogs/$year/$month/$day/$entry_name?" {
//            controller = 'authorization'
//            action = 'test'
//            constraints {
//            }
//
//        }


        "/api" {
            controller = 'authorization'
            action = [GET   : 'm1',
                      POST  : 'm2',
                      PUT   : 'm3',
                      DELETE: 'm4',
            ]
        }


        "/$controller/$action?/$id?(.$format)?" {
            constraints {
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')
        "404"(view:'/notFound')
    }
}
