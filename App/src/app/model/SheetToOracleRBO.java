package app.model;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
//import java.io.BufferedWriter;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.util.Arrays;

public class SheetToOracleRBO {
	
	public SheetToOracleRBO() {
		
	}
	public Collection<String> getRBOList() {
		Collection<String> rbo = new ArrayList<String>();
		try {
			File rboFile = new File("H:\\CS\\OM\\Share\\Project\\ExceltoSystem\\Oracle\\Setting\\Order\\RBOList.txt");
			InputStreamReader reader = new InputStreamReader(new FileInputStream(rboFile));
			BufferedReader bufferedReader = new BufferedReader(reader);
			ArrayList<String> rboList = new ArrayList<String>();
			
			String lineContent = "";
			lineContent = bufferedReader.readLine();
			while (lineContent != null) {
				rboList.add(lineContent);
				lineContent = bufferedReader.readLine();
			}
			bufferedReader.close();
			rbo.addAll(rboList);
			return rbo;
		}catch(Exception e) {
			e.printStackTrace();
			return rbo;
		}
		
	}
}
