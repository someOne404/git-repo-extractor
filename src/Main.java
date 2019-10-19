import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        //Input
        String path = "path to the folder containing html files";

        String pattern ="href=\"(https://github.com/[0-0a-zA-Z\\-]+/[0-0a-zA-Z\\-]+)\">";
        Pattern p = Pattern.compile(pattern);

        List<File> files = new ArrayList<>(extract(path));
        for (File f : files){
            Scanner scnr = new Scanner(f);
            String str = "";
            List<String> result = new ArrayList<String>();

            BufferedWriter writer = new BufferedWriter(new FileWriter(f.getName().substring(0, f.getName().length()-5)+ ".txt"));
            while(scnr.hasNextLine()){
                String line = scnr.nextLine();
                str += line;
            }
            Matcher matcher = p.matcher(str);
            while (matcher.find()) {
                String link = matcher.group(1) + ".git";
                result.add(link);
            }
            for (String r : result){
                writer.write(r + "\n");
            }
            writer.close();
        }
    }

    public static List<File> extract(String path){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        List<File> files = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files.add(listOfFiles[i]);
//                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
//                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        return files;
    }
}
