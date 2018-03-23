import net.ryian.autocode.AutoCode;

import java.sql.SQLException;

/**
 * Created by allenwc on 16/2/18.
 */
public class Main {

    public static void main(String[] args) {
        try {
            AutoCode.compiler(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
