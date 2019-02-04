package uk.ac.cam.dab80.oop.tick5;

import java.io.*;
import java.net.*;
import java.util.*;

public class PatternStore {
	// only sorted when request made, added in any order initially
    private List<Pattern> patterns = new LinkedList<>();
    private Map<String,List<Pattern>> mapAuths = new HashMap<>();
    private Map<String,Pattern> mapName = new HashMap<>();

	public PatternStore(String source) throws IOException {
		if (source.startsWith("http://") || source.startsWith("https://")) {
			loadFromURL(source);
		}
		else {
			loadFromDisk(source);
		}
	}
    
	public PatternStore(Reader source) throws IOException {
		load(source);
	}
    
	private void load(Reader r) throws IOException {
		// read from source line by line
		BufferedReader b = new BufferedReader(r);
		String line;
		Pattern p;
		while ( (line = b.readLine()) != null) {
			System.out.println(line);
			try{
				p = new Pattern(line);
			} catch (PatternFormatException e){
				// display issue then continue with remaining patterns
				System.out.println(e.getMessage());
				continue;
			}
			patterns.add(p);
			mapName.put(p.getName(),p);
			// need to check list is not null before trying to add to it
			List<Pattern> authorPatterns = mapAuths.get(p.getAuthor());
			if (authorPatterns == null){
				authorPatterns = new LinkedList<>();
				authorPatterns.add(p);
				mapAuths.put(p.getAuthor(),authorPatterns);
			}else{
				authorPatterns.add(p);
			}
		} 
	}
    
    
   private void loadFromURL(String url) throws IOException {
		URL destination = new URL(url);
		URLConnection conn = destination.openConnection();
		Reader r = new InputStreamReader(conn.getInputStream());
		load(r);
	}

   private void loadFromDisk(String filename) throws IOException {
        Reader r = new FileReader(filename);
        load(r);
   }

    public List<Pattern> getPatternsNameSorted() {
        Collections.sort(patterns);
		// new linkedList to avoid giving access to private collections
        return new LinkedList<Pattern>(patterns);
	}

	public List<Pattern> getPatternsAuthorSorted() {
		Collections.sort(patterns,new Comparator<Pattern>() {
			public int compare(Pattern p1, Pattern p2){
				// if authors equal, use natural name ordering
				if (p1.getAuthor().equals(p2.getAuthor())){
					return p1.compareTo(p2);
				}
				return p1.getAuthor().compareTo(p2.getAuthor());
			}
		});
		return new LinkedList<Pattern>(patterns);
	}

	public List<Pattern> getPatternsByAuthor(String author) throws PatternNotFound {
		// all patterns with given author
        List<Pattern> entry = mapAuths.get(author);
        if (entry == null){
           throw new PatternNotFound();
        }
		// natural ordering is by name
		Collections.sort(entry);
        return new LinkedList<Pattern>(entry);
	}

	public Pattern getPatternByName(String name) throws PatternNotFound {
		Pattern entry = mapName.get(name);
		if (entry == null){
			throw new PatternNotFound();
		}
		return entry;
	}

	public List<String> getPatternAuthors() {
		List<String> result = new LinkedList<String>(mapAuths.keySet());
		// hashMap unordered
		Collections.sort(result);
		return result;
	}

	public List<String> getPatternNames() {
		// return all keys in map of all patterns
		List<String> result = new LinkedList<String>(mapName.keySet());
		Collections.sort(result);
		return result;
	}
}
