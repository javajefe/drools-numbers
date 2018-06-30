package org.javajefe.pocs;

import org.javajefe.pocs.typed.TheNumber;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Bukarev on 27.06.2018.
 */
public class Main {

    private static final int[] numbers = new int[]{1, 2, 3, 5, 6, 9, 36};
    private KieContainer kc;

    public static void main(String[] args) {
        Main main = new Main();
        for (int number: numbers) {
            main.analyzeTypedNumber(number);
            main.analyzeUntypedNumber(number);
            System.out.println("------------------------------");
        }
    }

    public Main() {
        // KieServices is the factory for all KIE services
        KieServices ks = KieServices.Factory.get();

        // From the kie services, a container is created from the classpath
        kc = ks.getKieClasspathContainer();
    }

    public void analyzeTypedNumber(int number) {
        System.out.println("Analyzing the number (typed) " + number);
        runDrools("typed", new TheNumber(number));
    }

    public void analyzeUntypedNumber(int number) {
        System.out.println("Analyzing the number (untyped) " + number);
        Map<String, Object> numberMap = new HashMap<>();
        numberMap.put("value", number);
        numberMap.put("type", "the-number");
        runDrools("untyped", numberMap);
    }

    private void runDrools(String typeMode, Object fact) {
        String sessionName = null;
        switch (typeMode) {
            case "typed": sessionName = "TypedNumbersKS"; break;
            case "untyped": sessionName = "UntypedNumbersKS"; break;
            default: throw new IllegalArgumentException("Unknown type mode: " + typeMode);
        }

        KieSession kieSession = null;
        try {
            // We're using statefull session just in demo purpose
            kieSession = kc.newKieSession(sessionName);
            kieSession.insert(fact);
            kieSession.fireAllRules();

            QueryResults results = kieSession.getQueryResults("Get all decisions");
            for (QueryResultsRow row : results) {
                System.out.println(row.get("$decision"));
            }
        } finally {
            // We MUST destroy statefull session explicitly to avoid memory leaks
            if (kieSession != null)
                kieSession.destroy();
        }
    }
}
