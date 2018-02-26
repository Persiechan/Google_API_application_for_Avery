package google.sheet.main.java;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
//import com.google.api.services.sheets.v4.Sheets.Spreadsheets.BatchUpdate;
//import java.util.Collections;

public class GoogleSheetRange {
	public GoogleSheetRange googleSheetRange;
	private String spreadsheetId;
	private String sheet;
	private String range;
	private final static String VALUE_INPUT_OPTION = "USER_ENTERED"; 
	public GoogleSheetRange(){
		
	}
	
	public GoogleSheetRange(String spreadsheetId,String sheet,String range) {
		this.spreadsheetId = spreadsheetId;
		this.sheet = sheet;
		this.range = range;
	}
	
	public GoogleSheetRange set(String spreadsheetId,String sheet,String range) {
		this.spreadsheetId = spreadsheetId;
		this.sheet = sheet;
		this.range = range;
		return googleSheetRange;
	}
	
	public GoogleSheetRange setSpreadsheetId(String spreadsheetId) {
		this.spreadsheetId = spreadsheetId;
		return googleSheetRange;
	}
	
	public GoogleSheetRange setSheet(String sheet) {
		this.sheet = sheet;
		return googleSheetRange;
	}
	
	public GoogleSheetRange setRange(String range) {
		this.range = range;
		return googleSheetRange;
	}
	
	public String getSpreadsheetId() throws IOException{
		return spreadsheetId;
	}
	
	public String getSheet() throws IOException{
		return sheet;
	}
	
	public String getRange() throws IOException{
		return range;
	}
	
	public GoogleSheetRange get() throws IOException{
		return googleSheetRange;
	}
	
  public class Values{
	  public Values(){
		  
	  }
	  public String read(GoogleSheetRange range)throws Exception {
		List<List<Object>> queue = new LinkedList<List<Object>>();
		List<Object> member = new ArrayList<Object>();
		if (range == null){
			throw new Exception();
		}else {
			Sheets.Spreadsheets.Values.Get get = GoogleSheetService.service.spreadsheets().values().get(range.getSpreadsheetId(), range.getSheet()+"!"+range.getRange());
			get.setValueRenderOption("FORMATTED_VALUE");
		    get.setDateTimeRenderOption("SERIAL_NUMBER");
		    queue = get.execute().getValues();
			member = queue.get(0);
			return member.get(0).toString();
		}
	  }
	  
	  public void write(GoogleSheetRange range,String value) throws Exception {
		List<List<Object>> queue = new LinkedList<List<Object>>();
		List<Object> member = new ArrayList<Object>();
		ValueRange content = new ValueRange();
		member.add(value);
		queue.add(member);
		content.setValues(queue);
		content.setRange(range.getSheet()+"!"+range.getRange());
		
		GoogleSheetService.service.spreadsheets().values()
				.update(range.getSpreadsheetId(), range.getSheet()+"!"+range.getRange(), content).setValueInputOption(VALUE_INPUT_OPTION).execute();
	  }
	  
