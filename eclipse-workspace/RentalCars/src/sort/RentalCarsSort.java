package sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RentalCarsSort {
	
	public RentalCarsSort()
	{	
	}
	
	public enum SortDirection{
		ASC,
		DESC
	}
	
	public JSONArray GetJSONArrayFromWebsite(String websiteName)
	{
		JSONArray arr = new JSONArray();
		
        try {         
            URL url = new URL(websiteName); 
            URLConnection conn = url.openConnection();
            BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String inputLine;
            StringBuilder jsonObjectString = new StringBuilder();
            
            while ((inputLine = input.readLine()) != null) 
            {
            		jsonObjectString.append(inputLine);
            }
            
            input.close();
            
            JSONObject obj = new JSONObject(jsonObjectString.toString());
            arr = obj.getJSONObject("Search").getJSONArray("VehicleList");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
        		e.printStackTrace();
        } catch (Exception e) {
        		e.printStackTrace();
        } 
		return arr;
	}
	
	public String SippString(String sippString) 
	{
		String sipp = sippString.toUpperCase();
		
		Map<Character, String> firstLetter = new HashMap<Character, String>();
		Map<Character, String> secondLetter = new HashMap<Character, String>();
		Map<Character, String> thirdLetter = new HashMap<Character, String>();
		Map<Character, String> fourthLetter = new HashMap<Character, String>();
		
		firstLetter.put('M', "Mini");
		firstLetter.put('E', "Economy");
		firstLetter.put('C', "Compact");
		firstLetter.put('I', "Intermediate");
		firstLetter.put('S', "Standard");
		firstLetter.put('F', "Full size");
		firstLetter.put('P', "Premium");
		firstLetter.put('L', "Luxury");
		firstLetter.put('X', "Special");
		
		secondLetter.put('B', "2 doors");
		secondLetter.put('C', "4 doors");
		secondLetter.put('D', "5 doors");
		secondLetter.put('W', "Estate");
		secondLetter.put('T', "Convertible");
		secondLetter.put('F', "SUV");
		secondLetter.put('P', "Pick up");
		secondLetter.put('V', "Passenger Van");
		
		thirdLetter.put('M', "Manual");
		thirdLetter.put('A', "Automatic");
		
		fourthLetter.put('N', "Petrol/no AC");
		fourthLetter.put('R', "Petrol/AC");
		
		
		return firstLetter.get(sipp.charAt(0) ) + " - " + secondLetter.get(sipp.charAt(1)) + " - " + thirdLetter.get(sipp.charAt(2)) + " - " + fourthLetter.get(sipp.charAt(3));
	}

	public JSONArray SortArrayByDouble(JSONArray array, SortDirection direction, String fieldToSort) 
	{
		JSONArray sortedArray = new JSONArray();
		ArrayList<JSONObject> jsonValues = new ArrayList<JSONObject>();
		
		for(int i = 0; i < array.length(); i++) 
		{
			jsonValues.add(array.getJSONObject(i));
		}
		
		Collections.sort(jsonValues, new Comparator<JSONObject>() {
			
			private final String KEY_NAME = fieldToSort;

	        @Override
	        public int compare(JSONObject a, JSONObject b) {
	        	
	        	Double valA = 0.00;
	        	Double valB = 0.00;
	        	
            try {
                valA = (Double) a.get(KEY_NAME);
                valB = (Double) b.get(KEY_NAME);
            } 
            catch (JSONException e) {
                //do something
            }

            if(direction.equals(SortDirection.ASC))
            {
            		return valA.compareTo(valB);
            }else 
            {
            		return -valA.compareTo(valB);
            } 
	        }
		});

		for (int i = 0; i < array.length(); i++) 
		{
	        sortedArray.put(jsonValues.get(i));
	    }
		
		return sortedArray;
	}
	
	public JSONArray SortArrayByString(JSONArray array, SortDirection direction, String fieldToSort) 
	{
		JSONArray sortedArray = new JSONArray();
		ArrayList<JSONObject> jsonValues = new ArrayList<JSONObject>();
		
		for(int i = 0; i < array.length(); i++) 
		{
			jsonValues.add(array.getJSONObject(i));
		}
		
		Collections.sort(jsonValues, new Comparator<JSONObject>() {
			
			private final String KEY_NAME = fieldToSort;

	        @Override
	        public int compare(JSONObject a, JSONObject b) {
	        	
	        	String valA = "";
	        	String valB = "";
	        	
            try {
                valA = (String) a.get(KEY_NAME);
                valB = (String) b.get(KEY_NAME);
            } 
            catch (JSONException e) {
                e.printStackTrace();
            }

            if(direction.equals(SortDirection.ASC))
            {
            		return valA.compareTo(valB);
            }else 
            {
            		return -valA.compareTo(valB);
            } 
	        }
		});
		
		for (int i = 0; i < array.length(); i++) 
		{
	        sortedArray.put(jsonValues.get(i));
	    }
		
		return sortedArray;
	}
	
	public JSONArray SortPriceASC(JSONArray arr) 
	{
		JSONArray sortedArray = SortArrayByDouble(arr, SortDirection.ASC, "price");
		
		for	(Object o : sortedArray)
        {
        		JSONObject Vehicle = (JSONObject)o;
        		String make = Vehicle.getString("name");
        		Double price = Vehicle.getDouble("price");
        		System.out.println(make + " - " + price);
        }
		
		return sortedArray;
	}

	public JSONArray SortBySippDESC(JSONArray arr) 
	{
		JSONArray sortedArray = SortArrayByString(arr, SortDirection.DESC, "sipp");
		
		for (Object o : sortedArray)
		{
			JSONObject vehicle = (JSONObject)o;
			String make = vehicle.getString("name");
			String sipp = vehicle.getString("sipp");
			
			System.out.println(make + " - " + sipp + " - " + SippString(sipp));
		}
		
		return sortedArray;
	}

	public JSONArray SortByHighestRatedCarTypeDESC(JSONArray arr)
	{
		Map<Character, JSONObject> highestRatedVehicle = new HashMap<Character, JSONObject>();
		Map<Character, String> carType = new HashMap<Character, String>();
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		
		JSONArray unsortedArray = new JSONArray();
		JSONArray sortedArray = new JSONArray();
		
		carType.put('B', "2 doors");
		carType.put('C', "4 doors");
		carType.put('D', "5 doors");
		carType.put('W', "Estate");
		carType.put('T', "Convertible");
		carType.put('F', "SUV");
		carType.put('P', "Pick up");
		carType.put('V', "Passenger Van");
		
		for(Object o : arr)
		{
			JSONObject currentVehicle = (JSONObject)o;
			Character currentCarType = currentVehicle.getString("sipp").charAt(1);
			
			if(highestRatedVehicle.containsKey(currentCarType))
			{
				JSONObject storedVehicle = highestRatedVehicle.get(currentCarType);
				Double storedRating = storedVehicle.getDouble("rating");
				Double currentRating = currentVehicle.getDouble("rating");
				
				if(currentRating > storedRating) 
				{
					highestRatedVehicle.replace(currentCarType, storedVehicle, currentVehicle);
				}
				
			}else {
				highestRatedVehicle.put(currentCarType, currentVehicle);
			}
		}
		
		
		list.addAll(highestRatedVehicle.values());
		
		unsortedArray = new JSONArray(list);
		sortedArray = SortArrayByDouble(unsortedArray, SortDirection.DESC, "rating");
		
		
		for	(Object o : sortedArray)
        {
        		JSONObject vehicle = (JSONObject)o;
        		String make = vehicle.getString("name");
        		String supplier = vehicle.getString("supplier");
        		Double rating = vehicle.getDouble("rating");
        		System.out.println(make + " - " + carType.get(vehicle.getString("sipp").charAt(1)) + " - "+ supplier + " - " + rating);
        }
		return sortedArray;
	}

	public JSONArray SortBySumOfScoresDESC(JSONArray arr) 
	{	
		JSONArray unsortedArray = new JSONArray();
		JSONArray sortedArray = new JSONArray();
		
		for(Object o : arr) 
		{
			JSONObject vehicle = (JSONObject)o;
			vehicle.put("vehicleScore", VehicleScore(vehicle.getString("sipp")));
			unsortedArray.put(vehicle);
		}
		
		sortedArray = SortArrayByDouble(unsortedArray, SortDirection.DESC, "vehicleScore");
		
		for(Object o : sortedArray) 
		{		
			JSONObject vehicle = (JSONObject)o;
			String name = vehicle.getString("name");
			Double vehicleScore = vehicle.getDouble("vehicleScore");
			Double supplierRating = vehicle.getDouble("rating");
			Double sumOfScore = vehicleScore + supplierRating;
			
			System.out.println(name + " - " + vehicleScore + " - " + supplierRating + " - " + sumOfScore);
		}
		
		return sortedArray;

	}
	
	public Double VehicleScore(String sipp)
	{
		Double score = 0.00;
		Integer charIndex = 2;
		Integer sippLength = sipp.length();
		
		Map<Character, Integer> breakDown = new HashMap<Character, Integer>();
		breakDown.put('M', 1);
		breakDown.put('A', 5);
		breakDown.put('R', 2);		
		
		for(Integer i = charIndex; i < sippLength; i++)
		{	
			switch(sipp.charAt(i)) {
			
			case 'M':
				score += breakDown.get(sipp.charAt(i));
				break;
			case 'A':
				score += breakDown.get(sipp.charAt(i));
				break;
			case 'R':
				score += breakDown.get(sipp.charAt(i));
				break;
			default:
				break;
			
			}
		}
		
		return score;
	}

}
