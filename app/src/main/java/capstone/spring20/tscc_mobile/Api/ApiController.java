package capstone.spring20.tscc_mobile.Api;

public class ApiController {
    public static String base_url1 = "http://resolve-trash.azurewebsites.net";

    public static TSCCClient getTsccClient() {
        return RetrofitClient.getClient(base_url1).create(TSCCClient.class);
    }
}
