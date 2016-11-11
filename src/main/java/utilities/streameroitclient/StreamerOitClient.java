package utilities.streameroitclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import lostandfound.exceptions.StreamerOitException;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.PropertiesLoader;
import utilities.streameroitclient.response.ComplexStreamerOitInfoResponse;
import utilities.streameroitclient.response.StreamerOitInfoResponse;

import java.util.Properties;

public class StreamerOitClient {
    private final static Properties properties = PropertiesLoader.loadPropertiesFromPackage("duke.properties");

    public StreamerOitInfoResponse getInfoByUniqueId(String uniqueId) {
        StreamerOitInfoResponse response;
        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get(properties.getProperty("duke.streamer.people.url"))
                    .queryString("access_token", properties.getProperty("duke.streamer.authtoken"))
                    .queryString("q", uniqueId).asJson();
            JSONArray jsonResponse = httpResponse.getBody().getArray();
            response = new ObjectMapper().readValue(jsonResponse.getJSONObject(0).toString(), StreamerOitInfoResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StreamerOitException("This API cannot find anyone with " + uniqueId);
        }
        return response;
    }

    public ComplexStreamerOitInfoResponse getInfoByNetId(String netId) {
        ComplexStreamerOitInfoResponse response;
        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get(properties.getProperty("duke.streamer.people.url") + "/netid/{net_id}")
                    .routeParam("net_id", netId)
                    .queryString("access_token", properties.getProperty("duke.streamer.authtoken")).asJson();
            JSONObject jsonResponse = httpResponse.getBody().getArray().getJSONObject(0);
            response = new ObjectMapper().readValue(jsonResponse.toString(), ComplexStreamerOitInfoResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StreamerOitException("This API cannot find anyone with " + netId);
        }
        return response;
    }

}
