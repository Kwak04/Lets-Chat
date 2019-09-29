package com.example.soenapp;

public class SchoolData {
    String message;
    Result[] results;

    public class Result {
        String SCHUL_CODE;
        String SCHUL_RDNMA;
        String SCHUL_NM;
        String SCHUL_KND_SC_CODE;
        String HMPG_ADRES;
        String USER_TELNO;
    }

    @Override
    public String toString() {

        String info_string = "";

        if (message.equals("success")) {
            for (int i = 0 ; i<results.length; i++){
                info_string += ("SCHUL_CODE : " + results[i].SCHUL_CODE +
                        " SCHUL_RDNMA : " + results[i].SCHUL_RDNMA +
                        " SCHUL_NM : " + results[i].SCHUL_NM +
                        " SCHUL_KND_SC_CODE : " + results[i].SCHUL_KND_SC_CODE +
                        " HMPG_ADRES : " + results[i].HMPG_ADRES +
                        "USER_TELNO : " + results[i].USER_TELNO +
                        "\n-------------");
            }
        }
        return info_string;
    }
}
