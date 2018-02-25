package app.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import google.sheet.main.java.GoogleSheetRange;
import google.sheet.main.java.GoogleSheetRange.*;

public class SheetToOracleItem {
	
	private String spreadSheetID = "1L1ce7g3jCJTfXgaOOAM2xyhmscQx5yFJPeGrM0pbB8E";
	private String sheet = "Oracle_Order";
	
	public SheetToOracleItem() {
		
	}
	public Collection<String> getItemList(String rbo){
		
		Collection<String> itemList = new ArrayList<String>();
		try {
			File itemListFile = new File("H:\\CS\\OM\\Share\\Project\\ExceltoSystem\\Oracle\\Setting\\Order\\"+ rbo + "ItemList.txt");
			InputStreamReader reader = new InputStreamReader(new FileInputStream(itemListFile));
			BufferedReader bufferedReader = new BufferedReader(reader);
			ArrayList<String> list = new ArrayList<String>();
			
			String lineContent = "";
			lineContent = bufferedReader.readLine();
			while (lineContent != null && lineContent != "") {
				list.add(lineContent);
				lineContent = bufferedReader.readLine();
			}
			bufferedReader.close();
			itemList.addAll(list);
			return itemList;
		}catch(Exception e) {
			e.printStackTrace();
			return itemList;
		}
	}
	public void getItemContent(String item) {

		List<List<Object>> content = new LinkedList<List<Object>>();
		try {
			File itemFile = new File("H:\\CS\\OM\\Share\\Project\\ExceltoSystem\\Oracle\\Setting\\Item\\"+ item + ".txt");
			InputStreamReader reader = new InputStreamReader(new FileInputStream(itemFile));
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String lineContent = null;
			lineContent = bufferedReader.readLine();
			while (lineContent.length() > 0) {
				content.add(new ArrayList<Object>(Arrays.asList(lineContent.split("\\|"))));
				lineContent = bufferedReader.readLine();
			}
			bufferedReader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if (content != null) {
				Values batchValues = new GoogleSheetRange().new Values();
				try {
					batchValues.write(new GoogleSheetRange(this.spreadSheetID,this.sheet,"E4"), content.get(0).get(0).toString());
					batchValues.write(new GoogleSheetRange(this.spreadSheetID,this.sheet,"E5"), content.get(0).get(1).toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				BatchClear batchClear = new GoogleSheetRange().new BatchClear();
				try {
					batchClear.clear(new GoogleSheetRange(this.spreadSheetID,this.sheet,"B12:Z15"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<List<Object>> queue = new LinkedList<List<Object>>();
				queue.add(content.get(2));
				queue.add(content.get(3));
				queue.add(content.get(4));
				queue.add(content.get(5));
				try {
					batchValues.batchWrite(new GoogleSheetRange(this.spreadSheetID,this.sheet,"A12:Z15"), queue);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
