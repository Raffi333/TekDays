package tekdays

import grails.async.Promise
import grails.transaction.Transactional
import groovy.transform.Synchronized
import net.sf.ehcache.transaction.xa.OptimisticLockFailureException
import org.hibernate.Cache
import org.hibernate.FlushMode
import org.hibernate.LazyInitializationException
import org.hibernate.SessionFactory
import org.hibernate.criterion.Order
import org.hibernate.engine.profile.Association
import org.springframework.context.annotation.Scope
import org.springframework.dao.OptimisticLockingFailureException

import java.lang.reflect.Array
import java.util.stream.Collector

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class TekUserController {


    EnversService enversService
    SessionFactory sessionFactory
    EmailService emailService
    TimerService timerService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Transactional
//    @Synchronized('obj')
    def index(Integer max) {

        timerService.TimerByRH(1,0,0,0)


//        [
//                allEntity   : [
//                        TekUser.findAll(),
//                        TekUser.getAll(),
//                        TekUser.list(),
//                ],
//                byId        : [
//                        TekUser.get(1),
//                        TekUser.findById(1)
//
//                ],
//                byObject    : [
//                        TekUser.findAll(new TekUser())
//                ],
//                byParams    : [
//                        "max",
//                        "offset",
//                        "sort",
//                        "order"
//                ],
//                byLike      : [
//                        TekUser.findAllByEmailLike("%mrbill@email.com%"),
//                        TekUser.findAllByEmailLike("mrbill@email.com%"),
//                        TekUser.findAllByEmailLike("%mrbill@email.com"),
//
//                ],
//                T           : [
//                        Book.findAllByTitle("The Shining",
//                                [max: 10, sort: "title", order: "desc", offset: 100]),
//                        Book.findAllByTitleAndAuthor("The Sum of All Fears", "Tom Clancy"),
//
//                        TekUser.findAllByIdBetween(1, 5),
//                        //select * from tek_user where tek_user.id > 0 and tek_user.id <= 5
//
//                        TekUser.findAllByIdGreaterThan(3),
//                        //select * from tek_user where id > 3
//
//                        TekUser.findAllByIdGreaterThanEquals(3),
//                        //select * from tek_user where id >= 3
//
//                        TekUser.findAllByIdLessThan(3),
//                        //select * from tek_user where id < 3
//
//                        Book.findAllByTitleLike("%Hobbit%"),
//
//                        Book.findAllByTitleIlike("%Hobbit%"),// ignore case
//
//                        TekUser.findAllByIdNotEqual(3)
//
//                        Book.findAllByReleaseDateIsNull(),
//
//                        Book.findAllByReleaseDateIsNotNull(),
//
//                        Book.findAllPaperbackByAuthor("Douglas Adams"),
//
//                        Book.findAllNotPaperbackByAuthor("Douglas Adams"),
//
//                        Book.findAllByAuthorInList(["Douglas Adams", "Hunter S. Thompson"]),
//
//                          TekUser.countByIdBetween(1,3)
//                ],
//                executeQuery: [
//                        TekUser.executeQuery("from TekUser where id >= 3"),
//                        TekUser.findAll("from TekUser where id >= 3"),
//                        TekUser.findAll('from TekUser as t where t.email = ?', ['mrbill@email.com']),
//                        TekUser.findAll('from TekUser as t where t.email = :email', [email: 'mrbill@email.com'])
//                ]
//
//        ]

//        synchronized (this) {
//        long s = System.currentTimeMillis()
//        def f1 =TekUser.get(1)
//
//
//
//        f1.save()
//        println ".>>>>>>>>>>>>>>>>>>>>>>${k}"
//
//        render "${k++}->${m} ->>> ${System.currentTimeMillis() - s} Ms\n"


//         f1.properties.each { println it.value}

        params.max = Math.min(max ?: 5, 100)
        respond TekUser.list(params), model: [tekUserInstanceCount: TekUser.count()]
//        }
    }


    def show(TekUser tekUserInstance) {
        if (params.id == null) {
            redirect(action: "index")
            return
        }

        respond tekUserInstance
    }

    def create() {
        respond new TekUser(params)
    }

    @Transactional
    def save(TekUser tekUserInstance) {
        if (tekUserInstance == null) {
            notFound()
            return
        }

        if (tekUserInstance.hasErrors()) {
            respond tekUserInstance.errors, view: 'create'
            return
        }

        tekUserInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tekUser.label', default: 'TekUser'), tekUserInstance.id])
                redirect tekUserInstance
            }
            '*' { respond tekUserInstance, [status: CREATED] }
        }
    }

    def edit(TekUser tekUserInstance) {
        respond tekUserInstance
    }

    @Transactional
    def update(TekUser tekUserInstance) {
        if (tekUserInstance == null) {
            notFound()
            return
        }

        if (tekUserInstance.hasErrors()) {
            respond tekUserInstance.errors, view: 'edit'
            return
        }

        tekUserInstance.save flush: true
        if (session?.user?.id == tekUserInstance.id)
            session.user = tekUserInstance

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'TekUser.label', default: 'TekUser'), tekUserInstance.id])
                redirect tekUserInstance
            }
            '*' { respond tekUserInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(TekUser tekUserInstance) {

        if (tekUserInstance == null) {
            notFound()
            return
        }

        tekUserInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'TekUser.label', default: 'TekUser'), tekUserInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tekUser.label', default: 'TekUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }


}
