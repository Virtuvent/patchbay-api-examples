import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class PatchbayClient {
    OkHttpClient client;

    String authorizationToken;

    String BASE_URL = "https://live-captions.patchbay.net";

    public PatchbayClient(String appId, String apiSecret) {
        this.client = new OkHttpClient();

        Algorithm algorithm = Algorithm.HMAC256(apiSecret);

        Date issuedAt = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issuedAt);

        calendar.add(Calendar.HOUR_OF_DAY, 1);

        this.authorizationToken = JWT.create()
                .withIssuer(appId)
                .withIssuedAt(issuedAt)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public Response listStreams() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/v1/streams")
                .header("authorization", this.authorizationToken)
                .build();

        Call call = client.newCall(request);
        return call.execute();
    }
}
