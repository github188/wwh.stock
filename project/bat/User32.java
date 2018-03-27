package cn.hzstk.securities.util;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.WNDCLASS;
import org.xvolks.jnative.misc.basicStructures.*;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.util.Callback;

import javax.swing.*;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;


/**
 * User32 this is the class wrapper to User32.dll.<br>
 * When a developper needs a function of this DLL (s)he should add it here.
 *
 * $Id: User32.java,v 1.25 2007/04/27 18:42:03 thubby Exp $;
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class User32 {
    public static final String DLL_NAME = "user32.dll";

    private static JNative nEnumWindows;

    private static JNative nGetWindowText;

    private static Pointer nBufferGWT;

    private static JNative getProcessHandle;

    public static final int SWP_NOMOVE = 2;
    public static final int SWP_NOSIZE = 1;
    public static final int SWP_HIDEWINDOW = 0x80;
    public static final int SWP_SHOWWINDOW = 0x40;

    /*public static final HWND HWND_TOP = new HWND(0);
    public static final HWND HWND_TOPMOST = new HWND(-1);
    public static final HWND HWND_NOTOPMOST = new HWND(-2);*/

    public static final int IDI_APPLICATION  =  32512;
    public static final int IDI_ASTERISK     =  32516;
    public static final int IDI_ERROR        =  32513;
    public static final int IDI_EXCLAMATION  =  32515;
    public static final int IDI_HAND         =  IDI_ERROR;
    public static final int IDI_INFORMATION  =  IDI_ASTERISK;
    public static final int IDI_QUESTION     =  32514;
    public static final int IDI_WARNING      =  IDI_EXCLAMATION;
    public static final int IDI_WINLOGO      =  32517;  // IDI_APPLICATION on WinXP
    public static final int IDI_SHIELD  = 32518;  // Vista only

    public static final int WM_HOTKEY = 0x312;
    public static final int GWL_WNDPROC = (-4);
    public static final int MOD_ALT = 0x1;
    public static final int MOD_CONTROL = 0x2;
    public static final int MOD_SHIFT = 0x4;
    public static final int MOD_WIN = 0x8;

    /*
     *HWND GetDesktopWindow(VOID);
     */
    public static HWND GetDesktopWindow()
            throws NativeException, IllegalAccessException {
        JNative GetDesktopWindow = new JNative(DLL_NAME, "GetDesktopWindow");
        try {
            GetDesktopWindow.setRetVal(Type.INT);
            GetDesktopWindow.invoke();
            return new HWND(GetDesktopWindow.getRetValAsInt());
        } finally {
            if(GetDesktopWindow != null)
                GetDesktopWindow.dispose();
        }
    }

    /*
     int ReleaseDC(
        HWND hWnd,  // handle to window
        HDC hDC     // handle to DC
    );
     */
    public static int ReleaseDC(HWND hwnd, DC hdc)
            throws NativeException, IllegalAccessException {
        JNative ReleaseDC = new JNative(DLL_NAME, "ReleaseDC");
        try {
            ReleaseDC.setRetVal(Type.INT);
            ReleaseDC.setParameter(0, hwnd.getValue());
            ReleaseDC.setParameter(0, hdc.getValue());
            ReleaseDC.invoke();
            return ReleaseDC.getRetValAsInt();
        } finally {
            if(ReleaseDC != null)
                ReleaseDC.dispose();
        }
    }
    /*
    HDC GetDC(
      HWND hWnd   // handle to window
    );
     **/
    public static DC GetDC(HWND hwnd)
            throws NativeException, IllegalAccessException {
        JNative GetDC = new JNative(DLL_NAME, "GetDC");
        try {
            GetDC.setRetVal(Type.INT);
            GetDC.setParameter(0, hwnd.getValue());
            GetDC.invoke();
            return new DC(GetDC.getRetValAsInt());
        } finally {
            if(GetDC != null)
                GetDC.dispose();
        }
    }

    public static int GetWindowThreadProcessId(final HWND hwnd) {
        if(hwnd == null)
            return -1;
        Pointer nBufferGWT = null;
        try {
            if(getProcessHandle == null) {
                getProcessHandle = new JNative("User32.dll","GetWindowThreadProcessId");
                getProcessHandle.setRetVal(Type.INT);
            }
            nBufferGWT = new Pointer(MemoryBlockFactory.createMemoryBlock(4));
            getProcessHandle.setParameter(0, hwnd.getValue());
            getProcessHandle.setParameter(1, nBufferGWT);
            getProcessHandle.invoke();
            return nBufferGWT.getAsInt(0);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(nBufferGWT != null)
                    nBufferGWT.dispose();
                nBufferGWT = null;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * HWND FindWindow(
     *
     * LPCTSTR lpClassName, LPCTSTR lpWindowName );
     */
    public static HWND FindWindow(String className, String windowName)
            throws NativeException, IllegalAccessException {
        JNative _FindWindow = new JNative(DLL_NAME, "FindWindowA");
        try {
            _FindWindow.setRetVal(Type.INT);
            _FindWindow.setParameter(0, Type.STRING, className);
            _FindWindow.setParameter(1, Type.STRING, windowName);
            _FindWindow.invoke();
            int valRet = Integer.parseInt(_FindWindow.getRetVal());
            return new HWND(valRet);
        } finally {
            if(_FindWindow != null)
                _FindWindow.dispose();
        }
    }
    /*
        WNDPROC lpPrevWndFunc,
        HWND hWnd,
        UINT Msg,
        WPARAM wParam,
        LPARAM lParam
     */

    public static LRESULT CallWindowProc(LONG lpPrevWndFunc, HWND hwnd, UINT msg, WPARAM wparam,
                                         LPARAM lparam)
            throws NativeException, IllegalAccessException {

        JNative dwp = new JNative(DLL_NAME, "CallWindowProcA");
        try {
            dwp.setRetVal(Type.INT);
            int i = 0;
            dwp.setParameter(i++, lpPrevWndFunc.getValue());
            dwp.setParameter(i++, hwnd.getValue());
            dwp.setParameter(i++, msg.getValue());
            dwp.setParameter(i++, wparam.getValue());
            dwp.setParameter(i++, lparam.getValue());
            dwp.invoke();

            return new LRESULT(dwp.getRetValAsInt());
        } finally {
            if(dwp != null)
                dwp.dispose();
        }

    }

    public static LRESULT DefWindowProc(HWND hwnd, UINT msg, WPARAM wparam,
                                        LPARAM lparam) throws NativeException, IllegalAccessException {
        JNative dwp = new JNative(DLL_NAME, "DefWindowProcA");
        dwp.setRetVal(Type.INT);
        dwp.setParameter(0, Type.INT, hwnd.getValueAsString());
        dwp.setParameter(1, Type.INT, msg.getValueAsString());
        dwp.setParameter(2, Type.INT, wparam.getValueAsString());
        dwp.setParameter(3, Type.INT, lparam.getValueAsString());
        dwp.invoke();
        return new LRESULT(dwp.getRetValAsInt());
    }

    public static void UpdateWindow(HWND hwnd) throws NativeException,
            IllegalAccessException {
        JNative dm = new JNative(DLL_NAME, "UpdateWindow");
        dm.setParameter(0, Type.INT, hwnd.getValueAsString());
        dm.invoke();
        dm.dispose();
    }
/*

    public static void DispatchMessage(MSG msg) throws NativeException,
            IllegalAccessException {
        JNative dm = new JNative(DLL_NAME, "DispatchMessageA");
        // dm.setRetVal(Type.INT);
        dm.setParameter(0, msg.getValue());
        dm.invoke();
        dm.dispose();
    }

    public static void TranslateMessage(MSG msg) throws NativeException,
            IllegalAccessException {
        JNative dm = new JNative(DLL_NAME, "TranslateMessage");
        // dm.setRetVal(Type.INT);
        dm.setParameter(0, msg.getValue());
        dm.invoke();
        dm.dispose();
    }

    public static int PeekMessage(MSG msg, HWND hwnd, int minMSG, int maxMSG, int removeMsg)
            throws NativeException, IllegalAccessException {
        JNative gm = new JNative(DLL_NAME, "PeekMessageA");
        gm.setRetVal(Type.INT);
        gm.setParameter(0, msg.getValue());
        gm.setParameter(1, Type.INT, hwnd.getValueAsString());
        gm.setParameter(2, Type.INT, "" + minMSG);
        gm.setParameter(3, Type.INT, "" + maxMSG);
        gm.setParameter(4, Type.INT, "" + removeMsg);
        gm.invoke();
        int ret = gm.getRetValAsInt();
        gm.dispose();
        return ret;
    }

    public static int GetMessage(MSG msg, HWND hwnd, int minMSG, int maxMSG)
            throws NativeException, IllegalAccessException {
        JNative gm = new JNative(DLL_NAME, "GetMessageA");
        gm.setRetVal(Type.INT);
        gm.setParameter(0, msg.getValue());
        gm.setParameter(1, Type.INT, hwnd.getValueAsString());
        gm.setParameter(2, Type.INT, "" + minMSG);
        gm.setParameter(3, Type.INT, "" + maxMSG);
        gm.invoke();
        int ret = gm.getRetValAsInt();
        gm.dispose();
        return ret;
    }
*/

    public static boolean ShowWindow(HWND hwnd, int nCmdShow)
            throws NativeException, IllegalAccessException {
        JNative w = null;
        try {
            w = new JNative(DLL_NAME, "ShowWindow");
            w.setRetVal(Type.INT);
            w.setParameter(0, Type.INT, hwnd.getValueAsString());
            w.setParameter(1, Type.INT, "" + nCmdShow);
            w.invoke();
            return Integer.parseInt(w.getRetVal()) != 0;
        } finally {
            if (w != null)
                w.dispose();
        }
    }

    /**
     * HWND CreateWindowEx( DWORD dwExStyle, LPCTSTR lpClassName, LPCTSTR
     * lpWindowName, DWORD dwStyle, int x, int y, int nWidth, int nHeight, HWND
     * hWndParent, -3 for HWND_MESSAGE (Message only windows) HMENU hMenu,
     * HINSTANCE hInstance, LPVOID lpParam );
     */
    public static final int CreateWindowEx(int dwExStyle, String lpClassName,
                                           String lpWindowName, int dwStyle, int x, int y, int nWidth,
                                           int nHeight, int hWndParent, int hMenu, int hInstance, int lParam)
            throws NativeException, IllegalAccessException {
        JNative n = null;
        try {
            n = new JNative(DLL_NAME, "CreateWindowExA");
            n.setRetVal(Type.INT);
            int i = 0;
            n.setParameter(i++, Type.INT, "" + dwExStyle);
            n.setParameter(i++, Type.STRING, lpClassName);
            if(lpWindowName == null) {
                n.setParameter(i++, 0);
            } else {
                n.setParameter(i++, lpWindowName);
            }
            n.setParameter(i++, Type.INT, "" + dwStyle);
            n.setParameter(i++, Type.INT, "" + x);
            n.setParameter(i++, Type.INT, "" + y);
            n.setParameter(i++, Type.INT, "" + nWidth);
            n.setParameter(i++, Type.INT, "" + nHeight);
            n.setParameter(i++, Type.INT, "" + hWndParent);
            n.setParameter(i++, Type.INT, "" + hMenu);
            n
                    .setParameter(i++, Type.INT, ""
                            + (hInstance == 0 ? JNative.getCurrentModule()
                            : hInstance));
            n.setParameter(i++, Type.INT, "" + lParam);
            n.invoke();
            return Integer.parseInt(n.getRetVal());
        } finally {
            if (n != null) {
                n.dispose();
            }
        }
    }

    public static final int CreateWindowEx(int dwExStyle, LONG lpClassName,
                                           String lpWindowName, int dwStyle, int x, int y, int nWidth,
                                           int nHeight, int hWndParent, int hMenu, int hInstance, int lParam)
            throws NativeException, IllegalAccessException {
        JNative n = null;
        try {
            n = new JNative(DLL_NAME, "CreateWindowExA");
            n.setRetVal(Type.INT);
            int i = 0;
            n.setParameter(i++, Type.INT, "" + dwExStyle);
            n.setParameter(i++, lpClassName.getValue());
            if(lpWindowName == null) {
                n.setParameter(i++, 0);
            } else {
                n.setParameter(i++, lpWindowName);
            }
            n.setParameter(i++, Type.INT, "" + dwStyle);
            n.setParameter(i++, Type.INT, "" + x);
            n.setParameter(i++, Type.INT, "" + y);
            n.setParameter(i++, Type.INT, "" + nWidth);
            n.setParameter(i++, Type.INT, "" + nHeight);
            n.setParameter(i++, Type.INT, "" + hWndParent);
            n.setParameter(i++, Type.INT, "" + hMenu);
            n
                    .setParameter(i++, Type.INT, ""
                            + (hInstance == 0 ? JNative.getCurrentModule()
                            : hInstance));
            n.setParameter(i++, Type.INT, "" + lParam);
            n.invoke();
            return Integer.parseInt(n.getRetVal());
        } finally {
            if (n != null) {
                n.dispose();
            }
        }
    }

    public static final int MessageBox(int parentHandle, String message,
                                       String caption, int buttons) throws NativeException,
            IllegalAccessException {
        JNative n = null;
        try {
            n = new JNative(DLL_NAME, "MessageBoxA");
            n.setRetVal(Type.INT);
            int i = 0;
            n.setParameter(i++, Type.INT, "" + parentHandle);
            n.setParameter(i++, Type.STRING, message);
            n.setParameter(i++, Type.STRING, caption);
            n.setParameter(i++, Type.INT, "" + buttons);
            n.invoke();
            return Integer.parseInt(n.getRetVal());
        } finally {
            if (n != null)
                n.dispose();
        }
    }

    /**
     * <pre>
     *  EnumWindows Function
     *
     *   The EnumWindows function enumerates all top-level windows on the screen by passing the handle to each window, in turn, to an application-defined callback function. EnumWindows continues until the last top-level window is enumerated or the callback function returns FALSE.
     *
     *   Syntax
     *
     *   BOOL EnumWindows(
     *
     *   WNDENUMPROC lpEnumFunc,
     *   LPARAM lParam
     *   );
     *
     *   Parameters
     *
     *   lpEnumFunc
     *   [in] Pointer to an application-defined callback function. For more information, see EnumWindowsProc.
     *   lParam
     *   [in] Specifies an application-defined value to be passed to the callback function.
     *
     *   Return Value
     *
     *   If the function succeeds, the return value is nonzero.
     *
     *   If the function fails, the return value is zero. To get extended error information, call GetLastError.
     *
     *   If EnumWindowsProc returns zero, the return value is also zero. In this case, the callback function should call SetLastError to obtain a meaningful error code to be returned to the caller of EnumWindows.
     *
     *
     *   Remarks
     *
     *   The EnumWindows function does not enumerate child windows, with the exception of a few top-level windows owned by the system that have the WS_CHILD style.
     *
     *   This function is more reliable than calling the GetWindow function in a loop. An application that calls GetWindow to perform this task risks being caught in an infinite loop or referencing a handle to a window that has been destroyed.
     *
     * </pre>
     *
     * lpEnumFunc must be the address returned by JNative.createCallback()
     */

    public static boolean EnumWindows(Callback lpEnumFunc, int lParam)
            throws NativeException, IllegalAccessException {
        if (nEnumWindows == null) {
            nEnumWindows = new JNative(DLL_NAME, "EnumWindows", false);
            nEnumWindows.setRetVal(Type.INT);
        }
        nEnumWindows.setParameter(0, lpEnumFunc.getCallbackAddress());
        nEnumWindows.setParameter(1, lParam);
        nEnumWindows.invoke();
        return !"0".equals(nEnumWindows.getRetVal());
    }

    public static String GetWindowText(HWND hwnd) throws NativeException,
            IllegalAccessException {
        if (nGetWindowText == null) {
            nGetWindowText = new JNative(DLL_NAME, "GetWindowTextA");
            nGetWindowText.setRetVal(Type.INT);
            nBufferGWT = new Pointer(MemoryBlockFactory.createMemoryBlock(512));
            nGetWindowText.setParameter(1, nBufferGWT);
            nGetWindowText.setParameter(2, nBufferGWT.getSize());
        }
        nGetWindowText.setParameter(0, hwnd.getValue());
        nGetWindowText.invoke();
        if ("0".equals(nGetWindowText.getRetVal())) {
            return "";
        } else {
            return nBufferGWT.getAsString();
        }
    }

    /**
     * LONG SetWindowLong(
     *  HWND hWnd,
     *  int nIndex,
     *  LONG dwNewLong
     * );
     * @throws NativeException
     * @throws IllegalAccessException
     */
    public static int SetWindowLong(HWND hwnd, int nIndex, LONG dwNewLong) throws NativeException, IllegalAccessException {
        JNative _setWindowLong = new JNative(DLL_NAME, "SetWindowLongA");
        _setWindowLong.setRetVal(Type.INT);

        _setWindowLong.setParameter(0, hwnd.getValue());
        _setWindowLong.setParameter(1, nIndex);
        _setWindowLong.setParameter(2, dwNewLong.getValue());
        _setWindowLong.invoke();
        int ret = _setWindowLong.getRetValAsInt();
        _setWindowLong.dispose();
        return ret;
    }


    /**
     * <pre>
     * RegisterClass Function

     The RegisterClass function registers a window class for subsequent use in calls to the CreateWindow or CreateWindowEx function.

     The RegisterClass function has been superseded by the RegisterClassEx function. You can still use RegisterClass, however, if you do not need to set the class small icon.

     Syntax

     ATOM RegisterClass(

     public static final int WNDCLASS *lpWndClass
     );

     Parameters

     lpWndClass
     [in] Pointer to a WNDCLASS structure. You must fill the structure with the appropriate class attributes before passing it to the function.

     Return Value

     If the function succeeds, the return value is a class atom that uniquely identifies the class being registered. This atom can only be used by the CreateWindow, CreateWindowEx, GetClassInfo, GetClassInfoEx, FindWindow, FindWindowEx, and UnregisterClass functions and the IActiveIMMap::FilterClientWindows method.

     If the function fails, the return value is zero. To get extended error information, call GetLastError.
     * </pre>
     * @return
     * @throws IllegalAccessException
     * @throws NativeException
     */
    public static LONG RegisterClass(WNDCLASS lpWndClass) throws NativeException, IllegalAccessException {
        JNative registerClass = new JNative(DLL_NAME, "RegisterClassA");
        registerClass.setRetVal(Type.INT);

        registerClass.setParameter(0, lpWndClass.createPointer());
        registerClass.invoke();
        int i = registerClass.getRetValAsInt();
        registerClass.dispose();
        return new LONG(i);
    }

    /**
     * <pre>
     HICON LoadIcon(

     HINSTANCE hInstance,
     LPCTSTR lpIconName
     );

     Parameters

     hInstance
     [in] Handle to an instance of the module whose executable file contains the icon to be loaded. This parameter must be NULL when a standard icon is being loaded.
     lpIconName
     [in]

     Pointer to a null-terminated string that contains the name of the icon resource to be loaded. Alternatively, this parameter can contain the resource identifier in the low-order word and zero in the high-order word. Use the MAKEINTRESOURCE macro to create this value.

     To use one of the predefined icons, set the hInstance parameter to NULL and the lpIconName parameter to one of the following values.

     IDI_APPLICATION
     Default application icon.
     IDI_ASTERISK
     Same as IDI_INFORMATION.
     IDI_ERROR
     Hand-shaped icon.
     IDI_EXCLAMATION
     Same as IDI_WARNING.
     IDI_HAND
     Same as IDI_ERROR.
     IDI_INFORMATION
     Asterisk icon.
     IDI_QUESTION
     Question mark icon.
     IDI_WARNING
     Exclamation point icon.
     IDI_WINLOGO
     Windows logo icon. Windows XP: Default application icon.
     IDI_SHIELD
     Security Shield icon.

     Return Value

     If the function succeeds, the return value is a handle to the newly loaded icon.

     If the function fails, the return value is NULL. To get extended error information, call GetLastError.

     </pre>
     * @throws IllegalAccessException
     * @throws NativeException
     */

    public static LONG LoadIcon(LONG hInstance, String lpIconName) throws NativeException, IllegalAccessException {
        JNative loadIcon = new JNative(DLL_NAME, "LoadIconA");
        loadIcon.setRetVal(Type.INT);

        loadIcon.setParameter(0, hInstance.getValue());
        loadIcon.setParameter(1, lpIconName);
        loadIcon.invoke();
        int ret = loadIcon.getRetValAsInt();
        loadIcon.dispose();
        return new LONG(ret);
    }

    public static LONG LoadIcon(LONG hInstance, int ressource) throws NativeException, IllegalAccessException {
        JNative loadIcon = new JNative(DLL_NAME, "LoadIconA");
        loadIcon.setRetVal(Type.INT);

        loadIcon.setParameter(0, hInstance.getValue());
        loadIcon.setParameter(1, ressource);
        loadIcon.invoke();
        int ret = loadIcon.getRetValAsInt();
        loadIcon.dispose();

        return new LONG(ret);
    }
    /**
     * <pre>
     * SendMessage Function

     The SendMessage function sends the specified message to a window or windows. It calls the window procedure for the specified window and does not return until the window procedure has processed the message.

     To send a message and return immediately, use the SendMessageCallback or SendNotifyMessage function. To post a message to a thread's message queue and return immediately, use the PostMessage or PostThreadMessage function.

     Syntax

     LRESULT SendMessage(

     HWND hWnd,
     UINT Msg,
     WPARAM wParam,
     LPARAM lParam
     );

     Parameters

     hWnd
     [in] Handle to the window whose window procedure will receive the message. If this parameter is HWND_BROADCAST, the message is sent to all top-level windows in the system, including disabled or invisible unowned windows, overlapped windows, and pop-up windows; but the message is not sent to child windows.
     Msg
     [in] Specifies the message to be sent.
     wParam
     [in] Specifies additional message-specific information.
     lParam
     [in] Specifies additional message-specific information.

     Return Value

     The return value specifies the result of the message processing; it depends on the message sent.
     </pre>
     * @throws IllegalAccessException
     * @throws NativeException
     */
    public static LRESULT SendMessage(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam) throws NativeException, IllegalAccessException {
        JNative SendMessage = new JNative(DLL_NAME, "SendMessageA");
        SendMessage.setRetVal(Type.INT);
        int pos = 0;
        SendMessage.setParameter(pos++, hWnd.getValue());
        SendMessage.setParameter(pos++, Msg.getValue());
        SendMessage.setParameter(pos++, wParam.getValue());
        SendMessage.setParameter(pos++, lParam.getValue());
        SendMessage.invoke();
        pos = SendMessage.getRetValAsInt();
        SendMessage.dispose();
        return new LRESULT(pos);
    }

    /*
     if the ImageIcon is not readable by PixelGrabber you need to make use of external libs to generate a valid ImageIcon
     I.e. *.ico images cannot be grabbed. Use i.e. aclibico to generate a valid ImageIcon:
     try {
        if(Class.forName("com.ctreber.aclib.image.ico.ICOFile") != null) {
            final ICOFile lICOFile = new ICOFile(path);
            final Iterator lDescIt = lICOFile.getDescriptors().iterator();
            while (lDescIt.hasNext()) {
                final BitmapDescriptor lDescriptor = (BitmapDescriptor) lDescIt.next();
                final Image img = lDescriptor.getImageRGB();
                if (img != null) {
                    return new ImageIcon(img);
                }
            }
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
     **/
    public static int[] grabImage(ImageIcon icon) throws InterruptedException {

        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        int[] pixels = new int[w * h];

        // Collect pixel data in array
        PixelGrabber pg = new PixelGrabber(icon.getImage(), 0, 0, w, h, pixels, 0, w);
        pg.grabPixels();
        if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
            return null;
        }
        return pixels;
    }

    public static LONG CreateIcon(LONG hInstance, int cPlanes, int cBitsPixel, ImageIcon icon) throws InterruptedException, NativeException, IllegalAccessException {
        if(icon == null)
            return new LONG(0);
        int[] pixels = grabImage(icon);
        if(pixels == null)
            return new LONG(0);
        return CreateIcon(hInstance, icon.getIconWidth(), icon.getIconHeight(), cPlanes, cBitsPixel, pixels, pixels);
    }

    /*
    HINSTANCE hInstance,
    int nWidth,
    int nHeight,
    BYTE cPlanes,
    BYTE cBitsPixel,
    public static final int BYTE *lpbANDbits,
    public static final int BYTE *lpbXORbits
 */
    public static LONG CreateIcon(LONG hInstance, int nWidth, int nHeight, int cPlanes,
                                  int cBitsPixel, int[] lpbANDbits, int[] lpbXORbits)
            throws NativeException, IllegalAccessException {

        JNative createIcon = new JNative(DLL_NAME, "CreateIcon");
        Pointer p = new Pointer(MemoryBlockFactory.createMemoryBlock(lpbXORbits.length*4));
        for(int i = 0; i<lpbXORbits.length; i++) {
            p.setIntAt(i*4,lpbXORbits[i]);
        }
        createIcon.setRetVal(Type.INT);

        int i = 0;
        createIcon.setParameter(i++, hInstance.getValue());
        createIcon.setParameter(i++, nWidth);
        createIcon.setParameter(i++, nHeight);
        createIcon.setParameter(i++, cPlanes);
        createIcon.setParameter(i++, cBitsPixel);
        createIcon.setParameter(i++, p);
        createIcon.setParameter(i++, p);

        createIcon.invoke();
        i = createIcon.getRetValAsInt();
        createIcon.dispose();

        return new LONG(i);
    }
    /*
    LONG GetWindowLong(
        HWND hWnd,
        int nIndex
    );
     */

    public static LONG GetWindowLong(HWND hWnd,
                                     int nIndex) throws NativeException, IllegalAccessException {

        JNative GetWindowLong = new JNative(DLL_NAME, "GetWindowLongA");
        GetWindowLong.setRetVal(Type.INT);
        int pos = 0;
        GetWindowLong.setParameter(pos++, hWnd.getValue());
        GetWindowLong.setParameter(pos++, nIndex);
        GetWindowLong.invoke();
        pos = GetWindowLong.getRetValAsInt();
        GetWindowLong.dispose();
        return new LONG(pos);
    }

    /*
    BOOL WINAPI AttachThreadInput(
    DWORD idAttach,
    DWORD idAttachTo,
    BOOL fAttach
    );
     */
    public static boolean AttachThreadInput(int idAttach,
                                            int idAttachTo,
                                            boolean fAttach)
            throws NativeException, IllegalAccessException {
        JNative AttachThreadInput = new JNative(DLL_NAME, "AttachThreadInput");
        AttachThreadInput.setRetVal(Type.INT);
        int pos = 0;
        AttachThreadInput.setParameter(pos++, idAttach);
        AttachThreadInput.setParameter(pos++, idAttachTo);
        AttachThreadInput.setParameter(pos++, fAttach ? "1" : "0");
        AttachThreadInput.invoke();
        pos = AttachThreadInput.getRetValAsInt();

        AttachThreadInput.dispose();
        if(pos == 0)
            return false;
        return true;
    }
    /*
     *HWND GetForegroundWindow(VOID);
     */
    public static HWND GetForegroundWindow() throws NativeException, IllegalAccessException {
        JNative GetForegroundWindow = new JNative(DLL_NAME, "GetForegroundWindow");
        GetForegroundWindow.setRetVal(Type.INT);

        GetForegroundWindow.invoke();
        int ret = GetForegroundWindow.getRetValAsInt();
        GetForegroundWindow.dispose();
        return new HWND(ret);
    }

    /*
     *HWND SetActiveWindow(
    HWND hWnd
    );
     */
    public static HWND SetActiveWindow(HWND hWnd) throws NativeException, IllegalAccessException {
        JNative SetActiveWindow = new JNative(DLL_NAME, "SetActiveWindow");
        SetActiveWindow.setRetVal(Type.INT);
        int pos = 0;
        SetActiveWindow.setParameter(pos++, hWnd.getValue());
        SetActiveWindow.invoke();
        pos = SetActiveWindow.getRetValAsInt();

        SetActiveWindow.dispose();
        return new HWND(pos);
    }

    /*
     BOOL SetForegroundWindow(
           HWND hWnd
        );
     */
    public static boolean SetForegroundWindow(HWND hWnd) throws NativeException, IllegalAccessException {
        JNative SetForegroundWindow = new JNative(DLL_NAME, "SetForegroundWindow");
        SetForegroundWindow.setRetVal(Type.INT);
        int pos = 0;
        SetForegroundWindow.setParameter(pos++, hWnd.getValue());
        SetForegroundWindow.invoke();
        pos = SetForegroundWindow.getRetValAsInt();

        SetForegroundWindow.dispose();
        if(pos == 0)
            return false;
        return true;
    }

    public static boolean SetForegroundWindowEx(HWND hWnd) throws NativeException, IllegalAccessException {
        int lThreadWindow = GetWindowThreadProcessId(hWnd);
        int lThreadForeWin = GetWindowThreadProcessId(GetForegroundWindow());
        if(lThreadWindow == lThreadForeWin) {
            return SetForegroundWindow(hWnd);
        } else {
            AttachThreadInput(lThreadForeWin, lThreadWindow, true);
            boolean b = SetForegroundWindow(hWnd);
            AttachThreadInput(lThreadForeWin, lThreadWindow, false);
            return b;
        }
    }

     /*
    BOOL DestroyIcon(
        HICON hIcon
    );
      */

    public static boolean DestroyIcon(LONG hIcon) throws NativeException, IllegalAccessException {
        JNative DestroyIcon = new JNative(DLL_NAME, "DestroyIcon");
        DestroyIcon.setRetVal(Type.INT);
        int pos = 0;
        DestroyIcon.setParameter(pos++, hIcon.getValue());
        DestroyIcon.invoke();
        pos = DestroyIcon.getRetValAsInt();

        DestroyIcon.dispose();
        if(pos == 0)
            return false;
        return true;
    }

    /*
    UINT RegisterWindowMessage(
   LPCTSTR lpString
   );
     **/
    public static int RegisterWindowMessage(String lpString) throws NativeException, IllegalAccessException {
        JNative RegisterWindowMessage = new JNative(DLL_NAME, "RegisterWindowMessageA");
        RegisterWindowMessage.setRetVal(Type.INT);
        int pos = 0;
        RegisterWindowMessage.setParameter(pos++, lpString);
        RegisterWindowMessage.invoke();
        pos = RegisterWindowMessage.getRetValAsInt();

        RegisterWindowMessage.dispose();

        return pos;
    }

    /*
     *BOOL SetWindowPos(
    HWND hWnd,
    HWND hWndInsertAfter,
    int X,
    int Y,
    int cx,
    int cy,
    UINT uFlags
    );
     **/
    public static boolean SetWindowPos(HWND hWnd,
                                       HWND hWndInsertAfter,
                                       int X,
                                       int Y,
                                       int cx,
                                       int cy,
                                       int uFlags)
            throws NativeException, IllegalAccessException {
        JNative SetWindowPos = new JNative(DLL_NAME, "SetWindowPos");
        SetWindowPos.setRetVal(Type.INT);
        int pos = 0;
        SetWindowPos.setParameter(pos++, hWnd.getValue());
        SetWindowPos.setParameter(pos++, hWndInsertAfter.getValue());
        SetWindowPos.setParameter(pos++, X);
        SetWindowPos.setParameter(pos++, Y);
        SetWindowPos.setParameter(pos++, cx);
        SetWindowPos.setParameter(pos++, cy);
        SetWindowPos.setParameter(pos++, uFlags);
        SetWindowPos.invoke();
        pos = SetWindowPos.getRetValAsInt();

        SetWindowPos.dispose();
        if(pos == 0)
            return false;
        return true;
    }

    /*public static boolean setOnTop(HWND hWnd) {
        try {
            return User32.SetWindowPos(hWnd, HWND_TOPMOST, 0, 0, 0, 0, SWP_NOMOVE|SWP_NOSIZE);
        } catch(Exception ee) {
            ee.printStackTrace();
        }
        return false;
    }*/

    /*
     BOOL RegisterHotKey(
    HWND hWnd,
    int id,
    UINT fsModifiers,
    UINT vk
    );
     */
    public static boolean RegisterHotKey(HWND hWnd,
                                         int id,
                                         int fsModifiers,
                                         int vk)
            throws NativeException, IllegalAccessException {
        JNative RegisterHotKey = new JNative(DLL_NAME, "RegisterHotKey");
        RegisterHotKey.setRetVal(Type.INT);
        int pos = 0;
        RegisterHotKey.setParameter(pos++, hWnd.getValue());
        RegisterHotKey.setParameter(pos++, id);
        RegisterHotKey.setParameter(pos++, fsModifiers);
        RegisterHotKey.setParameter(pos++, vk);

        RegisterHotKey.invoke();
        pos = RegisterHotKey.getRetValAsInt();

        RegisterHotKey.dispose();
        if(pos == 0)
            return false;
        return true;
    }

    /*
    BOOL UnregisterHotKey(
    HWND hWnd,
    int id
    );
    */
    public static boolean UnregisterHotKey(String lpString) throws NativeException, IllegalAccessException {
        JNative UnregisterHotKey = new JNative(DLL_NAME, "UnregisterHotKey");
        UnregisterHotKey.setRetVal(Type.INT);
        int pos = 0;
        UnregisterHotKey.setParameter(pos++, lpString);
        UnregisterHotKey.invoke();
        pos = UnregisterHotKey.getRetValAsInt();

        UnregisterHotKey.dispose();
        if(pos == 0)
            return false;
        return true;
    }

    public static void main(String[] args) throws NativeException, IllegalAccessException{
        User32.MessageBox(100,"这是使用jnative练习", "jnativetest", 1);
    }
}
