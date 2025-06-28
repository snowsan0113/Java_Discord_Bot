package snowsan0113.discord_bot.manager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

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

    public int select(UUID uuid, String discordID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
            con.setAutoCommit(false);

            PreparedStatement checkStmt = con.prepareStatement(
                    "SELECT * FROM player_links WHERE uuid = ? AND discord_id = ?"
            );
            checkStmt.setString(1, uuid.toString());
            checkStmt.setString(2, discordID);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                //すでに登録済み
                return -1;
            }

            // プレイヤー登録
            PreparedStatement playerStmt = con.prepareStatement(
                    "INSERT IGNORE INTO minecraft_players (uuid, created_at) VALUES (?, ?)"
            );
            playerStmt.setString(1, uuid.toString());
            playerStmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            playerStmt.executeUpdate();

            // Discordユーザー登録
            PreparedStatement discordStmt = con.prepareStatement(
                    "INSERT IGNORE INTO discord_users (discord_id, created_at) VALUES (?, ?)"
            );
            discordStmt.setString(1, discordID);
            discordStmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            discordStmt.executeUpdate();

            PreparedStatement linkStmt = con.prepareStatement(
                    "INSERT INTO player_links (uuid, discord_id, linked_at) VALUES (?, ?, ?)"
            );
            linkStmt.setString(1, uuid.toString());
            linkStmt.setString(2, discordID);
            linkStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            linkStmt.executeUpdate();

            con.commit();
            return 0;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            con.rollback();
        }
        finally {
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return -2;
    }
}
