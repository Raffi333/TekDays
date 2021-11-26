package tekdays

class Task {
    String title
    String notes
    Date dueDate
    TekUser assignedTo
    TekEvent event

    static belongsTo = TekEvent
    static constraints = {
        title blank: false
        notes blank: true, nullable: true, maxSize: 5000
        assignedTo nullable: true
        dueDate nullable: true
    }

}