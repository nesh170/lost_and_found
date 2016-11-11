package utilities.streameroitclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StreamerOitInfoResponse {
    public String ldapkey;
    public String sn;
    public String givenName;
    public String duid;
    public String netid;
    public String display_name;
    public String url;

    public StreamerOitInfoResponse(@JsonProperty("duid")String duid, @JsonProperty("netid")String netid, @JsonProperty("givenName")String givenName,
                                   @JsonProperty("ldapkey")String ldapkey, @JsonProperty("sn")String sn, @JsonProperty("display_name")String display_name,
                                   @JsonProperty("url")String url) {
        this.ldapkey = ldapkey;
        this.sn = sn;
        this.givenName = givenName;
        this.duid = duid;
        this.netid = netid;
        this.display_name = display_name;
        this.url = url;
    }
}
