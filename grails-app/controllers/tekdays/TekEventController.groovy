package tekdays

import grails.transaction.Transactional
import org.hibernate.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class TekEventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TekEventController.class)
    TaskService taskService
    DatatablesSourceService datatablesSourceService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    def dataTablesRenderer() {
        def propertiesToRender = ["id", "name", "city", "organizer", "venue", "startDate", "endDate"]
        def entityName = TekEvent.class

//        println datatablesSourceService.dataTablesSource(propertiesToRender, entityName, params)
        render datatablesSourceService.dataTablesSource(propertiesToRender, entityName, params)
    }


    def index(Integer max) {
        LOGGER.info("message")
        params.max = Math.min(max ?: 10, 100)
        return respond(TekEvent.list(params), model: [tekEventInstanceCount: TekEvent.count()])
    }


    def show(Long id) {
        def tekEventInstance
        if (params.nickname) {
            tekEventInstance = TekEvent.findByNickname(params.nickname)
        } else {
            tekEventInstance = TekEvent.get(id)
        }
        if (!tekEventInstance) {
            if (params.nickname) {
                flash.message = "TekEvent not found with nickname ${params.nickname}"
            } else {
                flash.message = "TekEvent not found with id $id"
            }
            redirect(action: 'index')
            return
        }
        [tekEventInstance: tekEventInstance]
    }

    def create() {
//        return ["tekEventInstance": new TekEvent(params)]
        respond new TekEvent(params)
    }


    @Transactional
    def save(TekEvent tekEventInstance) {
        if (tekEventInstance == null) {
            notFound()
            return
        }

        if (tekEventInstance.hasErrors()) {
            respond tekEventInstance.errors, view: 'create'
            return
        }

        tekEventInstance.save(flush: true)
        taskService.addDefaultTasks(tekEventInstance)


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tekEvent.label', default: 'TekEvent'), tekEventInstance.id])
                redirect(tekEventInstance)
            }
            '*' { respond(tekEventInstance, [status: CREATED]) }
        }
    }

    def edit(TekEvent tekEventInstance) {
        respond tekEventInstance
    }

    @Transactional
    def update(TekEvent tekEventInstance) {
        if (tekEventInstance == null) {
            notFound()
            return
        }

        if (tekEventInstance.hasErrors()) {
            respond tekEventInstance.errors, view: 'edit'
            return
        }

        tekEventInstance.save flush: true


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'TekEvent.label', default: 'TekEvent'), tekEventInstance.id])
                redirect tekEventInstance
            }
            '*' { respond tekEventInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(TekEvent tekEventInstance) {

        if (tekEventInstance == null) {
            notFound()
            return
        }

        tekEventInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'TekEvent.label', default: 'TekEvent'), tekEventInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tekEvent.label', default: 'TekEvent'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def volunteer() {


        def event = TekEvent.get(params.id)
        println event
        event.addToVolunteers(session.user)
        event.save()
        println event

        redirect(action: "show", id: event.id)
    }
}
