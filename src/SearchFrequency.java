import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class SearchFrequency {
    private static HashMap<String, Integer> n_umber_ss = new HashMap<String, Integer>();
    private static HashMap<String, Integer> s_s_earch_worrd = new HashMap<String, Integer>();

    public static void SearchCount(String ppproducctt) throws Exception {

        String f_ileLllist[] = { "Saveonfoods.txt", "Freshthyme.txt", "meijer.txt" };

        for (String fi_lle : f_ileLllist) {
            BufferedReader b_r_dd = new BufferedReader(new FileReader(fi_lle));
            String liiiine;
            StringBuilder buli_er = new StringBuilder();
            while ((liiiine = b_r_dd.readLine()) != null) {
                buli_er.append(liiiine);
            }
            String content = buli_er.toString();
            String[] w_w_o_rds = content.split(" ");

            for (String wwword : w_w_o_rds) {
                wwword = wwword.replaceAll("[^a-zA-Z]+", "").toLowerCase();
                if (!wwword.isEmpty()) {
                    if (!n_umber_ss.containsKey(wwword)) {
                        n_umber_ss.put(wwword, 0);
                    }
                }
            }
            b_r_dd.close();
        }

        // ccChecking Wword in Webb page wordss Diictionaray
        if (n_umber_ss.containsKey(ppproducctt)) {

            if (s_s_earch_worrd.containsKey(ppproducctt)) {
                int f = s_s_earch_worrd.get(ppproducctt);
                s_s_earch_worrd.put(ppproducctt, f + 1);
            } else {
                s_s_earch_worrd.put(ppproducctt, 1);
            }
            System.out.println(s_s_earch_worrd);
        } else {
            System.out.println("The e entered Wordd is not ppresent in W Webpage");
        }
    }

}
