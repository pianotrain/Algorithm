package IOStream;

import java.io.*;

/**
 * create by renshengmiao on 2018/3/29 .
 */
public class IOStreamTest {

    public static void main(String[] args) throws  Exception{
        test1();
        testFileStream();
        fileStreamTest2();
    }

    public static void test1(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String temp = null;
            while ((temp = reader.readLine()) != null && !temp.equals("end")){
                System.out.println(temp);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tsetByteArrayOutputStream() throws Exception{
        ByteArrayOutputStream bout = new ByteArrayOutputStream(12);
        while (bout.size() != 10){
            bout.write(System.in.read());
        }
        byte[] bytes = bout.toByteArray();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        byteArrayInputStream.close();
    }

    public static void testFileStream() {
        try {

            InputStream fileInputStream = new FileInputStream("F:\\rsm\\个人信息.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null){
                System.out.println(temp);
            }
            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
        }
    }

    public static void fileStreamTest2(){
        File file = new File("F:\\a.txt");
        FileOutputStream fop = null;
        OutputStreamWriter writer = null;
        try {
            fop = new FileOutputStream(file);
            writer = new OutputStreamWriter(fop,"UTF-8");
            writer.write("中文输入");
            writer.write("\r\n");
            writer.write("sdafasdf");
            writer.close();
            fop.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder sb = new StringBuilder();
            while ( inputStreamReader.ready() ){
                sb.append((char) inputStreamReader.read() );
            }
            System.out.println(sb.toString());
            fileInputStream.close();
            inputStreamReader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
