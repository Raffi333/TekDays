package tekdays

class TekEvent {
    String city
    String name
    TekUser organizer
    String venue
    Date startDate
    Date endDate
    String description

    static def hasMany = [volunteers: TekUser, respondents: String, sponsors: Sponsorship, tasks: Task, messages: TekMessage]
    //static belongsTo = TekEvent

    static def constraints = {
        name()
        city()
        description(maxSize: 5000)
        organizer()
        venue()
        startDate()
        endDate()
       tasks nullable: true
        messages nullable: true
    }


//    @Override
//    String toString() {
//        "$name, $city"
//    }


    @Override
    public String toString() {
        return "TekEvent{" +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", organizer='" + organizer + '\'' +
                ", venue='" + venue + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }

}
