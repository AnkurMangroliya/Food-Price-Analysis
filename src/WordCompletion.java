//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.*;
//
//public class WordCompletion {
//
//    private static final int MAX_DISTANCE = 2;
//    private static final List<String> FILENAMES = Arrays.asList("Saveonfoods.txt", "meijer.txt", "Freshthyme.txt");
//
//    private static int getLevenshteinDistance(String s, String t) {
//        if (s == null || t == null) {
//            throw new IllegalArgumentException("Strings must not be null");
//        }
//
//        int n = s.length();
//        int m = t.length();
//
//        if (n == 0) {
//            return m;
//        } else if (m == 0) {
//            return n;
//        }
//
//        int[] d = new int[m + 1];
//
//        for (int j = 0; j <= m; j++) {
//            d[j] = j;
//        }
//
//        for (int i = 1; i <= n; i++) {
//            int prev = i;
//            for (int j = 1; j <= m; j++) {
//                int temp = d[j - 1];
//                d[j - 1] = prev;
//                prev = Math.min(Math.min(prev + 1, d[j] + 1), temp + (s.charAt(i - 1) == t.charAt(j - 1) ? 0 : 1));
//            }
//            d[m] = prev;
//        }
//
//        // Use MAX_DISTANCE here
//        return d[m] <= MAX_DISTANCE ? d[m] : Integer.MAX_VALUE; // Threshold check
//    }
//
//
//    private static List<String> readTitlesFromFile(String filename) {
//        List<String> titles = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] lineWords = line.split("\\|")[0].split("\\s+");
//                if (lineWords.length >= 1) {
//                    String title = lineWords[0].trim();
//                    titles.add(title);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return titles;
//    }
//
//    public static List<String> findSimilarWords(String title) {
//        Set<String> titles = new HashSet<>();
//        for (String filename : FILENAMES) {
//            titles.addAll(readTitlesFromFile(filename));
//        }
//
//        List<String> similarTitles = new ArrayList<>();
//        for (String t : titles) {
//            if (t.toLowerCase().contains(title.toLowerCase())) {
//                similarTitles.add(t);
//            }
//        }
//
//        Collections.sort(similarTitles, Comparator.comparingInt(s -> getLevenshteinDistance(title.toLowerCase(), s.toLowerCase())));
//        return similarTitles.subList(0, Math.min(similarTitles.size(), 5));
//    }
//
//}


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordCompletion {

    private static final int MMAX_DISTANCCE = 2;
    private static final List<String> F_FILE_NAMEES = Arrays.asList("Saveonfoods.txt", "meijer.txt", "Freshthyme.txt");

    private static int getLevenshteinDistance(String s_st_strinng, String t_tt_strr) {
        if (s_st_strinng == null || t_tt_strr == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int n_nu = s_st_strinng.length();
        int m_mu = t_tt_strr.length();

        if (n_nu == 0) {
            return m_mu;
        } else if (m_mu == 0) {
            return n_nu;
        }

        int[] d = new int[m_mu + 1];

        for (int j_jn_jp = 0; j_jn_jp <= m_mu; j_jn_jp++) {
            d[j_jn_jp] = j_jn_jp;
        }

        for (int i_in_ip = 1; i_in_ip <= n_nu; i_in_ip++) {
            int prev = i_in_ip;
            for (int j_jn_jp = 1; j_jn_jp <= m_mu; j_jn_jp++) {
                int temp = d[j_jn_jp - 1];
                d[j_jn_jp - 1] = prev;
                prev = Math.min(Math.min(prev + 1, d[j_jn_jp] + 1), temp + (s_st_strinng.charAt(i_in_ip - 1) == t_tt_strr.charAt(j_jn_jp - 1) ? 0 : 1));
            }
            d[m_mu] = prev;
        }

        // Use MAX_DISTANCE here
        return d[m_mu] <= MMAX_DISTANCCE ? d[m_mu] : Integer.MAX_VALUE; // Threshold check
    }

    private static List<String> readTitlesFromFile(String ff_fna_me) {
        List<String> l_t_itles = new ArrayList<>();
        try (BufferedReader brty = new BufferedReader(new FileReader(ff_fna_me))) {
            String ll_lline;
            while ((ll_lline = brty.readLine()) != null) {
                String[] lineWords = ll_lline.split("\\|")[0].split("\\s+");
                if (lineWords.length >= 1) {
                    String title = lineWords[0].trim();
                    l_t_itles.add(title);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return l_t_itles;
    }

    public static List<String> findSimilarWords(String t_t_itle) {
        Set<String> t_itl_ees = new HashSet<>();
        for (String ff_fna_me : F_FILE_NAMEES) {
            t_itl_ees.addAll(readTitlesFromFile(ff_fna_me));
        }

        List<String> l_s_imilar_T_itles = new ArrayList<>();
        for (String tt_strr : t_itl_ees) {
            if (tt_strr.toLowerCase().contains(t_t_itle.toLowerCase())) {
                l_s_imilar_T_itles.add(tt_strr);
            }
        }

        Collections.sort(l_s_imilar_T_itles, Comparator.comparingInt(s -> getLevenshteinDistance(t_t_itle.toLowerCase(), s.toLowerCase())));
        return l_s_imilar_T_itles.subList(0, Math.min(l_s_imilar_T_itles.size(), 5));
    }
}