	  public void batchWrite(GoogleSheetRange range,List<List<Object>> value)throws Exception {
		  ValueRange content = new ValueRange();
		  content.setValues(value);
		  content.setRange(range.getSheet()+"!"+range.getRange());
		  List<ValueRange> data = new ArrayList<ValueRange>();
		  data.add(content);
		  
		  BatchUpdateValuesRequest request = new BatchUpdateValuesRequest();
		  request.setValueInputOption("USER_ENTERED");
		  /*
		  request.setIncludeValuesInResponse(false);
		  request.setResponseDateTimeRenderOption("SERIAL_NUMBER");
		  request.setResponseValueRenderOption("UNFORMATTED_VALUE");
		  */
		  request.setData(data);
		  
		  GoogleSheetService.service.spreadsheets().values()
		  		.batchUpdate(range.getSpreadsheetId(), request).execute();
	  }
  }
  /*
  public class FindReplace{
	  FindReplace(){
		  
	  }
	  public void findReplace(GoogleSheetRange searchRange,String replaceValue) throws Exception{
		GridRange gridRange = new GridRange().setStartRowIndex(1).setEndRowIndex(2).setStartColumnIndex(1).setEndColumnIndex(2);
	    FindReplaceRequest findReplace = new FindReplaceRequest().setFind(replaceValue).setSheetId(0).setMatchCase(true).setMatchEntireCell(true).setRange(gridRange);
		Request request = new Request().setFindReplace(findReplace);
		List<Request> requests = new ArrayList<Request>(); 
	    	requests.add(request);
	    BatchUpdateSpreadsheetRequest requestBody = new BatchUpdateSpreadsheetRequest();
	    requestBody.setRequests(requests);

	    Sheets.Spreadsheets.BatchUpdate result =
	        	GoogleSheetsService.service.spreadsheets().batchUpdate(range.getSpreadsheetId(), requestBody);
	    BatchUpdateSpreadsheetResponse a = result.execute();
	    List<Response> b = a.getReplies();
	    FindReplaceResponse f = b.get(0).getFindReplace();
	    
	    System.out.println(f.getRowsChanged());
	  }
  }
  */
  public class Search{
	  Search(){
		  
	  }
	  // Transform the number of columns to A-Z Process
	  // And return the result.
	  private String decimal_Transform_LetterCountingMethod(int decimalNumber) {
		  int remainder = 0;
		  String temp = "";
		  while (decimalNumber % 26 > 0) {
			  remainder = decimalNumber % 26;
			  temp = temp + (char)(remainder + 64);
			  decimalNumber = decimalNumber / 26;
		  }
		  return new StringBuffer(temp).reverse().toString();
	  }
	  
	  // Get batch values for matching search value
	  // And return the columns and rows of multiple matching result.
	  public List<GoogleSheetRange> find(GoogleSheetRange searchRange,String searchValue) throws IOException {
		  int originRowIndex = 0;
		  int originColumnIndex = 0;
		  int foundRowIndex = 0;
		  int foundColumnIndex = 0;
		  String foundRange = null;
		  
		  for (int i = 0;i < searchRange.getRange().length();i++) {
			  if ((int)searchRange.getRange().charAt(i) == 58) {
				  break;
			  }
			  if ((int)searchRange.getRange().charAt(i) > 64 && (int)searchRange.getRange().charAt(i) < 91) {
				  originColumnIndex = originColumnIndex + (int)searchRange.getRange().charAt(i) - 64;
				  
			  }else {
				  originRowIndex = originRowIndex * 10 + (int)searchRange.getRange().charAt(i) - 48;
			  }
		  }
		  
		  List<String> ranges = new ArrayList<>();
		  ranges.add(0,searchRange.getSheet() + "!" + searchRange.getRange());
		  Sheets.Spreadsheets.Values.BatchGet request =
				  GoogleSheetService.service.spreadsheets().values()
				  .batchGet(searchRange.getSpreadsheetId()).setMajorDimension("ROWS").setValueRenderOption("FORMATTED_VALUE").setDateTimeRenderOption("SERIAL_NUMBER").setRanges(ranges);
		  BatchGetValuesResponse response = request.execute();
		  
		  List<List<Object>> batchValues = response.getValueRanges().get(0).getValues();
		  List<GoogleSheetRange> foundResults = new ArrayList<>(5);
		  for (int i = 0;i < batchValues.size();i++) {
			  for(int j = 0;j < batchValues.get(i).size();j++){
				  if (batchValues.get(i).get(j).toString().equals(searchValue)) {
					  foundRowIndex = originRowIndex + i;
					  foundColumnIndex = originColumnIndex + j;
					  foundRange = decimal_Transform_LetterCountingMethod(foundColumnIndex) + foundRowIndex;
					  foundResults.add(new GoogleSheetRange(searchRange.getSpreadsheetId(), searchRange.getSheet(), foundRange));
				  }
			  }
		  }
		  request.clear();
		  return (List<GoogleSheetRange>)foundResults;
	  }
	  
