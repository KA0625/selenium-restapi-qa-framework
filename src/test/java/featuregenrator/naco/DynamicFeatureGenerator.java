package featuregenrator.naco;

import java.io.FileWriter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import naco.hooks.Hooks;

public class DynamicFeatureGenerator {

	public static void generate() throws Exception {
	
		
		Map<String, List<Object[]>> grouped = new LinkedHashMap<>();

		for (Object[] row : Hooks.dynamicData) {
			String state = (String) row[0];
			grouped.computeIfAbsent(state, k -> new ArrayList<>()).add(row);
		}

		// Generate one feature file per state
		for (Map.Entry<String, List<Object[]>> entry : grouped.entrySet()) {

			String state = entry.getKey();
			List<Object[]> counties = entry.getValue();

			String fileName = "src/test/java/naco/CucumberFeature/County_" + state + ".feature";
			FileWriter fw = new FileWriter(fileName);

			fw.write("Feature: County Website Validation for " + state + "\n\n");

			fw.write("Background:\n");
			fw.write("  Given User fetches state codes from database\n");
			fw.write("  And User navigates to County Explorer page\n");
			fw.write("  And User retrieves county and fips for state \"" + state + "\"\n\n");

			for (Object[] row : counties) {
				String county = (String) row[1];
				String fips = (String) row[2];

				fw.write("Scenario: Validate county " + county + "\n");
				fw.write("  When User validates website for county \"" + county + "\" with fips \"" + fips + "\"\n\n");
			}

			fw.close();
		}
	}
}
