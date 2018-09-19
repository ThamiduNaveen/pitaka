package com.pitaka.app.pitaka;

import android.os.Environment;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class jsonStringCreate {

    String listSutra = "විනයපිටක\n" +
            "සුත්තපිටක\n" +
            "\tදීඝනිකාය\n" +
            "\t\tසීලක්ඛන්ධවග්ගපාළි\n" +
            "\t\t\t1. බ්\u200Dරහ්මජාලසුත්තං\n" +
            "\t\t\t2. සාමඤ්ඤඵලසුත්තං\n" +
            "\t\t\t3. අම්බට්ඨසුත්තං\n" +
            "\t\t\t4. සොණදණ්ඩසුත්තං\n" +
            "\t\t\t5. කූටදන්තසුත්තං\n" +
            "\t\t\t6. මහාලිසුත්තං\n" +
            "\t\t\t7. ජාලියසුත්තං\n" +
            "\t\t\t8. මහාසීහනාදසුත්තං\n" +
            "\t\t\t9. පොට්ඨපාදසුත්තං\n" +
            "\t\t\t10. සුභසුත්තං\n" +
            "\t\t\t11. කෙවට්ටසුත්තං\n" +
            "\t\t\t12. ලොහිච්චසුත්තං\n" +
            "\t\t\t13. තෙවිජ්ජසුත්තං\n" +
            "\t\tමහාවග්ගපාළි\n" +
            "\t\t\t1. මහාපදානසුත්තං\n" +
            "\t\t\t2. මහානිදානසුත්තං\n" +
            "\t\t\t3. මහාපරිනිබ්බානසුත්තං\n" +
            "\t\t\t4. මහාසුදස්සනසුත්තං\n" +
            "\t\t\t5. ජනවසභසුත්තං\n" +
            "\t\t\t6. මහාගොවින්දසුත්තං\n" +
            "\t\t\t7. මහාසමයසුත්තං\n" +
            "\t\t\t8. සක්කපඤ්හසුත්තං\n" +
            "\t\t\t9. මහාසතිපට්ඨානසුත්තං\n" +
            "\t\t\t10. පායාසිසුත්තං\n" +
            "\t\tපාථිකවග්ගපාළි\n" +
            "\t\t\t1. පාථිකසුත්තං\n" +
            "\t\t\t2. උදුම්බරිකසුත්තං\n" +
            "\t\t\t3. චක්කවත්තිසුත්තං\n" +
            "\t\t\t4. අග්ගඤ්ඤසුත්තං\n" +
            "\t\t\t5. සම්පසාදනීයසුත්තං\n" +
            "\t\t\t6. පාසාදිකසුත්තං\n" +
            "\t\t\t7. ලක්ඛණසුත්තං\n" +
            "\t\t\t8. සිඞ්ගාලසුත්තං\n" +
            "\t\t\t9. ආටානාටියසුත්තං\n" +
            "\t\t\t10. සඞ්ගීතිසුත්තං\n" +
            "\t\t\t11. දසුත්තරසුත්තං\n" +
            "\tමජ්ඣිමනිකාය\n" +
            "\t\tමූලපණ්ණාසපාළි\n" +
            "\t\t\t1. මූලපරියායවග්ගො\n" +
            "\t\t\t2. සීහනාදවග්ගො\n" +
            "\t\t\t3. ඔපම්මවග්ගො\n" +
            "\t\t\t4. මහායමකවග්ගො\n" +
            "\t\t\t5. චූළයමකවග්ගො\n" +
            "\t\tමජ්ඣිමපණ්ණාසපාළි\n" +
            "\t\t\t1. ගහපතිවග්ගො\n" +
            "\t\t\t2. භික්ඛුවග්ගො\n" +
            "\t\t\t3. පරිබ්බාජකවග්ගො\n" +
            "\t\t\t4. රාජවග්ගො\n" +
            "\t\t\t5. බ්\u200Dරාහ්මණවග්ගො\n" +
            "\t\tඋපරිපණ්ණාසපාළි\n" +
            "\t\t\t1. දෙවදහවග්ගො\n" +
            "\t\t\t2. අනුපදවග්ගො\n" +
            "\t\t\t3. සුඤ්ඤතවග්ගො\n" +
            "\t\t\t4. විභඞ්ගවග්ගො\n" +
            "\t\t\t5. සළායතනවග්ගො\n" +
            "\tසංයුත්තනිකාය\n" +
            "\t\tසගාථාවග්ගපාළි\n" +
            "\t\tනිදානවග්ගපාළි\n" +
            "\t\tඛන්ධවග්ගපාළි\n" +
            "\t\tසළායතනවග්ගපාළි\n" +
            "\t\tමහාවග්ගපාළි\n" +
            "\tඅඞ්ගුත්තරනිකාය\n" +
            "\t\tඑකකනිපාතපාළි\n" +
            "\t\tදුකනිපාතපාළි\n" +
            "\t\tතිකනිපාතපාළි\n" +
            "\t\tචතුක්කනිපාතපාළි\n" +
            "\t\tපඤ්චකනිපාතපාළි\n" +
            "\t\tඡක්කනිපාතපාළි\n" +
            "\t\tසත්තකනිපාතපාළි\n" +
            "\t\tඅට්ඨකාදිනිපාතපාළි\n" +
            "\t\tනවකනිපාතපාළි\n" +
            "\t\tදසකනිපාතපාළි\n" +
            "\t\tඑකාදසකනිපාතපාළි\n" +
            "\tඛුද්දකනිකාය\n" +
            "\t\tඛුද්දකපාඨපාළි\n" +
            "\t\tධම්මපදපාළි\n" +
            "\t\tඋදානපාළි\n" +
            "\t\tඉතිවුත්තකපාළි\n" +
            "\t\tසුත්තනිපාතපාළි\n" +
            "\t\tවිමානවත්ථුපාළි\n" +
            "\t\tපෙතවත්ථුපාළි\n" +
            "\t\tථෙරගාථාපාළි\n" +
            "\t\tථෙරීගාථාපාළි\n" +
            "\t\tඅපදානපාළි-1\n" +
            "\t\tඅපදානපාළි-2\n" +
            "\t\tබුද්ධවංසපාළි\n" +
            "\t\tචරියාපිටකපාළි\n" +
            "\t\tජාතකපාළි-1\n" +
            "\t\tජාතකපාළි-2\n" +
            "\t\tමහානිද්දෙසපාළි\n" +
            "\t\tචූළනිද්දෙසපාළි\n" +
            "\t\tපටිසම්භිදාමග්ගපාළි\n" +
            "\t\tනෙත්තිප්පකරණපාළි\n" +
            "\t\tමිලින්දපඤ්හපාළි\n" +
            "\t\tපෙටකොපදෙසපාළි\n" +
            "අභිධම්මපිටක\n";

    String sub1;
    String sub2;
    int pos;

    public String generateJson() {

        String outStr = "[";
        String[] sutraArray = listSutra.split("\n");

        for (int i = 0; i < sutraArray.length; i++) {
            String line = sutraArray[i];
            int noOfTabs = StringUtils.countMatches(line, "\t");
            if (noOfTabs == 0) {
                outStr += "{\\\"title\\\":\\\"";
                line = line.replaceAll("\t", "");
                outStr += line;
                outStr += "\\\",\\\"children\\\":[]},";
            }
        }

        outStr = outStr.substring(0, outStr.length() - 1);
        outStr += "]";
        String tempStr = "start";

        for (int i = 0; i < sutraArray.length; i++) {
            String line = sutraArray[i];
            int noOfTabs = StringUtils.countMatches(line, "\t");
            if (noOfTabs == 1) {
                if (tempStr.equals("1")) {
                    tempStr = "";
                }
                tempStr += "{\\\"title\\\":\\\"";
                line = line.replaceAll("\t", "");
                tempStr += line;
                tempStr += "\\\",\\\"children\\\":[1]},";
            }
            if (noOfTabs == 0) {
                if (tempStr.equals("start")) {
                    tempStr = "";
                } else if (tempStr.equals("1")) {
                    tempStr = "*";
                } else {
                    tempStr = tempStr.substring(0, tempStr.length() - 1);
                }

                pos = outStr.indexOf("[]");
                sub1 = outStr.substring(0, pos + 2);
                sub2 = outStr.substring(pos + 2);
                sub1 = sub1.replace("[]", "[" + tempStr + "]");
                outStr = sub1 + sub2;
                tempStr = "1";
            }
        }
        if (tempStr.equals("start")) {
            tempStr = "";
        } else if (tempStr.equals("1")) {
            tempStr = "*";
        } else {
            tempStr = tempStr.substring(0, tempStr.length() - 1);
        }

        int pos = outStr.indexOf("[]");
        sub1 = outStr.substring(0, pos + 2);
        sub2 = outStr.substring(pos + 2);
        sub1 = sub1.replace("[]", "[" + tempStr + "]");
        outStr = sub1 + sub2;
        tempStr = "start";


        for (int i = 0; i < sutraArray.length; i++) {
            String line = sutraArray[i];
            int noOfTabs = StringUtils.countMatches(line, "\t");
            if (noOfTabs == 2) {
                if (tempStr.equals("2")) {
                    tempStr = "";
                }
                tempStr += "{\\\"title\\\":\\\"";
                line = line.replaceAll("\t", "");
                tempStr += line;
                tempStr += "\\\",\\\"children\\\":[2]},";
            }
            if (noOfTabs == 1) {
                if (tempStr.equals("start")) {
                    tempStr = "1";
                } else if (tempStr.equals("2")) {
                    tempStr = "*";
                } else {
                    tempStr = tempStr.substring(0, tempStr.length() - 1);
                }

                pos = outStr.indexOf("[1]");
                sub1 = outStr.substring(0, pos + 3);
                sub2 = outStr.substring(pos + 3);
                sub1 = sub1.replace("[1]", "[" + tempStr + "]");
                outStr = sub1 + sub2;
                tempStr = "2";
            }
        }
        if (tempStr.equals("start")) {
            tempStr = "";
        } else if (tempStr.equals("2")) {
            tempStr = "*";
        } else {
            tempStr = tempStr.substring(0, tempStr.length() - 1);
        }

        pos = outStr.indexOf("[1]");
        sub1 = outStr.substring(0, pos + 3);
        sub2 = outStr.substring(pos + 3);
        sub1 = sub1.replace("[1]", "[" + tempStr + "]");
        outStr = sub1 + sub2;
        tempStr = "start";
//////////////////

        for (int i = 0; i < sutraArray.length; i++) {
            String line = sutraArray[i];
            int noOfTabs = StringUtils.countMatches(line, "\t");
            if (noOfTabs == 3) {
                if (tempStr.equals("*")) {
                    tempStr = "";
                }
                tempStr += "{\\\"title\\\":\\\"";
                line = line.replaceAll("\t", "");
                tempStr += line;
                tempStr += "\\\",\\\"children\\\":[*]},";
            }
            if (noOfTabs == 2) {
                if (tempStr.equals("start")) {
                    tempStr = "2";
                } else if (tempStr.equals("*")) {
                    tempStr = "*";
                } else {
                    tempStr = tempStr.substring(0, tempStr.length() - 1);
                }

                pos = outStr.indexOf("[2]");
                sub1 = outStr.substring(0, pos + 3);
                sub2 = outStr.substring(pos + 3);
                sub1 = sub1.replace("[2]", "[" + tempStr + "]");
                outStr = sub1 + sub2;
                tempStr = "*";
            }
        }
        if (tempStr.equals("start")) {
            tempStr = "2";
        } else if (tempStr.equals("*")) {
            tempStr = "*";
        } else {
            tempStr = tempStr.substring(0, tempStr.length() - 1);
        }

        pos = outStr.indexOf("[2]");
        sub1 = outStr.substring(0, pos + 3);
        sub2 = outStr.substring(pos + 3);
        sub1 = sub1.replace("[2]", "[" + tempStr + "]");
        outStr = sub1 + sub2;
        tempStr = "start";
        outStr = outStr.replace("*", "");

        return outStr;
    }

    public void write() {
        File sdCard = Environment.getExternalStorageDirectory();
        File f = new File(sdCard, "JsonString.txt");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            String data = generateJson();
            fos.write(data.getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
