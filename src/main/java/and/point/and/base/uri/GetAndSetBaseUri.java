package and.point.and.base.uri;

import config.Config;
import io.restassured.RestAssured;

public class GetAndSetBaseUri {
    private static  String baseUri;
    private static String configFilePath;

    public GetAndSetBaseUri(String configFilePath){
        GetAndSetBaseUri.configFilePath = configFilePath;
    }

    public void setUp(){
        Config config =new Config(configFilePath);
        baseUri = config.getBaseUri();
        RestAssured.baseURI = baseUri;
    }

    public static String getBaseUri() {
        return baseUri;
    }
}
