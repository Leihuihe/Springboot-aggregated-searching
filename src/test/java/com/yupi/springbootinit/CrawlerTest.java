package com.yupi.springbootinit;


import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.yupi.springbootinit.model.entity.Picture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CrawlerTest {

    @Test
    void testFetchPassage() {
        int current = 1;
        String url = "https://www.bing.com/images/search?q=toyota&first=" + current;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(".iuscp.isv");
            List<Picture> pictures = new ArrayList<>();
            for(Element element: elements) {
                String m = element.select(".iusc").get(0).attr("m");
                String title = element.select(".inflnk").get(0).attr("aria-label");
                Map<String, Object> map = JSONUtil.toBean(m, Map.class);
                String murl = (String) map.get("murl");
                Picture picture = new Picture();
                picture.setTitle(title);
                picture.setUrl(murl);
                pictures.add(picture);
                System.out.println(m);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        String json = "{}";
//        String url = "http://localhost:8101/api/post/list/page/vo";
//        String result2 = HttpRequest.post(url)
//                .body(json)
//                .execute().body();
//
//        System.out.println(result2);
//
//        Map<String, Object> map = JSONUtil.toBean(result2, Map.class);
    }
}
