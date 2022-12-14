package org.acme.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
  * OpenID Connect Configuration to define where to get access token.
 **/
public class OIDC  {

    /**
      * OpenID Connect Configuration to define where to get access token.
     **/
    private String tokenEndpoint;

    /**
    * Get tokenEndpoint
    * @return tokenEndpoint
    **/
    @JsonProperty("token_endpoint")
    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    /**
     * Set tokenEndpoint
     **/
    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    public OIDC tokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OIDC {\n");

        sb.append("    tokenEndpoint: ").append(toIndentedString(tokenEndpoint)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}