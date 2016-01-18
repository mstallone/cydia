package Nine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MovieGoersAdvance {
	private Map<String, Map<String, Integer>> map;
	
	public MovieGoersAdvance(){
		map = new HashMap<String, Map<String, Integer>>();
	}
	
	public void addMovie(String movie, String[] attendees){
		if(map.containsKey(movie)){
			Map<String, Integer> mapattendees = map.get(movie);
			for (String attendee : attendees) {
				if(map.get(movie).keySet().contains(attendee)){
					int timeswatched = mapattendees.get(attendee);
					timeswatched++;
					mapattendees.remove(attendee);
					mapattendees.put(attendee, timeswatched);
				}else mapattendees.put(attendee, 1);
			}
		}else{
			Map<String, Integer> mapattendees = new HashMap<String, Integer>();
			for (String attendee : attendees) mapattendees.put(attendee, new Integer(1));
			map.put(movie, mapattendees);
		}
	}
	
	public boolean hasSeen(String person, String movie){
		return (map.containsKey(movie) && map.get(movie).keySet().contains(person));
	}
	
	public void printMoviesSeen(String person){
		System.out.println("Movies seen:");
		Set<String> keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		while(iterator.hasNext()){
			String st = iterator.next();
			if(map.get(st).keySet().contains(person)) System.out.println(st + " " + map.get(st).get(person));
		}
		System.out.println("--END--");
	}
	
	public Map<String, Integer> peopleSeeingMovie(String movie){
		return map.get(movie);
	}
}
