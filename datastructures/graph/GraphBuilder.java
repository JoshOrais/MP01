public class GraphBuilder {
	//FORMAT FOR CREATING GRAPHS WOULD BE :
	//NAME EDGE EDGE EDGE EDGE //newline
	//NAME EDGE EDGE EDGE 
	//public static Graph buildGraph(String graph) {
	//	BufferedReader br = new BufferedReader( new StringReader(graph) );
	//	
	//	
	//}
	//TODO 
	// USE JSON FILES INSTEAD
	
	
	public static String loadFile(String fileName) throws Exception {
		String result;
        try (InputStream in = Class.forName(ResourceLoader.class.getName()).getResourceAsStream(fileName);
            Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
}