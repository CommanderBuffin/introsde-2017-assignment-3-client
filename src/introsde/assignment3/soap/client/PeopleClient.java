package introsde.assignment3.soap.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import introsde.assignment3.soap.ws.PeopleService;
import introsde.assignment3.soap.ws.Activity;
import introsde.assignment3.soap.ws.ActivityType;
import introsde.assignment3.soap.ws.People;
import introsde.assignment3.soap.ws.Person;
import introsde.assignment3.soap.ws.Person.Activities;

public class PeopleClient{
	static PeopleService service;
	static People people;
	
    public static void main(String[] args) throws Exception {
        service = new PeopleService();
        people = service.getPeopleImplPort();
        
        String r = "";
        PrintWriter writer = new PrintWriter("assignment3.log", "UTF-8");
        
        r+=callWSDL()+"\r\n";
        r+=call1();
        r+=call2();
        r+=call3();
        r+=call5();
        r+=call6();
        r+=call7();
        r+=call8();
        r+=call9();
        r+=call10();
        r+=call11();
        r+=call12();
        
        writer.println(r);
        writer.close();
    }
    
    public static String callWSDL() {
    	String r = "";
    	URL url = service.getWSDLDocumentLocation();
    	r+="WSDL URL: " + url.toString()+"\r\n\r\n";
    	try {
			URLConnection uc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String line;
			while((line=in.readLine())!=null) {
				r+=line+"\r\n";
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return r;
    }
    
    public static String call1() {
    	String r = "";
    	r+="Method #1: readPersonList() => List<Person>"+"\r\n";
    	r+="Parameters: ---\r\n";
    	r+="Response: \r\n\r\n";
    	List<Person> pp = people.readPersonList();
    	for(Person p : pp) {
        	r+=printPerson(p);
        }
    	return r;
    }
    
    public static String call2() {
    	String r = "";
    	r+="Method #2: readPerson(Long id) => Person"+"\r\n";
    	r+="Parameters: id=1\r\n";
    	r+="Response: \r\n\r\n";
    	Person p = people.readPerson(1);
    	r+=printPerson(p);
    	return r;
    }
    
    public static String call3() {
    	String r = "";
    	r+="Method #3: updatePerson(Person p) => Person"+"\r\n";
    	
    	Person p = new Person();
    	p.setId(4l);
    	p.setLastname("Rossi");
    	String xmlString="";
    	JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Person.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    	StringWriter sw = new StringWriter();
	    	jaxbMarshaller.marshal(new JAXBElement<Person>(new QName("uri","local"), Person.class, p), sw);
	    	xmlString = sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	r+="Parameters: "+xmlString+"\r\n";
    	r+="Response: \r\n\r\n";
    	Person rp = people.updatePerson(p);
    	r+=printPerson(rp);
    	return r;
    }

    public static String call4() {
    	String r = "";
    	r+="Method #4: createPerson(Person p) => Person"+"\r\n";
    	
    	Person p = new Person();
    	p.setLastname("Bianchi");
    	p.setFirstname("Giancarlo");
    	p.setBirthdate("16-09-1990");
    	Activity a = new Activity();
    	a.setName("Sleep");
    	a.setDescription("Go to sleep late");
    	a.setPlace("Home");
    	a.setRating(3);
    	a.setStartdate("07-12-2017 01:28:00.0");
    	ActivityType at = new ActivityType();
    	at.setName("Living");
    	a.setActivityType(at);
    	Activities aa = new Activities();
    	aa.getActivity().add(a);
    	p.setActivities(aa);
    	
    	String xmlString="";
    	JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Person.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    	StringWriter sw = new StringWriter();
	    	jaxbMarshaller.marshal(new JAXBElement<Person>(new QName("uri","local"), Person.class, p), sw);
	    	xmlString = sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	r+="Parameters: "+xmlString+"\r\n";
    	r+="Response: \r\n\r\n";
    	Person rp = people.createPerson(p);
    	r+=printPerson(rp);
    	return r;
    }
    
    public static String call5() {
    	String r = "";
    	r+="Method #5: deletePerson(Long id)"+"\r\n";
    	r+="Parameters: id=6\r\n";
    	r+="Response: \r\n\r\n";
    	people.deletePerson(6);
    	r+="DELETED?\r\n\r\n";
    	return r;
    }
    
    public static String call6() {
    	String r = "";
    	r+="Method #6: readPersonPreferences(Long id, String activity_type) => List<Activity>"+"\r\n";
    	r+="Parameters: id=1, activity_type=\"Game\"\r\n";
    	r+="Response: \r\n\r\n";
    	List<Activity> aa = people.readPersonPreferences(1, "Game");
    	for(Activity a : aa) {
        	r+=printActivity(a,false);
        	r+="\r\n";
        }
    	return r;
    }
    
    public static String call7() {
    	String r = "";
    	r+="Method #7: readPreferences() => List<Activity>"+"\r\n";
    	r+="Parameters: ---\r\n";
    	r+="Response: \r\n\r\n";
    	List<Activity> aa = people.readPreferences();
    	for(Activity a : aa) {
        	r+=printActivity(a,false);
        }
    	return r;
    }
    
    public static String call8() {
    	String r = "";
    	r+="Method #8: readPersonPreferences(Long id, Long activity_id) => Activity"+"\r\n";
    	r+="Parameters: id=1, activity_id=1\r\n";
    	r+="Response: \r\n\r\n";
    	Activity a = people.readPersonPreferences2(1l, 1l);
        r+=printActivity(a,false);
        r+="\r\n";
    	return r;
    }
    
    public static String call9() {
    	String r = "";
    	r+="Method #9: savePersonPreferences(Long id, Activity activity)"+"\r\n";
    	
    	Activity a = new Activity();
    	a.setName("Assignment3 developement");
    	a.setDescription("Assignment3 SOAP client developement");
    	a.setPlace("Home");
    	a.setRating(5);
    	a.setStartdate("07-12-2017 01:30:00.0");
    	ActivityType at = new ActivityType();
    	at.setName("Programming");
    	a.setActivityType(at);
    	
    	String xmlString="";
    	JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Activity.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    	StringWriter sw = new StringWriter();
	    	jaxbMarshaller.marshal(new JAXBElement<Activity>(new QName("uri","local"), Activity.class, a), sw);
	    	xmlString = sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	r+="Parameters: id=1, "+xmlString+"\r\n";
    	r+="Response: \r\n\r\n";
    	people.savePersonPreferences(1l,a);
    	r+="CREATED?\r\n\r\n";
    	return r;
    }
    
    public static String call10() {
    	String r = "";
    	r+="Method #10: updatePersonPreferences(Long id, Activity activity) => Activity"+"\r\n";
    	
    	Activity a = new Activity();
    	a.setId(6l);
    	a.setName("Assignment3 developement");
    	a.setDescription("Assignment3 SOAP server developement");
    	a.setPlace("Home");
    	a.setRating(4);
    	a.setStartdate("07-12-2017 00:30:00.0");
    	ActivityType at = new ActivityType();
    	at.setName("Programming");
    	a.setActivityType(at);
    	
    	String xmlString="";
    	JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Activity.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    	StringWriter sw = new StringWriter();
	    	jaxbMarshaller.marshal(new JAXBElement<Activity>(new QName("uri","local"), Activity.class, a), sw);
	    	xmlString = sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	r+="Parameters: id=1, "+xmlString+"\r\n";
    	r+="Response: \r\n\r\n";
    	Activity ra =people.updatePersonPreferences(1l,a);
    	if(ra==null)
    		return r+"Activity not found\r\n\r\n";
    	r+=printActivity(ra,false)+"\r\n";
    	return r;
    }
    
    public static String call11() {
    	String r = "";
    	r+="Method #11 (Extra): evaluatePersonPreferences(Long id, Activity activity, int value) => Activity"+"\r\n";
    	
    	Activity a = new Activity();
    	a.setId(6l);
    	
    	String xmlString="";
    	JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Activity.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    	StringWriter sw = new StringWriter();
	    	jaxbMarshaller.marshal(new JAXBElement<Activity>(new QName("uri","local"), Activity.class, a), sw);
	    	xmlString = sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	r+="Parameters: id=1, value=5, "+xmlString+"\r\n";
    	r+="Response: \r\n\r\n";
    	Activity ra =people.evaluatePersonPreferences(1l,a,5);
    	if(ra==null)
    		return r+"Activity not found\r\n\r\n";
    	r+=printActivity(ra,false)+"\r\n";
    	return r;
    }
    
    public static String call12() {
    	String r = "";
    	r+="Method #12 (Extra): getBestPersonPreference(Long id) => List<Activity>"+"\r\n";
    	r+="Parameters: id=1\r\n";
    	r+="Response: \r\n\r\n";
    	List<Activity> aa = people.getBestPersonPreferences(1);
    	for(Activity a : aa) {
        	r+=printActivity(a,false);
        }
    	return r;
    }
    
    public static String printPerson(Person p) {
    	String r = "";
    	r+="PERSON\r\n"
    			+ "Id: " + p.getId() + "\r\n"
    			+ "Firstname: " + p.getFirstname()+"\r\n"
    			+ "Lastname: " + p.getLastname() + "\r\n"
    			+ "Birthdate: " + p.getBirthdate() + "\r\n";
    	Person.Activities aa = p.getActivities();
    	for(Activity a : aa.getActivity()) {
    		r+="\t"+printActivity(a,true);
    	}
    	return r+"\r\n";
    }
    
    public static String printActivity(Activity a, boolean indent) {
    	String r = "";
    	if(indent) {
	    	r+="ACTIVITY\r\n"
	    			+ "\tId: " + a.getId() +"\r\n"
	    			+ "\tName: " + a.getName() + "\r\n"
	    			+ "\tDescription: " + a.getDescription() + "\r\n"
	    			+ "\tPlace: " + a.getPlace() + "\r\n"
	    			+ "\tRating: " + a.getRating() + "\r\n"
	    			+ "\tStartdate: " + a.getStartdate() + "\r\n"
	    			+ "\tActivity type: " + a.getActivityType().getName() + "\r\n";
    	}
    	else
    	{
    		r+="ACTIVITY\r\n"
	    			+ "Id: " + a.getId() +"\r\n"
	    			+ "Name: " + a.getName() + "\r\n"
	    			+ "Description: " + a.getDescription() + "\r\n"
	    			+ "Place: " + a.getPlace() + "\r\n"
	    			+ "Rating: " + a.getRating() + "\r\n"
	    			+ "Startdate: " + a.getStartdate() + "\r\n"
	    			+ "Activity type: " + a.getActivityType().getName() + "\r\n";
    	}
    	return r+"\r\n";
    }
}