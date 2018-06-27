package org.javajefe.pocs;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

/**
 * Created by Alexander Bukarev on 27.06.2018.
 */
public class Main {

    private static final int[] numbers = new int[]{1, 2, 3, 5, 6, 9, 25};
    private KieContainer kc;

    public static void main(String[] args) {
        Main main = new Main();
        for (int number: numbers) {
            main.analyzeNumber(number);
        }
    }

    public Main() {
        // KieServices is the factory for all KIE services
        KieServices ks = KieServices.Factory.get();

        // From the kie services, a container is created from the classpath
        kc = ks.getKieClasspathContainer();
    }

    public void analyzeNumber(int number) {
        System.out.println("Analyzing the number " + number);
        KieSession kieSession = kc.newKieSession("NumbersKS");
        kieSession.insert(new TheNumber(number));
        kieSession.fireAllRules();

        QueryResults results = kieSession.getQueryResults("Get all decisions");
        for (QueryResultsRow row: results) {
            System.out.println(row.get("$decision"));
        }

        kieSession.destroy();
        System.out.println("------------------------------");
    }
}
