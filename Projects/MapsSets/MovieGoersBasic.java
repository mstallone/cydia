package Nine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MovieGoersBasic {
	private Map<String, Set<String>> map;
	
	public MovieGoersBasic(){
		map = new HashMap<String, Set<String>>();
	}
	
	public void addMovie(String movie, String[] attendees){
		if(map.containsKey(movie)){
			Set<String> setattendees = map.get(movie);
			for (String attendee : attendees) setattendees.add(attendee);
		}else{
			Set<String> setattendees = new TreeSet<String>();
			for (String attendee : attendees) setattendees.add(attendee);
			map.put(movie, setattendees);
		}
	}
	
	public boolean hasSeen(String person, String movie){
		return (map.containsKey(movie) && map.get(movie).contains(person));
	}
	
	public void printMoviesSeen(String person){
		System.out.println("Movies seen:");
		Set<String> keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		while(iterator.hasNext()){
			String st = iterator.next();
			if(map.get(st).contains(person)) System.out.println(st);
		}
		System.out.println("--END--");
	}
	
	public Set<String> peopleSeeingMovie(String movie){
		return map.get(movie);
	}
}
