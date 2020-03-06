package capstone.spring20.tscc_mobile.Api;

public class ApiController {
    public static String base_url1 = "localhost:5000/api";

    public static TSCCClient getTsccClient() {
        return RetrofitClient.getClient(base_url1).create(TSCCClient.class);
    }
}
