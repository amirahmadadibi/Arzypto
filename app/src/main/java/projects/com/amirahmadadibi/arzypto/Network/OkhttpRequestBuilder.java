package projects.com.amirahmadadibi.arzypto.Network;

public class OkHttpGetRequestBuilder {
    private String mUrl;

    public OkHttpGetRequestBuilder setmUrl(String mUrl) {
        this.mUrl = mUrl;
        return this;
    }

    public OkHttpGetCall createGetRequest() {
        return new OkHttpGetCall(mUrl);
    }
}
