<%@ page import="tekdays.TekEvent" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'tekEvent.label', default: 'TekEvent')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
    <style>

.ddd{
    margin-top: 200px;
    display: flex;
    justify-content: center;
    align-items: center;
}
#modal_rh{

display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.5);
}


    </style>
</head>

<body>


<a href="#show-tekEvent" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                               default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
        <li><g:link class="list" controller="dashboard" action="dashboard"
                    id="${tekEventInstance.id}">Event Dashboard</g:link></li>
        <li><g:volunteerButton eventId="${tekEventInstance.id}"/></li>
    </ul>
</div>

<div id="show-tekEvent" class="content scaffold-show" role="main">
    <h1>${tekEventInstance?.name}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list tekEvent">

        <g:if test="${tekEventInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="tekEvent.name.label"
                                                                        default="Name"/></span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${tekEventInstance}"
                                                                                        field="name"/></span>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.city}">
            <li class="fieldcontain">
                <span id="city-label" class="property-label">
                    Location
                </span>

                <span class="property-value" aria-labelledby="city-label">
                    ${tekEventInstance.venue} ,
                    <g:fieldValue bean="${tekEventInstance}" field="city"/>
                </span>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.description}">
            <li class="fieldcontain">
                <span id="description-label" class="property-label"><g:message code="tekEvent.description.label"
                                                                               default="Description"/></span>

                <span class="property-value" aria-labelledby="description-label"><g:fieldValue
                        bean="${tekEventInstance}" field="description"/></span>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.organizer}">
            <li class="fieldcontain">
                <span id="organizer-label" class="property-label"><g:message code="tekEvent.organizer.label"
                                                                             default="Organizer"/></span>

                <span class="property-value" aria-labelledby="organizer-label">
                    <g:link controller="tekUser" action="show"
                            id="${tekEventInstance?.organizer?.id}">${tekEventInstance?.organizer?.encodeAsHTML()}</g:link>
                </span>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.venue}">
            <li class="fieldcontain">
                <span id="venue-label" class="property-label"><g:message code="tekEvent.venue.label"
                                                                         default="Venue"/></span>

                <span class="property-value" aria-labelledby="venue-label"><g:fieldValue bean="${tekEventInstance}"
                                                                                         field="venue"/></span>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.startDate}">
            <li class="fieldcontain">
                <span id="startDate-label" class="property-label"><g:message code="tekEvent.startDate.label"
                                                                             default="Start Date"/></span>

                <span class="property-value" aria-labelledby="startDate-label">
                    <g:formatDate format="MMMM dd, yyyy"
                                  date="${tekEventInstance?.startDate}"/></span>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.endDate}">
            <li class="fieldcontain">
                <span id="endDate-label" class="property-label"><g:message code="tekEvent.endDate.label"
                                                                           default="End Date"/></span>

                <span class="property-value" aria-labelledby="endDate-label"><g:formatDate
                        date="${tekEventInstance?.endDate}"/></span>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.tasks}">
            <li class="fieldcontain">
                <span id="tasks-label" class="property-label"><g:message code="tekEvent.tasks.label"
                                                                         default="Tasks"/></span>

                <g:each in="${tekEventInstance.tasks}" var="t">
                    <span class="property-value" aria-labelledby="tasks-label"><g:link controller="task" action="show"
                                                                                       id="${t.id}">${t?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.messages}">
            <li class="fieldcontain">
                <span id="messages-label" class="property-label"><g:message code="tekEvent.messages.label"
                                                                            default="Messages"/></span>

                <g:each in="${tekEventInstance.messages}" var="m">
                    <span class="property-value" aria-labelledby="messages-label"><g:link controller="tekMessage"
                                                                                          action="show"
                                                                                          id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.respondents}">
            <li class="fieldcontain">
                <span id="respondents-label" class="property-label"><g:message code="tekEvent.respondents.label"
                                                                               default="Respondents"/></span>

                <span class="property-value" aria-labelledby="respondents-label"><g:fieldValue
                        bean="${tekEventInstance}" field="respondents"/></span>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.sponsors}">
            <li class="fieldcontain">
                <span id="sponsors-label" class="property-label">
                    <g:message code="tekEvent.sponsors.label" default="Sponsors"/>
                </span>

                <g:each in="${tekEventInstance.sponsors}" var="s">
                    <span class="property-value" aria-labelledby="sponsors-label">
                        <g:link controller="sponsorship" action="show"
                                id="${s.id}">${s?.sponsor?.encodeAsHTML()}</g:link>
                    </span>
                </g:each>

            </li>
        </g:if>

        <g:if test="${tekEventInstance?.volunteers}">
            <li class="fieldcontain">
                <span id="volunteers-label" class="property-label"><g:message code="tekEvent.volunteers.label"
                                                                              default="Volunteers"/></span>

                <g:each in="${tekEventInstance.volunteers}" var="v">
                    <span class="property-value" aria-labelledby="volunteers-label"><g:link controller="tekUser"
                                                                                            action="show"
                                                                                            id="${v.id}">${v?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>


        <g:if test="${tekEventInstance?.messages}">
            <li class="fieldcontain">
                <span id="messages-label" class="property-label"><g:message
                        code="tekEvent.messages.label" default="Messages"/></span>
                <span class="property-value" aria-labelledby="messages-label">
                    <g:link controller="tekMessage" action="index"
                            id="${tekEventInstance.id}">
                        View Messages
                    </g:link></span>
            </li>
        </g:if>

    </ol>
    <g:form url="[resource: tekEventInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${tekEventInstance}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>


<div id="modal_rh">
    <div class="ddd">
        <div id="volunteerDialog" title="Volunteer for ${tekEventInstance.name}"
             style="position: fixed;background: #f6f7f8;padding: 50px;border-radius: 25px">
            <g:form name="volunteerForm" action="volunteer">
                <g:hiddenField name="id" value="${tekEventInstance.id}"/>
                <p>Welcome to the team! Your help will make a huge difference.</p>
                <button class="btn btn-outline-success">ok</button>

                <div id="cancel_div" class="btn btn-outline-danger">cancel</div>
            </g:form>
        </div>
    </div>

</div>

<script type="text/javascript">
    var x = 0;
    $(document).ready(function () {
        $('#volunteerDialog').hide();
        $("#volunteerButton").click(function () {
            $('#volunteerDialog').show(100);
            $('#modal_rh').show(100);
        });  $("#cancel_div").click(function () {
            $('#volunteerDialog').hide();
            $('#modal_rh').hide();
        });
    });
</script>

</body>
</html>
