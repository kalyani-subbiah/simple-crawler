package simplespider;

import java.io.IOException;
import java.io.FileWriter;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class Spider
{
  private static final int MAX_PAGES_TO_SEARCH = 10;
  private Set<String> pagesVisited = new HashSet<String>();
  private List<String> pagesToVisit = new LinkedList<String>();


  /**
   * Our main launching point for the Spider's functionality. Internally it creates spider legs
   * that make an HTTP request and parse the response (the web page).
   * 
   * @param url
   *            - The starting point of the spider
   * @param searchWord
   *            - The word or string that you are searching for
   */
  public void search(String url, String searchWord)
  {
      while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
      {
          String currentUrl;
          SpiderLeg leg = new SpiderLeg();
          if(this.pagesToVisit.isEmpty())
          {
              currentUrl = url;
              this.pagesVisited.add(url);
          }
          else
          {
              currentUrl = this.nextUrl();
          }
          leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
                                 // SpiderLeg
          boolean success = leg.searchForWord(searchWord);
          if(success)
          {
              System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
              break;
          }
          this.pagesToVisit.addAll(leg.getLinks());
      }
      System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
  }
  
  /**
   * Writes a csv with two columns: the urls, and the number of times the searchWord
   * has occurred in the webpage.  
   * 
   * @param searchWord
   *            - The word or string to look for
   * @param csvFileName
   *            - The csv file to write output to
   */
  public void writeSearchResults(String url, String searchWord, String csvFileName) throws IOException
  {
	  List<List<String>> searchResults = new ArrayList<>();
	  
	  while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
      {
          String currentUrl;
          SpiderLeg leg = new SpiderLeg();
          if(this.pagesToVisit.isEmpty())
          {
              currentUrl = url;
              this.pagesVisited.add(url);
          }
          else
          {
              currentUrl = this.nextUrl();
          }
          leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
                                 // SpiderLeg
          int count = 0;
          
          count = leg.findFreqWord(searchWord);
          
          String countstr = String.valueOf(count);
          
          searchResults.add(new List<String> 
          
          this.pagesToVisit.addAll(leg.getLinks());
      }
      System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
      
      this.convertToCsv(searchResults, csvFileName);
     
  }

  /**
   * Writes a csv with columns: the urls, and their content.
   * Used to export the scraped webpages. 
   * 
   */
  public boolean writeScrapedContent()
  {
      
  }

  /**
   * Returns the next URL to visit (in the order that they were found). We also do a check to make
   * sure this method doesn't return a URL that has already been visited.
   * 
   * @return
   */
  private String nextUrl()
	  {
	      String nextUrl;
	      do
	      {
	          nextUrl = this.pagesToVisit.remove(0);
	      } while(this.pagesVisited.contains(nextUrl));
	      this.pagesVisited.add(nextUrl);
	      return nextUrl;
	  }
  
  private void convertToCsv(List<String[]> dataLines, String csvFileName) throws IOException 
  	{
		  try
		  {
			  FileWriter csvWriter = new FileWriter(csvFileName);

			  for (List<String> data : dataLines) {
			      csvWriter.append(String.join(",", data));
			      csvWriter.append("\n");
			  }

			  csvWriter.flush();
			  csvWriter.close();
		  }
		  catch (IOException ex)
		  {
			  System.out.println("CSV Write Operation Failed");
			  
		  }
  
  }
}