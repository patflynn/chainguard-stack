package com.google.cloud.paflynn.samples.recaptcha.guestbook.restservice;

import com.google.cloud.paflynn.samples.recaptcha.guestbook.resouces.Greeting;
import com.google.cloud.paflynn.samples.recaptcha.guestbook.resouces.GreetingRepository;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class GreetingController {

    private final GreetingRepository greetingRepository;
    private final OkHttpClient httpClient = new OkHttpClient();
    private Logger logger = Logger.getLogger(GreetingController.class.getName());

    @Autowired
    public GreetingController(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @GetMapping("/greetings")
    public Page<Greeting> listGreetings(Pageable page) {
        return greetingRepository.findAll(page);
    }

    @RequestMapping(value = "/greetings", method = RequestMethod.POST, consumes = "application/json")
    public Greeting greeting(@RequestBody String postPayload) {
        JacksonJsonParser parser = new JacksonJsonParser();
        Map<String, Object> values = parser.parseMap(postPayload);
        String captchaToken = (String) values.get("token");
        FormBody formBody = new FormBody.Builder()
                .add("secret", "token here")
                .add("response", captchaToken)
                .build();
        Request request = new Request.Builder()
                .url("https://www.google.com/recaptcha/api/siteverify")
                .post(formBody)
                .build();
        try {
            Response recaptchaResponse = httpClient.newCall(request).execute();
            if (recaptchaResponse.isSuccessful()) {
                Map<String, Object> captchaResponse = parser.parseMap(recaptchaResponse.body().string());
                boolean isHuman = (boolean) captchaResponse.get("success");
                logger.info("recaptcha verdict: " + (isHuman ? "human after all" : "robot"));
                if (!isHuman) {
                    throw new RuntimeException("we're onto you!");
                }
            } else {
                throw new RuntimeException("damn something went wrong with recaptcha");
            }
        } catch (IOException e) {
            throw new RuntimeException("this can't be good", e);
        }

        greetingRepository.save(new Greeting((String) values.get("username"), (String) values.get("message")));
        logger.info("user has been saved: " + values.toString());
        return null;
    }
}