//package utilities;
//
//import net.masterthought.cucumber.Configuration;
//import net.masterthought.cucumber.ReportBuilder;
//import net.masterthought.cucumber.json.support.Status;
//import stepdefinitions.StepDefinitions;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
//public class Reporting {
//    public static String mailBody = "";
//
//    public void configureReport() {
//        try {
//            File reportOutputDirectory = new File(PathAndVariable.report_Name);
//            List<String> jsonFiles = new ArrayList<>();
//            jsonFiles.add("target/cucumber.json");
//            String buildNumber = "1";
//            String projectName = "Flex E2E";
//            Configuration configuration = new Configuration(reportOutputDirectory, projectName);
//            configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
//            configuration.setBuildNumber(buildNumber);
//            configuration.addClassifications("Platform", System.getProperty("os.name").toUpperCase());
//            configuration.addClassifications("Browser", "Chrome");
//            configuration.addClassifications("Branch", "release/1.0");
//            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
//            reportBuilder.generateReports();
//            StepDefinitions.LOGGER.info("****************Reports generated*****************");
//            ZipHelper.zipDir(PathAndVariable.report_Name + "/cucumber-html-reports", PathAndVariable.report_Name + "/" + PathAndVariable.tags.split("@")[1] + ".zip");
//        } catch (Exception e) {
//            e.printStackTrace();
//            StepDefinitions.LOGGER.error("Exception occurs : " + e);
//        }
//    }
//
//    static class ZipHelper {
//        public static void zipDir(String dirName, String nameZipFile) {
//            try {
//                ZipOutputStream zip;
//                FileOutputStream fW;
//                fW = new FileOutputStream(nameZipFile);
//                zip = new ZipOutputStream(fW);
//                addFolderToZip("", dirName, zip);
//                StepDefinitions.LOGGER.info("Zip folder created at directory: " + dirName);
//                zip.close();
//                fW.close();
//            } catch (Exception exception) {
//                exception.printStackTrace();
//                StepDefinitions.LOGGER.error("Exception occurs while zipping file:" + exception);
//            }
//        }
//
//        private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) {
//            try {
//                File folder = new File(srcFolder);
//                if (Objects.requireNonNull(folder.list()).length == 0) {
//                    addFileToZip(path, srcFolder, zip, true);
//                } else {
//                    for (String fileName : Objects.requireNonNull(folder.list())) {
//                        if (path.equals("")) {
//                            addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
//                        } else {
//                            addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                StepDefinitions.LOGGER.error("Exception occurs : " + e);
//            }
//        }
//
//        private static void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag) {
//            try {
//                File folder = new File(srcFile);
//                if (flag) {
//                    zip.putNextEntry(new ZipEntry(path + "/" + folder.getName() + "/"));
//                } else {
//                    if (folder.isDirectory()) {
//                        addFolderToZip(path, srcFile, zip);
//                    } else {
//                        byte[] buf = new byte[1024];
//                        int len;
//                        FileInputStream in = new FileInputStream(srcFile);
//                        zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
//                        while ((len = in.read(buf)) > 0) {
//                            zip.write(buf, 0, len);
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                StepDefinitions.LOGGER.error("Exception occurs : " + e);
//            }
//        }
//    }
//
//    public void updateResult(String classname, int indexSI, String methodName, String response, String error) {
//        methodName = methodName.toUpperCase();
//        String startDate = new SimpleDateFormat("MM-dd-yyyy").format(new GregorianCalendar().getTime());
//        String[] arr = PathAndVariable.env.split("_");
////        File file = new File(PathAndVariable.report_Name + "/" + PathAndVariable.tags.split("@")[1] + ".html");
//        try {
//            if (!file.exists()) {
//                BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
//                bw.write("<html>" + "\n");
//                bw.write("<head><title>" + "Test execution report" + "</title>" + "\n");
//                bw.write("</head>" + "\n");
//                bw.write("<body>");
//                bw.write("<u><h1 align='center'>" + "Test execution Report" + "</h1></u>" + "\n");
//                bw.write("<table align=center style='border-width:1px; border-color:#000000;' style='width: auto;'cellpadding='7' cellspacing='0'>" + "\n");
//                bw.write("<thead style='border-style: solid; border-width:1px; border-color:#000000;'bgcolor=#AED1FA align = center>");
//                bw.write("<td align=left valign=\"bottom\" style='border-style: solid; border-width:1px;border-color:#AED1FA;'><p><h4>Application</h4></p></td>");
//                bw.write("<td align=center style='border-style: solid; border-width:0px; border-color:#AED1FA;'><h4> Environment </h4></td>");
//                bw.write("<td align=center style='border-style: solid; border-width:1px; border-color:#AED1FA;' ><h4> Execution Date </h4></td>");
//                bw.write("</thead>");
//                bw.write("<tr style='border-style: solid; border-width:1px; border-color:#003366;'>");
//                bw.write("<td align=\"center\" style=\"border-style: solid; border-width:1px; border-color:#AED1FA;\"bgcolor= '#58D68D'><p><b>" + arr[0] + "</b></p></td>");
//                bw.write("<td align=\"center\" style='border-style: solid; border-width:1px; border-color:#AED1FA;'><p>" + arr[1] + "</p></td>");
//                bw.write("<td align=\"center\" style='border-style: solid; border-width:1px; border-color:#AED1FA;'><p>" + startDate + "</p></td>");
//                bw.write("</tr>");
//                bw.write("</table>");
//                bw.write("<br><br>");
//                bw.write("<table align=center style='border-width:1px; border-color:#000000;' style='width: auto;'cellpadding='7' cellspacing='0'>");
//                bw.write("<thead style='border-style: solid; border-width:1px; border-color:#000000;'bgcolor=#AED1FA align = center>");
//                bw.flush();
//                bw.close();
//            }
//            BufferedWriter bw1 = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
//            if (indexSI == 1) {
//                bw1.write("<tr>" + "\n");
//                bw1.write("<td bgcolor='#AED1FA' align='center'<b><font color='#000000' face='Tahima' size='3'>S.No</font></b></td>");
//                bw1.write("<td bgcolor='#AED1FA' align='center'<b><font color='#000000' face='Tahima' size='3'>Feature Name</font></b></td>");
//                bw1.write("<td bgcolor='#AED1FA' align='center'<b><font color='#000000' face='Tahima' size='3'>Scenario Name</font></b></td>");
//                bw1.write("<td bgcolor='#AED1FA' align='center'<b><font color='#000000' face='Tahima' size='3'>Result</font></b></td>");
//                bw1.write("<td bgcolor='#AED1FA' align='center'<b><font color='#000000' face='Tahima' size='3'>Error</font></b></td>");
//                bw1.write("<td bgcolor='#AED1FA' align='center'<b><font color='#000000' face='Tahima' size='3'>Execution time</font></b></td>");
//                bw1.write("</tr>");
//                bw1.write("</thead>");
//            }
//            if (response.equalsIgnoreCase("Fail")) {
//                bw1.write("<tr style='border-style: solid; border-width:1px; border-color:#003366;'>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + indexSI + "</font></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + methodName + "</font></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + classname + "</font></td>");
//                bw1.write("<td bgcolor='#c00' align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + response + "</p></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + error + "</font></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + PathAndVariable.time_difference + "</font></td>");
//                bw1.write("</tr>");
//            } else if (response.equalsIgnoreCase("Skip")) {
//                bw1.write("<tr style='border-style: solid; border-width:1px; border-color:#003366;'>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + indexSI + "</font></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + methodName + "</font></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + classname + "</font></td>");
//                bw1.write("<td bgcolor='#ffff4d' align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + response + "</p></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + error + "</font></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + PathAndVariable.time_difference + "</font></td>");
//                bw1.write("</tr>");
//            } else {
//                bw1.write("<tr style='border-style: solid; border-width:1px; border-color:#003366;'>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + indexSI + "</font></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + methodName + "</font></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + classname + "</font></td>");
//                bw1.write("<td bgcolor='#58D68D' align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + response + "</p></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + error + "</font></td>");
//                bw1.write("<td align=left style='border-style: solid; border-width:1px; border-color:#AED1FA;'><font color='#000000' face='Tahima' size='2'>" + PathAndVariable.time_difference + "</font></td>");
//                bw1.write("</tr>");
//            }
////            bw1.write("</table>");
////            bw1.write("</body>");
////            bw1.write("</html>");
//            bw1.flush();
//            bw1.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            StepDefinitions.LOGGER.error("Exception occurs : " + e);
//        }
//        StringBuilder content = new StringBuilder();
//        try {
//            BufferedReader input = new BufferedReader(new FileReader(file.getAbsoluteFile()));
//            String str;
//            while ((str = input.readLine()) != null) {
//                content.append(str);
//            }
//            input.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            StepDefinitions.LOGGER.error("Exception occurs : " + e);
//        }
//        mailBody = content.toString();
//    }
//}