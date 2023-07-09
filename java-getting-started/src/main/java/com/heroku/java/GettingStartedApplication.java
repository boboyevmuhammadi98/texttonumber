package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

@SpringBootApplication
@RestController
public class GettingStartedApplication {
    private static final String[] maxsusSonlar = {
            "", " минг", " миллион", " миллиард", " триллион", " квадриллион", " квинтиллион"
    };

    private static final String[] onlikSonlar = {
            "", " ўн", " йигирма", " ўттиз", " қирқ", " эллик", " олтмиш", " етмиш", " саксон", " тўқсон"
    };

    private static final String[] sonlar = {
            "", " бир", " икки", " уч", "тўрт", " беш", " олти", " етти", " саккиз", " тўққиз", " ўн", " ўн бир",
            " ўн икки", " ўн уч", " ўн тўрт", " ўн беш", " ўн олти", " ўн етти", " ўн саккиз", " ўн тўққиз"
    };

    private String sonlarniSozgaOgirish(long son) {
        String soz;

        if (son % 100 < 20) {
            soz = sonlar[(int) son % 100];
            son /= 100;
        } else {
            soz = sonlar[(int) son % 10];
            son /= 10;
            soz = onlikSonlar[(int) son % 10] + soz;
            son /= 10;
        }
        if (son == 0) {
            return soz;
        }
        return sonlar[(int) son] + " юз" + soz;
    }

    public String ogirish(long son) {

        String ishora = "", soz = "", s = "";
        if (son == 0) {
            return "нол";
        } else if (son < 0) {
            ishora = "минус";
        }

        long joy = 0, n;

        do {
            n = son % 1000;
            if (n != 0) {
                s = sonlarniSozgaOgirish(n);
                soz = s + maxsusSonlar[(int) joy] + soz;
            }
            joy++;
            son /= 1000;
        } while (son > 0);

        return (ishora + soz).trim();
    }


    @GetMapping("/{num}")
    public String index(@PathVariable int num) {
        return String.valueOf(ogirish(num));
    }



    public static void main(String[] args) {
        SpringApplication.run(GettingStartedApplication.class, args);
    }
}
