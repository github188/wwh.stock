package cn.hzstk.securities.util;

import cn.hzstk.securities.common.Constant;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.StdCallLibrary;
import static com.sun.jna.platform.win32.WinUser.*;

/**
 * Created by wangweihang on 2016/8/24.
 */
public class TryWithHWND {
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

        boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);
        int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);
        HWND FindWindowA(String className, String windowName);
        HWND FindWindowExA(HWND hWnd, HWND hWndChild, String className, String windowName);
        long DestroyWindow(HWND hWnd);
        void SwitchToThisWindow(HWND hWnd, boolean bRestore);
        boolean SetWindowPos(HWND hWnd,
                             HWND hWndInsertAfter,
                             int X,
                             int Y,
                             int cx,
                             int cy,
                             int uFlags);

        HWND GetActiveWindow();
        boolean SetForegroundWindow(HWND hWnd);
        HWND GetForegroundWindow();

        final WinDef.WPARAM SC_MONITORPOWER = new WinDef.WPARAM(0xF170);

        final WinDef.LPARAM MONITOR_ON = new WinDef.LPARAM(-1); // the display is powering on
        final WinDef.LPARAM MONITOR_STANBY = new WinDef.LPARAM(1); // the display is being shut off
        final WinDef.LPARAM MONITOR_OFF = new WinDef.LPARAM(2); // the display is going to low power

        WinDef.LRESULT SendMessageA(final HWND hWnd, final int msg, final WinDef.WPARAM wParam, final WinDef.LPARAM lParam);
        WinDef.LRESULT SendMessageW(final HWND hWnd, final int msg, final WinDef.WPARAM wParam, final WinDef.LPARAM lParam);
    }

    private static void closeWin(String className) {
        int SWP_NOMOVE = 2;
        int SWP_NOSIZE = 1;
        int SWP_SHOWWINDOW = 0x40;
        int SWP_ASYNCWINDOWPOS = 0x4000;
        HWND HWND_TOPMOST = new HWND(new Pointer(-1));
        TryWithHWND.User32 user32 = TryWithHWND.User32.INSTANCE;

        WinDef.HWND hwnd = user32.FindWindowA(className, null);
        boolean ret = user32.SetForegroundWindow(hwnd);
        user32.SetWindowPos(hwnd, HWND_TOPMOST, 0, 0, 100, 200, SWP_SHOWWINDOW | SWP_ASYNCWINDOWPOS);
        System.out.print(ret);
        //long ret = user32.DestroyWindow(hwnd);
    }

    public static void main(String[] args) {
        final User32 user32 = User32.INSTANCE;
        closeWin(Constant.EXCEL_CLASSNAME);
        /*user32.EnumWindows(new WNDENUMPROC() {
            int count = 0;

            @Override
            public boolean callback(HWND hWnd, Pointer arg1) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText,"GBK");

                // get rid of this if block if you want all windows regardless of whether
                // or not they have text
                if (wText.isEmpty()) {
                    return true;
                }

                System.out.println("Found window with text " + hWnd + ", total " + ++count
                        + " Text: " + wText);
                //user32.SetWindowPos(hWnd, HWND_TOPMOST, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
                return true;
            }
        }, null);
        User32.INSTANCE.SendMessageW(
                HWND_BROADCAST,
                WM_SYSCOMMAND,
                User32.SC_MONITORPOWER,
                User32.MONITOR_OFF);*/
    }
}
