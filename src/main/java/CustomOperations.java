import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aokly on 29.09.2016.
 */
public class CustomOperations {

    public static String reverseName(String s){
        if (!s.contains(" "))
            return s;
        String [] sArr = s.split(" ");
        return sArr[1]+" "+sArr[0];
    }
    public static void copyQuestions() throws Exception {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            //webSelenium.copyQuestion("558,1");
            String arr[] = {
                    "1760,33",
                    "1760,33",
            };
            String arr2[] = {
                    "111,1",
                    "22,1"};


            for (String a : arr) {
                System.out.println(a);
                webSelenium.copyQuestion(a, s -> true);
                Thread.sleep(100);
               // webSelenium.moveCopiedQuestions(a, "4,1", (n) -> n % 2 == 0);
                //Thread.sleep(100);
            }
            /*
            for (String a : arr2) {
                webSelenium.copyQuestion(a, s -> true);
                Thread.sleep(100);
                webSelenium.moveCopiedQuestions(a, "1498,33", (n) -> n % 2 == 0);
                Thread.sleep(100);
            }*/

        }
    }

    public static void renameTests() throws Exception {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(2000);
            String arr[][] = {
                    {"1515,33", "10_Рекурсия"},
                    {"1526,33", "10_Рекурсия_ДЗ"}
            };
            for (String[] a : arr) {
                webSelenium.renameTests(a[1], a[0]);
            }

        }
    }

    public ArrayList<ArrayList<String>> readAnichkovCSV() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/source/AnichkovForm.csv")))) {
            String commandstring;
            ArrayList<ArrayList<String>> sList = new ArrayList<>();
            int cnt = 0;
            while ((commandstring = bufferedReader.readLine()) != null) {
                String ls_regex = "\".*?\"";
                Pattern pattern = Pattern.compile(ls_regex);
                Matcher matcher = pattern.matcher(commandstring);
                ArrayList<String> sl = new ArrayList<>();

                int i = 0;

                boolean flgAdd = false;
                while (matcher.find()) {
                    String tmp = matcher.group();
                    //System.out.println(tmp);
                    sl.add(tmp.replace("\"", ""));
                    i++;
                }
                cnt++;

                sList.add(sl);
            }
            return sList;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fuck");
        }
        System.out.println("eagasg");
        return null;
    }

    public static void addQuestionsToTest(){
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            String sArr[][] = {
                   // {"1515,33", "http://mdl.sch239.net/mod/quiz/view.php?id=1548"},
                    {"1526,33", "http://mdl.sch239.net/mod/quiz/view.php?id=1550"},
            };
            for (String [] sA:sArr) {
                webSelenium.addQuestionsToTest(sA[1], sA[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void addRefs() {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            String sArr[][] = {
                    {"1515,33", "10_Рекурсия"},
                    {"1526,33", "10_Рекурсия_ДЗ"},
                    {"1604,33", "11_Рекурсия_2"},
                    {"1605,33", "11_Рекурсия_2_ДЗ"},
                    {"1596,33", "12_Двумерные массивы"},
                    {"1597,33", "12_Двумерные массивы_ДЗ"},
            };

            for (String sa[] : sArr) {
                webSelenium.addRef(sa[0],sa[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fillRandom() {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            String sArr[] = {
                    "1510,33",
                    "1521,33",
                    "1511,33",
                    "1522,33",
                    "1512,33",
                    "1523,33",
                    "1513,33",
                    "1524,33"
            };

            for (String s : sArr) {
                webSelenium.fillRandom("1129,33", s, 13);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTemplateToQuestions() throws Exception {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            String template = "import java.util.*;\n" +
                    "\n" +
                    "public class A{\n" +
                    "\n" +
                    " public static void main(String [] args){\n" +
                    " {{ TEST.testcode }}\n" +
                    " {{ STUDENT_ANSWER }}\n" +
                    " for (int i=0;i<arr.length;i++){\n" +
                    "  for (int j = 0; j < arr[i].length; j++) {\n" +
                    "    System.out.print(arr[i][j]+\" \");\n" +
                    "  \n" +
                    "  System.out.println();\n" +
                    " " +
                    " \n";
            String [] arr = {
                   // "1596,33",
                    "1597,33"
            };
            for (String sa:arr)
                webSelenium.setTemplateToQuestions(sa, template, (s) -> true);

        }
    }

    public void fillAnichkov() {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToAnichkov();

            ArrayList<ArrayList<String>> lst = readAnichkovCSV();
            lst.sort((o1, o2) -> o1.get(1).compareTo(o2.get(1)));
            //System.out.println( lst);
            String prevGroup = "0";
            if (lst != null) {
                //System.out.println(cnt);
                if (lst.size() > 0) {
                    int size1 = lst.size();
                    //String sArr[][] = new String[size1][size2];
                    for (ArrayList<String> l : lst) {
                        //sArr[i] = new String[size2];
                        if (!l.get(1).equals(prevGroup)) {
                            prevGroup = l.get(1);
                            webSelenium.goToSamsungPage(prevGroup);
                        }
                        l.remove(1);

                        String sArr[] = l.get(6).split("-");
                        l.set(6, sArr[2] + "." + sArr[1] + "." + sArr[0]);
                        for (int i = 0; i < l.size(); i++) {
                            if (l.get(i).replace(" ", "").equals("")) {
                                l.set(i, "-");
                            }
                        }
                        webSelenium.fillAnichkov(l);
                    }
                } else {
                    System.out.println("Пустой лист");
                }
            }

            System.out.println("Прочитано");

            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


