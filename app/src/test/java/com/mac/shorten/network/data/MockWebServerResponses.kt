package com.mac.shorten.network.data

object MockWebServerResponses {

    /**
     * 1 shortened link from api
     */
    const val linkResponse: String = "{\n" +
            "    \"ok\": true,\n" +
            "    \"result\": {\n" +
            "        \"code\": \"evdI2Q\",\n" +
            "        \"short_link\": \"shrtco.de/evdI2Q\",\n" +
            "        \"full_short_link\": \"https://shrtco.de/evdI2Q\",\n" +
            "        \"short_link2\": \"9qr.de/evdI2Q\",\n" +
            "        \"full_short_link2\": \"https://9qr.de/evdI2Q\",\n" +
            "        \"short_link3\": \"shiny.link/evdI2Q\",\n" +
            "        \"full_short_link3\": \"https://shiny.link/evdI2Q\",\n" +
            "        \"share_link\": \"shrtco.de/share/evdI2Q\",\n" +
            "        \"full_share_link\": \"https://shrtco.de/share/evdI2Q\",\n" +
            "        \"original_link\": \"http://helloworld.com\"\n" +
            "    }\n" +
            "}"
}