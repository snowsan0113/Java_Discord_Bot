package snowsan0113.discord_bot.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ConfigManager {

    private static final Gson gson;

    static  {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public static String getToken() throws IOException {
        JsonObject raw_json = getRawJson();
        JsonObject bot_obj = raw_json.getAsJsonObject("bot_setting");
        return bot_obj.get("token").getAsString();
    }

    public static JsonObject getRawJson() throws IOException {
        createJson();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
            return gson.fromJson(reader, JsonObject.class);
        }
    }

    public static void writeFile(String date) {
        try (BufferedWriter write = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
            write.write(date);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createJson() throws IOException {
        if (!getFile().exists()) {
            getFile().createNewFile();

            JsonObject bot_obj = new JsonObject();
            bot_obj.addProperty("token", "");
            JsonObject json_obj = new JsonObject();
            json_obj.add("bot_setting", bot_obj);

            writeFile(json_obj.toString());
            System.out.print(getFile().toPath() + "にBOT設定ファイルを作成しました。");
        }
    }

    public static File getFile() {
        return new File("bot_config.json");
    }
}
