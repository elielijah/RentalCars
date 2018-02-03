package test;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import sort.RentalCarsSort;

public class RentalCarsUnitTests {

	@Test
	public void SuccessfullyGetJSONArrayFromWebsite() 
	{
		
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.rentalcars.com/js/vehicles.json");
		
		Assert.assertFalse(arr.toList().isEmpty());
	}

	@Test
	public void UnsuccessfullyGetJSONArrayFromWebsite() 
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.test.com");
		
		Assert.assertTrue(arr.toList().isEmpty());
	}
	
	@Test
	public void SuccessfullyCalculateVehicleScore() 
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		Assert.assertTrue(rentalCarsSort.VehicleScore("XXAA") == 10);
	}
	
	@Test
	public void UnsuccessfullyCalculateVehicleScore() 
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		Assert.assertFalse(rentalCarsSort.VehicleScore("XXAA") == 12);
	}
	
	@Test
	public void SuccessfullyCalculateSIPP() 
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		Assert.assertTrue(rentalCarsSort.SippString("ECMN").equals("Economy - 4 doors - Manual - Petrol/no AC"));
	}
	
	@Test
	public void UnsuccessfullyCalculateSIPP() 
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		Assert.assertFalse(rentalCarsSort.SippString("ECMN").equals("Full size passenger van - automatic - petrol - air conditioning"));
	}

	@Test
	public void SuccessfullySortByPrice()
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.rentalcars.com/js/vehicles.json");
		
		JSONArray sortedArray = rentalCarsSort.SortPriceASC(arr);
		
		JSONObject a = (JSONObject)sortedArray.get(0);
		JSONObject b = (JSONObject)sortedArray.get(1);
		JSONObject c = (JSONObject)sortedArray.get(2);
		
		
		Assert.assertTrue(a.getDouble("price") < b.getDouble("price"));
		Assert.assertTrue(b.getDouble("price") < c.getDouble("price"));
	}
	
	@Test
	public void UnsuccessfullySortByPrice()
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.rentalcars.com/js/vehicles.json");
		
		JSONArray sortedArray = rentalCarsSort.SortPriceASC(arr);
		
		JSONObject a = (JSONObject)sortedArray.get(0);
		JSONObject b = (JSONObject)sortedArray.get(1);
		JSONObject c = (JSONObject)sortedArray.get(2);
		
		Assert.assertFalse(a.getDouble("price") > b.getDouble("price"));
		Assert.assertFalse(b.getDouble("price") > c.getDouble("price"));
	}
	
		
	@Test
	public void SuccessfullySortBySIPP()
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.rentalcars.com/js/vehicles.json");
		
		JSONArray sortedArray = rentalCarsSort.SortBySippDESC(arr);
		
		JSONObject a = (JSONObject)sortedArray.get(0);
		JSONObject b = (JSONObject)sortedArray.get(1);
		JSONObject c = (JSONObject)sortedArray.get(2);
		
		String val1 = a.getString("sipp");
		String val2 = b.getString("sipp");
		String val3 = c.getString("sipp");
		
		Assert.assertTrue(val1.compareTo(val2) == 0 || val1.compareTo(val2) == 1);
		Assert.assertTrue(val2.compareTo(val3) == 0 || val2.compareTo(val3) == 1);
	}
	
	@Test
	public void UnsuccessfullySortBySIPP()
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.rentalcars.com/js/vehicles.json");
		
		JSONArray sortedArray = rentalCarsSort.SortBySippDESC(arr);
		
		JSONObject a = (JSONObject)sortedArray.get(0);
		JSONObject b = (JSONObject)sortedArray.get(1);
		JSONObject c = (JSONObject)sortedArray.get(2);
		
		String val1 = a.getString("sipp");
		String val2 = b.getString("sipp");
		String val3 = c.getString("sipp");
		
		Assert.assertFalse(val1.compareTo(val2) == -1);
		Assert.assertFalse(val2.compareTo(val3) == -1);
	}
	

	@Test
	public void SuccessfullySortByHighestRatedCarTypeDESC()
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.rentalcars.com/js/vehicles.json");
		
		JSONArray sortedArray = rentalCarsSort.SortByHighestRatedCarTypeDESC(arr);
		
		JSONObject a = (JSONObject)sortedArray.get(0);
		JSONObject b = (JSONObject)sortedArray.get(1);
		JSONObject c = (JSONObject)sortedArray.get(2);
		JSONObject d = (JSONObject)sortedArray.get(3);
		JSONObject e = (JSONObject)sortedArray.get(4);
		JSONObject f = (JSONObject)sortedArray.get(5);
		
		Assert.assertTrue(a.getDouble("rating") >= b.getDouble("rating"));
		Assert.assertTrue(b.getDouble("rating") >= c.getDouble("rating"));
		Assert.assertTrue(c.getDouble("rating") >= d.getDouble("rating"));
		Assert.assertTrue(d.getDouble("rating") >= e.getDouble("rating"));
		Assert.assertTrue(e.getDouble("rating") >= f.getDouble("rating"));
	}


	@Test
	public void UnsuccessfullySortByHighestRatedCarTypeDESC()
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.rentalcars.com/js/vehicles.json");
		
		JSONArray sortedArray = rentalCarsSort.SortByHighestRatedCarTypeDESC(arr);
		
		JSONObject a = (JSONObject)sortedArray.get(0);
		JSONObject b = (JSONObject)sortedArray.get(1);
		JSONObject c = (JSONObject)sortedArray.get(2);
		JSONObject d = (JSONObject)sortedArray.get(3);
		JSONObject e = (JSONObject)sortedArray.get(4);
		JSONObject f = (JSONObject)sortedArray.get(5);
		
		Assert.assertFalse(a.getDouble("rating") < b.getDouble("rating"));
		Assert.assertFalse(b.getDouble("rating") < c.getDouble("rating"));
		Assert.assertFalse(c.getDouble("rating") < d.getDouble("rating"));
		Assert.assertFalse(d.getDouble("rating") < e.getDouble("rating"));
		Assert.assertFalse(e.getDouble("rating") < f.getDouble("rating"));
	}
	
	@Test
	public void SuccessfullySortBySumOfScoresDESC() 
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.rentalcars.com/js/vehicles.json");
		
		JSONArray sortedArray = rentalCarsSort.SortBySumOfScoresDESC(arr);
		
		
		JSONObject a = (JSONObject)sortedArray.get(0);
		JSONObject b = (JSONObject)sortedArray.get(1);
		JSONObject c = (JSONObject)sortedArray.get(2);	
		
		Assert.assertTrue(a.getDouble("vehicleScore") >= b.getDouble("vehicleScore"));
		Assert.assertTrue(b.getDouble("vehicleScore") >= c.getDouble("vehicleScore"));
	}
	
	@Test
	public void UnsuccessfullySortBySumOfScoresDESC() 
	{
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.rentalcars.com/js/vehicles.json");
		
		JSONArray sortedArray = rentalCarsSort.SortBySumOfScoresDESC(arr);
		
		
		JSONObject a = (JSONObject)sortedArray.get(0);
		JSONObject b = (JSONObject)sortedArray.get(1);
		JSONObject c = (JSONObject)sortedArray.get(2);	
		
		Assert.assertFalse(a.getDouble("vehicleScore") < b.getDouble("vehicleScore"));
		Assert.assertFalse(b.getDouble("vehicleScore") < c.getDouble("vehicleScore"));
	}
}
