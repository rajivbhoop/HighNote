package com.highnote.solution;

import java.time.LocalDate;

public class CardTransactionHelper {

    public static String populateResponseStr(TransactionDetails response) {
        System.out.println(response.toString());
        StringBuilder builder = new StringBuilder();
        builder.append(response.getAuthCode());

        builder.append(response.getBitMap());

        if(response.getPan() != null) {
            builder.append(response.getPan().length());
            builder.append(response.getPan());
        }
        if(response.getExp() != null) builder.append(response.getExp());
        if(response.getOriginalString() != null ){
            builder.append(response.getOriginalString().substring(10));
        }
        if(response.getAmount() != null) {
            String amount = Double.toString(response.getAmount()).replace(".","0");
            int padLength = 10 - amount.length();
            for(int i = 0; i < padLength; i++)
                builder.append("0");
            builder.append(amount);
        }
        builder.append(response.getResponseCode());
        if(response.getCardHolderName() != null) {
            builder.append(response.getCardHolderName().length());
            builder.append(response.getCardHolderName());
        }
        if(response.getZip() != null) builder.append(response.getZip());


        return builder.toString();
    }

    public static TransactionDetails generateResponse(TransactionDetails reqDetails) {
        TransactionDetails response = new TransactionDetails();
        boolean isValid = validateTx(reqDetails);
        String responseCode = "OK";
        if (!isValid) responseCode = "ER";
        response.setResponseCode(responseCode);
        boolean authorized = authorize(reqDetails);
        if(!authorized) response.setResponseCode("DE");
        // System.out.println(response.getResponseCode());
        StringBuilder responseBitMapBin = new StringBuilder(reqDetails.getBinBitMap());
        if(!response.getResponseCode().equalsIgnoreCase("ER"))
            responseBitMapBin = responseBitMapBin.replace(3,4,"1");
        else{
            responseBitMapBin = responseBitMapBin.replace(2,3,"1");
        }
        String responseBitMap =  convertBinToHex(responseBitMapBin.toString());
        response.setBinBitMap(responseBitMapBin.toString());
        response.setBitMap(responseBitMap);
        response.setAuthCode("0110");
        response.setZip(reqDetails.getZip());
        response.setCardHolderName(reqDetails.getCardHolderName());
        response.setExp(reqDetails.getExp());
        response.setAmount(reqDetails.getAmount());
        response.setPan(reqDetails.getPan());
        System.out.println(response.toString());
        response.setOriginalString(reqDetails.getOriginalString());
        return response;
    }

    private static String convertBinToHex(String responseBitMapBin) {
        int decimal = Integer.parseInt(responseBitMapBin,2);
        return Integer.toString(decimal,16);
    }

    //public static void main(String args[]){
        //System.out.println(hexToBin("6"));
    //}

    private static boolean authorize(TransactionDetails reqDetails) {
        //check zip code present
        if(reqDetails.getZip() != null && reqDetails.getAmount() > 200) return false;
        if(reqDetails.getZip() == null && reqDetails.getAmount() != null && reqDetails.getAmount() > 100) return false;
        if(reqDetails.getExp() == null) return false;
        Integer month = Integer.parseInt(reqDetails.getExp().substring(0,2));
        Integer year = Integer.parseInt(reqDetails.getExp().substring(2,4));
        LocalDate date = LocalDate.now();
        if(date.getMonthValue() > month) return false;
        if(date.getYear() % 2000 > year) return false;
        return true;
    }

    private static boolean validateTx(TransactionDetails reqDetails) {
        // PAN , exp date,
        if(reqDetails.getPan() == null || reqDetails.getExp() == null) return false;
        return true;
    }

    public static TransactionDetails parseTx(StringBuilder str){

        // Auth code (0-3 index)
        TransactionDetails reqDetails = new TransactionDetails();
        reqDetails.setAuthCode(str.substring(0,4));
        //System.out.println(reqDetails.getAuthCode());
        // bit map (index 4-5)
        String bitMap = str.substring(4,6);
        String binBitMap = hexToBin(bitMap).toString();
        reqDetails.setBinBitMap(binBitMap);
        reqDetails.setBitMap(bitMap);
        parseBitMapAndPopulateDetails(binBitMap,str,reqDetails);
        //System.out.println(binBitMap);
        //


        return reqDetails;
    }

    private static void parseBitMapAndPopulateDetails(String binBitMap, StringBuilder str, TransactionDetails details) {
//        if(str.length() < 36) {
//            //details = null;
//            return; // PAN missing
//        }
        int curPos = 6;
        // PAN
        if(binBitMap.charAt(0) == '1' && str.length() >= 36){
            // PAN present
            // 5-6 for PAN length
            int panLength = Integer.parseInt(str.substring(6,8));
            curPos +=2;
            details.setPan(str.substring(8,8+panLength+1));
            curPos+=panLength;
            //System.out.println(details.getPan());
        }else{
            details.setOriginalString(str.toString());
        }
        if(binBitMap.charAt(1) == '1'){
            // exp date present
            details.setExp(str.substring(curPos,curPos + 4));
            curPos+=4;
            //System.out.println(details.getExp());
        }
        if(binBitMap.charAt(2) == '1'){
            // transaction amount
            details.setAmount(Double.parseDouble(str.substring(curPos,curPos+10))/100);
            curPos+=10;
            //System.out.println(details.getAmount());
        }
        if(binBitMap.charAt(4) == '1'){
            // cardholder name
            int nameLength = Integer.parseInt(str.substring(curPos,curPos+2));
            curPos+=2;
            details.setCardHolderName(str.substring(curPos,curPos+nameLength));
            curPos+=nameLength;
            //System.out.println(details.getCardHolderName());
        }
        if(binBitMap.charAt(5) == '1'){
            // zip code present
            details.setZip(str.substring(curPos,curPos+5));
            //System.out.println(details.getZip());
        }
        System.out.println(details.toString());
    }

    public static StringBuilder hexToBin(String s)
    {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }

        int dec_num = val, i=1, j;
        int bin_num[] = new int[10];
        /* convert decimal to binary */
        while(dec_num != 0)
        {
            bin_num[i++] = dec_num%2;
            dec_num = dec_num/2;
        }

        StringBuilder result = new StringBuilder();

        for(j=i-1; j>0; j--)
        {
            //System.out.print(bin_num[j]);
            result.append(bin_num[j]);
        }
        //System.out.print("\n");

        return result;
    }
}
