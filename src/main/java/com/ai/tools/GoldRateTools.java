package com.ai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class GoldRateTools {

    @Tool(description = "Return today's gold rate")
    public String getTodaysGoldRate() {
        return "11535 INR per gram";
    }
}
