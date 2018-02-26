package google.sheet.main.java;


import java.util.List;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.*;
import google.sheet.main.java.GoogleSheetRange;
import google.sheet.main.java.User32;
import google.sheet.main.java.GoogleSheetRange.*;
import com.sun.jna.platform.win32.WinUser;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;  
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
//import java.awt.event.KeyEvent;

public class GoogleSheetToOracle {
	public static final HWND hWnd = User32.INSTANCE.FindWindow(null,"Oracle Applications - Avery Dennison Production System");
	
	public static final Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
	
	public static void oracleActivate() {
		final int WINDOW_MAXIMIZE = 3;
		User32.INSTANCE.ShowWindow(hWnd,WINDOW_MAXIMIZE);
    	User32.INSTANCE.SetForegroundWindow(hWnd);
    	User32.INSTANCE.SetFocus(hWnd);
	}
	
	public static String getPixelColor(int x,int y) throws AWTException{
		Robot robot = new Robot();
		String red,green,blue;
		
		oracleActivate();
		red = Integer.toHexString(robot.getPixelColor(x, y).getRed());
		green = Integer.toHexString(robot.getPixelColor(x, y).getGreen());
		blue = Integer.toHexString(robot.getPixelColor(x, y).getBlue());
		try {
			return "0x"+(red+green+blue).toUpperCase();
		}catch(NumberFormatException e) {
			System.out.print("cannot return the value of the color");
			e.printStackTrace();
		}
		
		return "0x"+(red+green+blue).toUpperCase();
    }
	
	public static void sendKeyToOracle(String value) throws InterruptedException{
    	//Robot robot = new Robot();
    	//System.out.println(value.length());
		WinUser.INPUT input = new WinUser.INPUT();
		for(int i = 0;i < value.length();i++) {
        	//WinUser.INPUT input = new WinUser.INPUT();
    		input.input.setType("ki");
    		input.type = new WinDef.DWORD( WinUser.INPUT.INPUT_KEYBOARD );
    		input.input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_UNICODE);
    		input.input.ki.wScan = new WinDef.WORD(value.charAt(i));
    		User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size()); 
    		
    		input.input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_UNICODE | WinUser.KEYBDINPUT.KEYEVENTF_KEYUP); 
            User32.INSTANCE.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size()); 
		}
	}
	
	public static void sendKeyToOracle(int value) throws AWTException{
    	Robot robot = new Robot(); 
		robot.keyPress(value);
    	robot.keyRelease(value);
	}
	public static void sendKeyToOracle(int value_1,int value_2) throws AWTException{
		Robot robot = new Robot(); 
		robot.keyPress(value_1);
		robot.keyPress(value_2);
    	robot.keyRelease(value_2);
    	robot.keyRelease(value_1);
	}
	public static void mouseClick(int x,int y) throws AWTException{
		Robot robot = new Robot();
		robot.mouseMove(x, y);
		robot.mousePress(MouseEvent.BUTTON1);
		robot.mouseRelease(MouseEvent.BUTTON1);
	}
	
	public void toOracle() throws Exception{
	    /*
		String pixelColor;
	    String spreadsheetId = "1L1ce7g3jCJTfXgaOOAM2xyhmscQx5yFJPeGrM0pbB8E";
    	String sheet = "Oracle_Order";
    	String a = "A16";
    	
    	oracleActivate();
    	pixelColor = getPixelColor(960,20);
    	Thread.sleep(5000);
    	oracleActivate();
    	sendKeyToOracle("avery dennison is bad.2333");
    	
    	GoogleSheetRange range = new GoogleSheetRange();
    	Search search = new GoogleSheetRange().new Search();
    	//BatchClear batchClear = new GoogleSheetRange().new BatchClear(); 
    	Formate formate = new GoogleSheetRange().new Formate(); 
    	range.set(spreadsheetId, sheet, a);
    	List<GoogleSheetRange> foundResult = search.find(range, "1",false);
    	for(int i = 0;i < foundResult.size();i++) {
    		System.out.printf("%s%s%s\n",foundResult.get(i).getSpreadsheetId(),foundResult.get(i).getSheet(),foundResult.get(i).getRange());
    	}
    	
    	formate.fillColor(range,0xff0000);
    	//batchClear.clear(range);
    	*/
	}

}