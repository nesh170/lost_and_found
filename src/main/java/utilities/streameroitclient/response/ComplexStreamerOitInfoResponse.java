package utilities.streameroitclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ComplexStreamerOitInfoResponse extends StreamerOitInfoResponse {
    public String nickname;
    public String primary_affiliation;
    public List<String> emails;
    public List<String> post_office_box;


    public ComplexStreamerOitInfoResponse(@JsonProperty("duid")String duid, @JsonProperty("netid")String netid, @JsonProperty("givenName")String givenName,
                                          @JsonProperty("ldapkey")String ldapkey, @JsonProperty("sn")String sn, @JsonProperty("display_name")String display_name,
                                          @JsonProperty("url")String url, @JsonProperty("nickname")String nickname, @JsonProperty("primary_affiliation")String primary_affiliation,
                                          @JsonProperty("emails")List<String> emails, @JsonProperty("post_office_box")List<String> post_office_box) {
        super(ldapkey, sn, givenName, duid, netid, display_name, url);
        this.nickname = nickname;
        this.primary_affiliation = primary_affiliation;
        this.emails = emails;
        this.post_office_box = post_office_box;
    }
}
