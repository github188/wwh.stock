#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package};

        import net.ryian.autocode.AutoCode;

public class Generator {


    public static void main(String[] s) {
        try {
            AutoCode.compiler(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
