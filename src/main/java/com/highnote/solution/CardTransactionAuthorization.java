package com.highnote.solution;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CardTransactionAuthorization {

    //private static final Map<Integer, BitMapFieldMeta> bitMapMetaMap = new HashMap<>();
    //static CardTransactionHelper helper = new CardTransactionHelper();

    public static void main(String args[]) throws IOException {


        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as a escape sequence)

        // Creating an object of BufferedReader class
        BufferedReader br;
        try {
            File file = new File(args[0]);
            //String directory =
            br = new BufferedReader(new FileReader(file));

            // Declaring a string variable
            String st;
            // Condition holds true till
            // there is character in a string

            List<String> responses = new ArrayList<>();
            while ((st = br.readLine()) != null) {
                StringBuilder builder = new StringBuilder(st);
                if (st != null && st.length() != 0) {
                    System.out.println(st);
                    TransactionDetails reqDetails = CardTransactionHelper.parseTx(builder);
                    TransactionDetails response = CardTransactionHelper.generateResponse(reqDetails);
                    String responseStr = CardTransactionHelper.populateResponseStr(response);
                    //System.out.println(responseStr);
                    responses.add(responseStr);
                }
            }
            br.close();
            if (!responses.isEmpty()) {
                BufferedWriter writer = null;
                File outputFile = new File(args[1]);

                writer = new BufferedWriter(new FileWriter(outputFile));
                for (String response : responses) {
                    try {
                        writer.write(response);
                        writer.write("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(outputFile.getAbsolutePath());
                writer.close();
            }


        } catch (Exception e) {
            System.out.println("File not found or parsing exception");
        }


    }
}


class TransactionDetails {
    private String authCode;
    private String bitMap;
    private String pan;
    private String exp;
    private Double amount;
    private String responseCode;
    private String cardHolderName;
    private String zip;
    private String binBitMap;
    private String originalString;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getBitMap() {
        return bitMap;
    }

    public void setBitMap(String bitMap) {
        this.bitMap = bitMap;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getBinBitMap() {
        return binBitMap;
    }

    public void setBinBitMap(String binBitMap) {
        this.binBitMap = binBitMap;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    @Override
    public String toString() {
        return "TransactionDetails{" +
                "authCode='" + authCode + '\'' +
                ", bitMap='" + bitMap + '\'' +
                ", pan='" + pan + '\'' +
                ", exp='" + exp + '\'' +
                ", amount=" + amount +
                ", responseCode='" + responseCode + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", zip='" + zip + '\'' +
                ", binBitMap='" + binBitMap + '\'' +
                ", originalString='" + originalString + '\'' +
                '}';
    }
}