	  public List<GoogleSheetRange> find(GoogleSheetRange searchRange,String searchValue,boolean lookAt) throws IOException {
		  int originRowIndex = 0;
		  int originColumnIndex = 0;
		  int foundRowIndex = 0;
		  int foundColumnIndex = 0;
		  String foundRange = null;
		  
		  for (int i = 0;i < searchRange.getRange().length();i++) {
			  if ((int)searchRange.getRange().charAt(i) == 58) {
				  break;
			  }
			  if ((int)searchRange.getRange().charAt(i) > 64 && (int)searchRange.getRange().charAt(i) < 91) {
				  originColumnIndex = originColumnIndex + (int)searchRange.getRange().charAt(i) - 64;
				  
			  }else {
				  originRowIndex = originRowIndex * 10 + (int)searchRange.getRange().charAt(i) - 48;
			  }
		  }
		  
		  List<String> ranges = new ArrayList<>();
		  ranges.add(0,searchRange.getSheet() + "!" + searchRange.getRange());
		  Sheets.Spreadsheets.Values.BatchGet request =
				  GoogleSheetService.service.spreadsheets().values()
				  .batchGet(searchRange.getSpreadsheetId()).setMajorDimension("ROWS").setValueRenderOption("FORMATTED_VALUE").setDateTimeRenderOption("SERIAL_NUMBER").setRanges(ranges);
		  BatchGetValuesResponse response = request.execute();
		  
		  List<List<Object>> batchValues = response.getValueRanges().get(0).getValues();
		  List<GoogleSheetRange> foundResults = new ArrayList<>(5);
		  if (lookAt == true) {
			  for (int i = 0;i < batchValues.size();i++) {
				  for(int j = 0;j < batchValues.get(i).size();j++){
					  if (batchValues.get(i).get(j).toString().equals(searchValue)) {
						  foundRowIndex = originRowIndex + i;
						  foundColumnIndex = originColumnIndex + j;
						  foundRange = decimal_Transform_LetterCountingMethod(foundColumnIndex) + foundRowIndex;
						  foundResults.add(new GoogleSheetRange(searchRange.getSpreadsheetId(), searchRange.getSheet(), foundRange));
					  }
				  }
			  }  
		  }else {
			  for (int i = 0;i < batchValues.size();i++) {
				  for(int j = 0;j < batchValues.get(i).size();j++){
					  if (batchValues.get(i).get(j).toString().contains(searchValue)) {
						  foundRowIndex = originRowIndex + i;
						  foundColumnIndex = originColumnIndex + j;
						  foundRange = decimal_Transform_LetterCountingMethod(foundColumnIndex) + foundRowIndex;
						  foundResults.add(new GoogleSheetRange(searchRange.getSpreadsheetId(), searchRange.getSheet(), foundRange));
					  }
				  }
			  }  
		  }
		  request.clear();
		  return (List<GoogleSheetRange>)foundResults;
	  }
  }
  
  public class BatchClear{
	  public BatchClear(){
		  
	  }
	  public void clear(GoogleSheetRange clearRange) throws IOException {
		  List<String> ranges = new ArrayList<>();
		  ranges.add(0,clearRange.getSheet() + "!" + clearRange.getRange());
		  BatchClearValuesRequest requestBody = new BatchClearValuesRequest();
		  requestBody.setRanges(ranges);
		  Sheets.Spreadsheets.Values.BatchClear request =
				  GoogleSheetService.service.spreadsheets().values().batchClear(clearRange.getSpreadsheetId(), requestBody);

		  request.execute();
		  request.clear();
	  }
  }
  
  public class Formate{
	  private int startRowIndex = 0;
	  private int endRowIndex = 0;
	  private int startColumnIndex = 0;
	  private int endColumnIndex = 0;
	  private GridRange gridRange;
	  
