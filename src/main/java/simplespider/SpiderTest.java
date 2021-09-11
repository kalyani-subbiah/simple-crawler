package simplespider;
import java.io.IOException;

public class SpiderTest {
	/** 
	 * This is our test. It creates the spider (which creates the
	 * spider legs) and crawls the web.
	 * 
	 * @param args
	 * 				- not used
	 * 
	 */
	
	public static void main(String[] args) throws IOException
	{
		Spider spider = new Spider();
		spider.writeSearchResults("http://arstechnica.com", "investor", "resources/results.csv");
	}
}
	
