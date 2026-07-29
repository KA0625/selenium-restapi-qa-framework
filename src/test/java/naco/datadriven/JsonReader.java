package naco.datadriven;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {

	

	public List<HashMap<String, String>> getJsonData(String string) throws IOException {

		String jsonfilepath = System.getProperty("user.dir") + "/src/test/resources/" + string;

		String content = FileUtils.readFileToString(new File(jsonfilepath), StandardCharsets.UTF_8);
		ObjectMapper obm = new ObjectMapper();
		List<HashMap<String, String>> data = obm.readValue(content, new TypeReference<List<HashMap<String, String>>>() {
		});

		return data;
	}
	public List<String> getStateCodestc6Json() throws IOException {

        List<HashMap<String, String>> data = getJsonData("SouthEast.json");
        List<String> stateCodes = new ArrayList<>();

        for (HashMap<String, String> map : data) {
            stateCodes.add(map.get("state"));  
        }

        return stateCodes;
    }
	
	public Object[][] getDatatc1() throws IOException {
		
		List <HashMap<String,String>> data=	getJsonData("data.json");
		System.out.println("JSON DATA: " + data);
		
	    Object[][] dp = new Object[data.size()][1];

	    for (int i = 0; i < data.size(); i++) {
	        dp[i][0] = data.get(i);

	    }
		return dp;
       }
	
}