	  private static final String CONDITION_TYPE_UNSPECIFIED = "CONDITION_TYPE_UNSPECIFIED";
	  private static final String NUMBER_GREATER = "NUMBER_GREATER";
	  private static final String NUMBER_GREATER_THAN_EQ = "NUMBER_GREATER_THAN_EQ";
	  private static final String NUMBER_LESS =	"NUMBER_LESS";
	  private static final String NUMBER_LESS_THAN_EQ =	"NUMBER_LESS_THAN_EQ";
	  private static final String NUMBER_EQ	= "NUMBER_EQ";
	  private static final String NUMBER_NOT_EQ	= "NUMBER_NOT_EQ";
	  private static final String NUMBER_BETWEEN = "NUMBER_BETWEEN";
	  private static final String NUMBER_NOT_BETWEEN = "NUMBER_NOT_BETWEEN";
	  private static final String TEXT_CONTAINS	= "TEXT_CONTAINS";
	  private static final String TEXT_NOT_CONTAINS	= "TEXT_NOT_CONTAINS";
	  private static final String TEXT_STARTS_WITH = "TEXT_STARTS_WITH";
	  private static final String TEXT_ENDS_WITH = "TEXT_ENDS_WITH";
	  private static final String TEXT_EQ = "TEXT_EQ";
	  private static final String TEXT_IS_EMAIL = "TEXT_IS_EMAIL";
	  private static final String TEXT_IS_URL = "TEXT_IS_URL";
	  private static final String DATE_EQ = "DATE_EQ";
	  private static final String DATE_BEFORE =	"DATE_BEFORE";
	  private static final String DATE_AFTER = "DATE_AFTER";
	  private static final String DATE_ON_OR_BEFORE	= "DATE_ON_OR_BEFORE";
	  private static final String DATE_ON_OR_AFTER = "DATE_ON_OR_AFTER";
	  private static final String DATE_BETWEEN = "DATE_BETWEEN";
	  private static final String DATE_NOT_BETWEEN = "DATE_NOT_BETWEEN";
	  private static final String DATE_IS_VALID = "DATE_IS_VALID";
	  private static final String ONE_OF_RANGE = "ONE_OF_RANGE";
	  private static final String ONE_OF_LIST = "ONE_OF_LIST";
	  private static final String BLANK	= "BLANK";
	  private static final String NOT_BLANK	= "NOT_BLANK";
	  private static final String CUSTOM_FORMULA = "CUSTOM_FORMULA";
	  


