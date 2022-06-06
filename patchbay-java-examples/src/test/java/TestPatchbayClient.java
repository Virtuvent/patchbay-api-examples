import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestPatchbayClient {
    String appId = System.getenv("PATCHBAY_APP_ID");
    String secret = System.getenv("PATCHBAY_SECRET");

    PatchbayClient client = new PatchbayClient(
            appId,
            secret
    );

    @Test
    public void testListStreams() {
        try {
            Response response = client.listStreams();
            Assertions.assertEquals(200, response.code());

            Assertions.assertNotNull(response.body());
            String jsonString = response.body().string();
            JsonObject jsonResponse = new Gson().fromJson(jsonString, JsonObject.class);
            JsonArray streamsList = jsonResponse.getAsJsonArray("streams");

            Assertions.assertTrue(streamsList.size() > 0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
