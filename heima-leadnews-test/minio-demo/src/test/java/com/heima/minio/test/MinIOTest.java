package com.heima.minio.test;

import com.heima.file.service.FileStorageService;
import com.heima.minio.MinIOApplication;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jnlp.FileSaveService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
@SpringBootTest(classes = MinIOApplication.class)
@RunWith(SpringRunner.class)
public class MinIOTest {
    @Autowired
    private FileStorageService fileStorageService;
    @Test
    public void test() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("/Users/meiting/Downloads/file-for-workspace/day2/list.html");

        String path = fileStorageService.uploadHtmlFile("","list.html", fileInputStream);
        System.out.println(path);
    }


    public static void main(String[] args) {
        try{
            FileInputStream fileInputStream = new FileInputStream("/Users/meiting/Downloads/file-for-workspace/day2/plugins/js/axios.min.js");

            //1. 获取minio的链接信息，创建一个minio的客户端
            MinioClient minioClient = MinioClient.builder().credentials("minio","minio123").endpoint("http://localhost:9000").build();


            // 2. 上传
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object("plugins/js/axios.min.js") //文件名称
                    .contentType("text/js") //文件类型
                    .bucket("leadnews") //桶名称，与minio管理界面 与创建的桶//一致即可
                    .stream(fileInputStream, fileInputStream.available(), -1).build();
            minioClient.putObject(putObjectArgs);

            //访问路径
//            System.out.println("http://localhost:9000/leadnews/list.html");
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
