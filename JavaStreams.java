import java.util.stream.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class JavaStreams {
   public static void main(String[] args) {
     System.out.println("Processing file:" + args[0]);
     BufferedReader bufferedReader = getBufferedReader(args[0]);
     List<Double> prices = getPrices(bufferedReader);
     Double mean = getMean(prices);
     System.out.println("Mean is: " + mean);
     bufferedReader = getBufferedReader(args[0]);
     prices = getPricesByName(bufferedReader, args[1]);   
     System.out.println("Prices for " + args[1] + ":" + prices);
   }

   private static BufferedReader getBufferedReader(String fileName) {
	try {
		File inputF = new File(fileName);
		InputStream inputFS = new FileInputStream(inputF);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
		return br;
	} catch (FileNotFoundException e) {
		System.out.println("File not found.");
		return null;
	}
   }

   private static Double getPrice(String line) {
	   List<String> arr = Arrays.asList(line.split(","));

	   if(arr.size() >= 4) {
 		try {
		   Double price = Double.parseDouble(arr.get(4));
		   return price;
		} catch(NumberFormatException e) {
			System.out.println("Invalid price" + arr.get(4));
                        return null;
		}
	   } else {
		   System.out.println("price not found");
		   return null;
	   }
   }

   private static List<Double> getPrices(BufferedReader bufferedReader) {
   	List<Double> prices = bufferedReader.lines().skip(1).map(i -> getPrice(i)).collect(Collectors.toList());
	return prices;
   }

   private static List<Double> getPricesByName(BufferedReader bufferedReader, String name) {
	List<Double> prices = bufferedReader.lines().skip(1).filter(l -> l.contains(name)).map(l -> getPrice(l)).collect(Collectors.toList());
        return prices;
	/*List<String> arr = Arrays.asList(line.split(","));
        Optional<Present> optional = Arrays.stream(line.split(","))
                                   .filter(x -> x.equals(.getName()))
                                   .findFirst();

	if(optional.isPresent()) {//Check whether optional has element you are looking for
	    Present p = optional.get();//get it from optional
	}
	return 0.0;*/
   } 

   private static Double getMean(List<Double> prices) {
	List<Double> prop = prices.stream().map(i -> i / prices.size()).collect(Collectors.toList());
        Double mean = prices.stream().reduce(0.0, (p1, p2) -> p1+p2)/prices.size();
	// System.out.println(prop);
	// System.out.println(mean);
	return mean;
   }

}
