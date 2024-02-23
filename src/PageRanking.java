import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageRanking {
    public static int appearNumber(String inputText, String srcText) {
        int count = 0;
        Pattern p = Pattern.compile(inputText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    public static String innerContent(String fileName, String folderName) throws IOException {
        File file = new File("../first/" + folderName + "/" + fileName);
        FileInputStream fIStream;
        StringBuilder stringBuilder = new StringBuilder();
        fIStream = new FileInputStream(file);
        int temp = 0;
        while ((temp = fIStream.read()) != -1) {
            stringBuilder.append((char) temp);
        }
        return stringBuilder.toString();
    }

    public ArrayList<String> fileNameList(String folderName) {
        ArrayList<String> fNameList = new ArrayList<String>();
        String path = "../first/" + folderName;  // Adjust the path based on your project structure
        File file = new File(path);

        if (!file.exists()) {
            System.out.println("Directory does not exist: " + path);
            // Handle this case (create the directory or provide a valid one)
            return fNameList;  // or throw an exception, etc.
        }

        File[] filelist = file.listFiles();

        if (filelist == null) {
            System.out.println("Error listing files in directory: " + path);
            // Handle this case (provide a default value, throw an exception, etc.)
            return fNameList;
        }

        for (File f : filelist) {
            fNameList.add(f.getName());
        }

        return fNameList;
    }

    public static void pageRank(String objWord) throws IOException {
        PageRanking pageRanking = new PageRanking();
        String[] folder = { "SaveonfoodsFiles", "freshDirectFiles", "meijerFiles" };

        HashMap<String, Integer> map = new HashMap<>();

        for (String folderName : folder) {
            ArrayList<String> fNameList = new ArrayList<>();
            fNameList.addAll(pageRanking.fileNameList(folderName));

            for (String fNL : fNameList) {
                String fileText = innerContent(fNL, folderName);
                int NUMS = appearNumber(objWord.toLowerCase(), fileText.toLowerCase());
                map.put(fNL, NUMS);
            }
        }

        PriorityQueue<HashMap.Entry<String, Integer>> hmPriorityQueue = new PriorityQueue<HashMap.Entry<String, Integer>>(
                new Comparator<HashMap.Entry<String, Integer>>() {
                    public int compare(HashMap.Entry<String, Integer> e1, HashMap.Entry<String, Integer> e2) {
                        return e2.getValue() - e1.getValue();
                    }
                });

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            hmPriorityQueue.add(entry);
        }

        for (int p = 0; p < 10; p++) {
            Entry<String, Integer> big = hmPriorityQueue.poll();
            if (big != null) {
                System.out.println("The file name is " + big.getKey() + ", value is " + big.getValue());
            } else {
                System.out.println("No more entries in the priority queue.");
            }
        }


        System.out.println("Finished.");
    }

    public static void main(String[] args) throws IOException {
        PageRanking pageRanking = new PageRanking();
        String[] folder = {"SaveOnFoodsHtml","ShopFoodExHtml","meijerFiles" };

        HashMap<String, Integer> map = new HashMap<>();

        System.out.print("Please enter your keywords: ");
        Scanner inputWordScanner = new Scanner(System.in);
        String objWord = inputWordScanner.next();

        for (String folderName : folder) {
            ArrayList<String> fNameList = new ArrayList<>();
            fNameList.addAll(pageRanking.fileNameList(folderName));

            for (String fNL : fNameList) {
                String fileText = innerContent(fNL, folderName);
                int NUMS = appearNumber(objWord.toLowerCase(), fileText.toLowerCase());
                map.put(fNL, NUMS);
            }
        }

        PriorityQueue<HashMap.Entry<String, Integer>> hmPriorityQueue = new PriorityQueue<HashMap.Entry<String, Integer>>(
                new Comparator<HashMap.Entry<String, Integer>>() {
                    public int compare(HashMap.Entry<String, Integer> e1, HashMap.Entry<String, Integer> e2) {
                        return e2.getValue() - e1.getValue();
                    }
                });

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            hmPriorityQueue.add(entry);
        }

        for (int p = 0; p < 10; p++) {
            Entry<String, Integer> big = hmPriorityQueue.poll();
            System.out.println("The file name is " + big.getKey() + ", value is " + big.getValue());
        }

        System.out.println("Finished.");
    }
}
