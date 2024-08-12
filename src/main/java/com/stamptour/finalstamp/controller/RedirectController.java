package com.stamptour.finalstamp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

@RestController
public class RedirectController {

    @GetMapping("/redirect")
    public String redirect(@RequestParam String url, @RequestParam(required = false) String token, @RequestParam(required = false) String stampedId) {
        // URL 인코딩
        String encodedUrl = UriUtils.encode(url, "UTF-8");
        String encodedToken = (token != null) ? UriUtils.encode(token, "UTF-8") : "";
        String encodedStampedId = (stampedId != null) ? UriUtils.encode(stampedId, "UTF-8") : "";

        // 크롬 스킴 URL 생성
        String chromeUrl = "googlechrome://" + encodedUrl;
        if (!encodedToken.isEmpty()) {
            chromeUrl += "?token=" + encodedToken;
        }
        if (!encodedStampedId.isEmpty()) {
            chromeUrl += (chromeUrl.contains("?") ? "&" : "?") + "stampedId=" + encodedStampedId;
        }

        // 리디렉션 HTML 페이지로 이동
        return "redirect:/choose-browser?url=" + UriUtils.encode(chromeUrl, "UTF-8");
    }
}
