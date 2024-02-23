import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// f fre frequency count code...
public class FrequencyCount {
    private static Map<String, Integer> qwe_fileF_requency_Map = new HashMap<>();

    private static void recurrenceOfWord(File f_op_fil_eee, Map<String, Map<String, Integer>> w_ordFr_equency_Mappp,
                                         String s_earch_W_ord) {
        try (Scanner aws_in = new Scanner(f_op_fil_eee)) {
            while (aws_in.hasNext()) {
                String we_wordSearch = aws_in.next().replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (we_wordSearch.equals(s_earch_W_ord.toLowerCase())) {
                    String o_p_fil_Nameee = f_op_fil_eee.getName();
                    Map<String, Integer> k_file_k_Frequency_mPMap = w_ordFr_equency_Mappp.getOrDefault(s_earch_W_ord, new HashMap<>());
                    k_file_k_Frequency_mPMap.put(o_p_fil_Nameee, k_file_k_Frequency_mPMap.getOrDefault(o_p_fil_Nameee, 0) + 1);
                    w_ordFr_equency_Mappp.put(s_earch_W_ord, k_file_k_Frequency_mPMap);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void searchingFolder(File f_hfolder, Map<String, Map<String, Integer>> op_wordFrequencyMap_p,
                                        String s__earch_Wordddd) {
        for (File qaswe__f_eeile_e : f_hfolder.listFiles()) {
            if (qaswe__f_eeile_e.isDirectory()) {
                searchingFolder(qaswe__f_eeile_e, op_wordFrequencyMap_p, s__earch_Wordddd);
            } else {
                recurrenceOfWord(qaswe__f_eeile_e, op_wordFrequencyMap_p, s__earch_Wordddd);
            }
        }
    }


    public static int Frequency(String po_pro_duc__t) {
        String qwp_fol_der__LList[] = {"freshDirectfiles", "SaveonfoodsFiles", "meijerFiles"};
        Map<String, Map<String, Integer>> CCcount_Frequenc_y = new HashMap<>();
        for (String f__pfolder_ppPath : qwp_fol_der__LList) {
            searchingFolder(new File(f__pfolder_ppPath), CCcount_Frequenc_y, po_pro_duc__t);
        }
        boolean w__q_wordFoun_d = false;
        int cc_coun_ttt = 0;
        for (String word : CCcount_Frequenc_y.keySet()) {
            if (word.equals(po_pro_duc__t)) {
                w__q_wordFoun_d = true;
                qwe_fileF_requency_Map = CCcount_Frequenc_y.get(word);
                for (String fileName : qwe_fileF_requency_Map.keySet()) {
                    int fr_eque__ncy = qwe_fileF_requency_Map.get(fileName);
                    cc_coun_ttt += fr_eque__ncy;
                }
            }
        }
        if (!w__q_wordFoun_d) {
            System.out.println("W Word n_not found>>.");
        }
        return cc_coun_ttt;
    }
}