	  private GridRange transformGoogleSheetRangeToGridRange (GoogleSheetRange googleSheetRange) throws IOException{
		  /**    REFERANCE BY GOOGLE
		   * A range on a sheet. All indexes are zero-based. Indexes are half open, 
		   * e.g the start index is inclusive and the end index is exclusive -- [start_index, end_index). Missing indexes indicate the range is unbounded on that side. 
		   * For example, if `"Sheet1"` is sheet ID 0, then: `Sheet1!A1:A1 == sheet_id: 0, start_row_index: 0, end_row_index: 1, start_column_index: 0, end_column_index: 1` 
		   * `Sheet1!A3:B4 == sheet_id: 0, start_row_index: 2, end_row_index: 4, start_column_index: 0, end_column_index: 2` 
		   * `Sheet1!A:B == sheet_id: 0, start_column_index: 0, end_column_index: 2` 
		   * `Sheet1!A5:B == sheet_id: 0, start_row_index: 4, start_column_index: 0, end_column_index: 2` 
		   * `Sheet1 == sheet_id:0` The start index must always be less than or equal to the end index. If the start index equals the end index, then the range is empty. 
		   * Empty ranges are typically not meaningful and are usually rendered in the UI as `#REF!`. 
		   */
		  List<String> ranges = new ArrayList<String>();
		  ranges.add(googleSheetRange.getRange());
		  int separateLocation = 0;
		  int sheetId = 0;
		  
		  Sheets.Spreadsheets.Get request = GoogleSheetService.service.spreadsheets().get(googleSheetRange.getSpreadsheetId());
		  request.setRanges(ranges);
		  request.setIncludeGridData(false);
		  
		  //Get sheetId
		  Spreadsheet response = request.execute();
		  for (int i = 0;i < response.getSheets().size();i++) {
			  if (response.getSheets().get(i).getProperties().getTitle().equals(googleSheetRange.getSheet())) {
				  sheetId = response.getSheets().get(i).getProperties().getIndex();
			  }
		  }
		  
		  //Get startRowIndex,startRowIndex,startRowIndex,startRowIndex
		  for(int i = 0;i < googleSheetRange.getRange().length();i++) {
			  if ((int)googleSheetRange.getRange().charAt(i) == 58) {
				  separateLocation = i;
				  break;
			  }
			  if ((int)googleSheetRange.getRange().charAt(i) > 64 && (int)googleSheetRange.getRange().charAt(i) < 91) {
				  startColumnIndex = startColumnIndex + (int)googleSheetRange.getRange().charAt(i) - 64;
				  
			  }else {
				  startRowIndex = startRowIndex * 10 + (int)googleSheetRange.getRange().charAt(i) - 48;
			  }
		  }		
		  
		  startColumnIndex = startColumnIndex - 1;
		  startRowIndex = startRowIndex - 1;
		  
		  if (separateLocation != 0) {
			  for(int i = separateLocation + 1;i < googleSheetRange.getRange().length();i++) {
				  if ((int)googleSheetRange.getRange().charAt(i) > 64 && (int)googleSheetRange.getRange().charAt(i) < 91) {
					  endColumnIndex = endColumnIndex + (int)googleSheetRange.getRange().charAt(i) - 64;
					  
				  }else {
					  endRowIndex = endRowIndex * 10 + (int)googleSheetRange.getRange().charAt(i) - 48;
				  }
			  }	
		  }else {
			  endColumnIndex = startColumnIndex + 1;
			  endRowIndex = startRowIndex + 1;
		  }
		  
		  return new GridRange()
			        .setSheetId(sheetId)
			        .setStartRowIndex(startRowIndex)
			        .setEndRowIndex(endRowIndex)
			        .setStartColumnIndex(startColumnIndex)
			        .setEndColumnIndex(endColumnIndex);
	  }
	  
	  private float[] transformHexColorToFloat(int color) throws IOException{
		  float red ;
		  float green;
		  float blue;
		  red = (float)((color & 0xff0000)>>16) / (float)0xff;
		  green = (float)((color & 0x00ff00)>>8) / (float)0xff;
		  blue = (float)((color & 0x0000ff)) / (float)0xff;
		  float[] RGB = {red,green,blue};
		  return RGB;
	  }
	  public void fillColor(GoogleSheetRange fillRange,int color) throws IOException{
		  
		  this.gridRange = transformGoogleSheetRangeToGridRange(fillRange);
		  List<GridRange> ranges = new ArrayList<GridRange>();
		  ranges.add(gridRange);
		  
		  List<Request> requests = new ArrayList<Request>();
		  Request request = new Request();
		  request.setRepeatCell(new RepeatCellRequest()
				  .setRange(gridRange)
				  .setCell(new CellData()
						  .setUserEnteredFormat(new CellFormat()	  
								  .setBackgroundColor(new Color()
										  .setRed(transformHexColorToFloat(color)[0]).setGreen(transformHexColorToFloat(color)[1]).setBlue(transformHexColorToFloat(color)[2]))))
				  .setFields("UserEnteredFormat(BackgroundColor)"));
		  requests.add(request);
		  BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
		  GoogleSheetService.service.spreadsheets().batchUpdate(fillRange.getSpreadsheetId(), body).execute();
		  
	  }
  }
}

