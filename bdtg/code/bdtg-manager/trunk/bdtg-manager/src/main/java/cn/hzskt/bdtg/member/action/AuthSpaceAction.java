package cn.hzskt.bdtg.member.action;

import cn.hzskt.bdtg.common.constants.DictKeys;
import cn.hzskt.bdtg.member.domain.AuthEmail;
import cn.hzskt.bdtg.member.domain.AuthRealname;
import cn.hzskt.bdtg.member.service.AuthEmailService;
import cn.hzskt.bdtg.member.service.AuthRealnameService;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.DictService;
import cn.hzskt.bdtg.sys.service.UserService;
import net.ryian.orm.domain.BaseEntity;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.service.AuthSpaceService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
*
* @description:AuthSpace action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/member/auth-space")
@SuppressWarnings("serial")
public class AuthSpaceAction extends MagicAction<AuthSpace,AuthSpaceService> {
    @Autowired
    DictService dictService;
    @Autowired
    private UserService userService;
    @Autowired
    AuthSpaceService authSpaceService;
    @Autowired
    AuthRealnameService authRealnameService;
    @Autowired
    AuthEmailService authEmailService;
    protected void beforeEdit(HttpServletRequest request,Model model, BaseEntity entity) {
        model.addAttribute("indusid",dictService.getDictsByKey(DictKeys.INDUSID).values());
    }
    @RequestMapping(value = "importExcel")
    public String  importExcel(HttpServletRequest request,Model model){
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
        String path = request.getSession().getServletContext().getRealPath("/")  + File.separator + "upload";
        MultipartFile file=multipartRequest.getFile("importExcel");
        Date time =new Date();
//        String updateName = time.getTime()+".xls";
        model.addAttribute("message","2");
//        try{
//            this.SaveFileFromInputStream(file.getInputStream(), path, updateName);
//        }catch(Exception ex){
//            model.addAttribute("message","1");
//            ex.printStackTrace();
//
//        }
//        File file1 = new File(path+ File.separator +updateName);

        // 臨時數組
        String[][] result=null;
        try {
            result = getData(file.getInputStream(), 1);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        if(result==null){
           model.addAttribute("message","1");
        }
        int rowLength = result.length;
        //默认从1 开始    本来是为0 剔除掉
        for(int i=0;i<rowLength;i++) {
            User user = new User();
            AuthSpace mem = new AuthSpace();
            Map param = new HashMap();
            param.put("userName", result[i][2]);
            List<User> list = userService.getByUname(param);
            if (list.size() > 0) {
            System.out.println("账号:"+result[i][2]+"已存在");
            } else {
                user.setName(result[i][1]);
                user.setUserName(result[i][2]);
//            user.encryptUserPassword("111111");
                userService.saveOrUpdate(user).toString();
                List<User> ulist = userService.query(param);
                if (ulist.size() > 0) {
                    User us = ulist.get(0);
                    mem.setUserId(us.getId().intValue());
                    mem.setUserName(result[i][2]);
                    mem.setName(result[i][1]);
                    mem.setPassword(us.getPassword());
                    mem.setSecCode(us.getPassword());
                    mem.setCompany(result[i][0]);
                    mem.setMobile(result[i][3]);
                    mem.setEmail(result[i][4]);
                    mem.setRegTime(new Date());
                    mem.setUserType(0);
                    mem.setAuthStatus(1);
                    mem.setEmailStatus(1);
                    mem.setMobileStatus(1);
                    authSpaceService.saveOrUpdate(mem);
                    AuthRealname real=new AuthRealname();
                    real.setIdpic("");
                    real.setIdpicDown("");
                    real.setName(result[i][1]);
                    real.setCode(result[i][5]);
                    real.setUserName(result[i][2]);
                    real.setUserId(us.getId());
                    real.setAuthStatus(1);
                    authRealnameService.saveOrUpdate(real);
                    AuthEmail email =new AuthEmail();
                    email.setUserId(us.getId());
                    email.setUserName(result[i][2]);
                    email.setEmail(result[i][4]);
                    email.setEmailStatus(1);
                    email.setCheckUrl("");
                    authEmailService.saveOrUpdate(email);
                } else {
                    System.out.println("账号" + result[i][2] + "插入失败");
                }


            }
        }
//        file1.delete();
//        return ajaxDoneFile(200, "上传成功", null);
        return getNameSpace() + "index";
    }

//    public static void saveFileFromInputStream(InputStream stream,String path,String savefile) throws IOException
//    {
//        FileOutputStream fs=new FileOutputStream( path + "/"+ savefile);
//          System.out.println("------------"+path + "/"+ savefile);
//        byte[] buffer =new byte[1024*1024];
//        int bytesum = 0;
//        int byteread = 0;
//        while ((byteread=stream.read(buffer))!=-1)
//        {
//            bytesum+=byteread;
//            fs.write(buffer,0,byteread);
//            fs.flush();
//        }
//        fs.close();
//        stream.close();
//    }

    public static String[][] getData(InputStream is, int ignoreRows)

            throws FileNotFoundException, IOException {

        List<String[]> result = new ArrayList<String[]>();

        int rowSize = 0;

        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XSSFCell cell = null;

        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

            XSSFSheet st = wb.getSheetAt(sheetIndex);

            // 第一行为标题，不取

            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {

                XSSFRow row = st.getRow(rowIndex);

                if (row == null) {

                    continue;

                }

                int tempRowSize = row.getLastCellNum() + 1;

                if (tempRowSize > rowSize) {

                    rowSize = tempRowSize;

                }

                String[] values = new String[rowSize];

                Arrays.fill(values, "");

                boolean hasValue = false;

                for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {

                    String value = "";

                    cell = row.getCell(columnIndex);

                    if (cell != null) {

                        // 注意：一定要设成这个，否则可能会出现乱码

//                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);

                        switch (cell.getCellType()) {

                            case XSSFCell.CELL_TYPE_STRING:

                                value = cell.getStringCellValue();

                                break;

                            case XSSFCell.CELL_TYPE_NUMERIC:

                                if (HSSFDateUtil.isCellDateFormatted(cell)) {

                                    Date date = cell.getDateCellValue();

                                    if (date != null) {

                                        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                                                .format(date);

                                    } else {

                                        value = "";

                                    }

                                } else {

                                    value = new DecimalFormat("0").format(cell

                                            .getNumericCellValue());

                                }

                                break;

                            case XSSFCell.CELL_TYPE_FORMULA:

                                // 导入时如果为公式生成的数据则无值

                                if (!cell.getStringCellValue().equals("")) {

                                    value = cell.getStringCellValue();

                                } else {

                                    value = cell.getNumericCellValue() + "";

                                }

                                break;

                            case XSSFCell.CELL_TYPE_BLANK:

                                break;

                            case XSSFCell.CELL_TYPE_ERROR:

                                value = "";

                                break;

                            case XSSFCell.CELL_TYPE_BOOLEAN:

                                value = (cell.getBooleanCellValue() == true ? "Y"

                                        : "N");

                                break;

                            default:

                                value = "";

                        }

                    }

                    if (columnIndex == 0 && value.trim().equals("")) {

                        break;

                    }

                    values[columnIndex] = rightTrim(value);

                    hasValue = true;

                }



                if (hasValue) {

                    result.add(values);

                }

            }

        }
        wb.close();
//        in.close();

        String[][] returnArray = new String[result.size()][rowSize];

        for (int i = 0; i < returnArray.length; i++) {

            returnArray[i] = (String[]) result.get(i);

        }

        return returnArray;

    }
    public static String rightTrim(String str) {

        if (str == null) {

            return "";

        }

        int length = str.length();

        for (int i = length - 1; i >= 0; i--) {

            if (str.charAt(i) != 0x20) {

                break;

            }

            length--;

        }

        return str.substring(0, length);

    }

    @RequestMapping(value = "import")
    public String update(HttpServletRequest request,Model model) {
        return getNameSpace() + "import";
    }
}
