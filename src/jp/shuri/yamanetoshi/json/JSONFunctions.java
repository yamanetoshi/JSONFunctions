package jp.shuri.yamanetoshi.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONFunctions {
    public static JSONObject getJSONfromURL(String url, DefaultHttpClient httpclient)
    	throws IOException, ClientProtocolException, RuntimeException, JSONException {
        InputStream is = null;
        
        HttpGet httpget = new HttpGet(url);
        is = httpclient.execute(httpget,
				new ResponseHandler<InputStream>() {

					@Override
					public InputStream handleResponse(HttpResponse response)
							throws ClientProtocolException, IOException {
						switch (response.getStatusLine().getStatusCode()) {
						case HttpStatus.SC_OK:
							return response.getEntity().getContent();
						default:
							throw new RuntimeException("HTTP Status is " + 
										   response.getStatusLine().getStatusCode());
						}
					}
        });

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
        	sb.append(line + "\n");
        }
        is.close();
        
        return new JSONObject(sb.toString());            
    }
}
