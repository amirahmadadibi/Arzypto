package projects.com.amirahmadadibi.arzypto.Network;

public class OkhttpRequestBuilder {
    private String mUrl;

    public OkhttpRequestBuilder setmUrl(String mUrl) {
        this.mUrl = mUrl;
        return this;
    }

    public OkhttpPostCall createGetRequest() {
        return new OkhttpPostCall(mUrl);
    }
}
