package sort;

import org.json.JSONArray;

public class RentalCarsTechnicalTest {
	
	public static void main (String [] args) {
		
		RentalCarsSort rentalCarsSort = new RentalCarsSort();
		JSONArray arr = rentalCarsSort.GetJSONArrayFromWebsite("http://www.rentalcars.com/js/vehicles.json");
		
		//rentalCarsSort.SortPriceASC(arr);
		//rentalCarsSort.SortBySippDESC(arr);
		//rentalCarsSort.SortByHighestRatedCarTypeDESC(arr);
		rentalCarsSort.SortBySumOfScoresDESC(arr);
    }   


}
