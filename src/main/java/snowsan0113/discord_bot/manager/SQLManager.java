package snowsan0113.discord_bot.manager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class SQLManager {

    private static String DRIVER_NAME; //ドライバーの名前
    private static String DB_HOST; //ホスト（IP）
    private static String DB_PORT; //ポート
    private static String JDBC_URL; //接続したいURL
    private static String USER_ID; //ログインしたいID
    private static String USER_PASS; //ログインしたいユーザーパスワード

    public SQLManager () throws IOException {
        DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
        DB_HOST = ConfigManager.getObjectValue("bot_setting.db.host");
        DB_PORT = ConfigManager.getObjectValue("bot_setting.db.port");
        JDBC_URL = "jdbc:mySQL://" + DB_HOST + ":" + DB_PORT + "/testdb?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
        USER_ID = ConfigManager.getObjectValue("bot_setting.db.user");
        USER_PASS = ConfigManager.getObjectValue("bot_setting.db.password");

        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void select() {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

            String sql = "INSERT INTO test (name, email) VALUES (?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, "田中太郎");
            ps.setString(2, "aa@example.com");
            ps.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
