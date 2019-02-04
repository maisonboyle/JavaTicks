package uk.ac.cam.dab80.oop.tick3;

import java.io.*;
import java.net.*;
import java.util.*;

public class PatternStore {
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
		BufferedReader b = new BufferedReader(r);
		String line;
		Pattern p;
		while ( (line = b.readLine()) != null) {
			System.out.println(line);
			try{
				p = new Pattern(line);
			} catch (PatternFormatException e){
				System.out.println(e.getMessage());
				continue;
			}
			patterns.add(p);
			mapName.put(p.getName(),p);
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
        return new LinkedList<Pattern>(patterns);
	}

	public List<Pattern> getPatternsAuthorSorted() {
		Collections.sort(patterns,new Comparator<Pattern>() {
			public int compare(Pattern p1, Pattern p2){
				if (p1.getAuthor().equals(p2.getAuthor())){
					return p1.compareTo(p2);
				}
				return p1.getAuthor().compareTo(p2.getAuthor());
			}
		});
		return new LinkedList<Pattern>(patterns);
	}

	public List<Pattern> getPatternsByAuthor(String author) throws PatternNotFound {
        List<Pattern> entry = mapAuths.get(author);
        if (entry == null){
           throw new PatternNotFound();
        }
		Collections.sort(entry);
        return new LinkedList<Pattern>(entry);
  // TODO:  return a list of patterns from a particular author sorted by name
	}

	public Pattern getPatternByName(String name) throws PatternNotFound {
		Pattern entry = mapName.get(name);
		if (entry == null){
			throw new PatternNotFound();
		}
		return entry;
  // TODO: Get a particular pattern by name
	}

	public List<String> getPatternAuthors() {
		List<String> result = new LinkedList<String>(mapAuths.keySet());
		Collections.sort(result);
		return result;
  // TODO: Get a sorted list of all pattern authors in the store
	}

	public List<String> getPatternNames() {
		List<String> result = new LinkedList<String>(mapName.keySet());
		Collections.sort(result);
		return result;
  // TODO: Get a list of all pattern names in the store,
  // sorted by name
	}
}
