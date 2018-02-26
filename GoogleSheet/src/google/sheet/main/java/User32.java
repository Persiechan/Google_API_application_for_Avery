package google.sheet.main.java;


import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;

public interface User32 extends StdCallLibrary,WinUser{
	public static final User32 INSTANCE = (User32)Native.loadLibrary("User32",User32.class, W32APIOptions.DEFAULT_OPTIONS);
	
	public abstract HWND FindWindow(String paramString1, String paramString2);
    public abstract void ShowWindow(HWND hWnd, int nCmdShow);
    public abstract void SetForegroundWindow(HWND hWnd);
    public abstract void SetFocus(HWND hWnd);
    public abstract void SetCursorPos(long x,long y);
    public abstract boolean GetWindowRect(HWND hWnd,RECT rect); 
    public abstract boolean GetCursorPos(POINT point);
    public abstract DWORD SendInput(DWORD dWord,INPUT[] input,int i);
}


