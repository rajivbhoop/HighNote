package com.highnote.solution;

import org.junit.Assert;
import org.junit.Test;

public class CardTransactionHelperTest {

    //CardTransactionHelper stub = new CardTransactionHelper();

    @Test
    public void testParseTx(){
        TransactionDetails details = CardTransactionHelper
                .parseTx(new StringBuilder("0100e016411111111111111112250000001000"));
        Assert.assertTrue(details.getExp().equalsIgnoreCase("1225"));
        Assert.assertTrue(details.getPan().equalsIgnoreCase("41111111111111111"));
        Assert.assertTrue(details.getAmount().doubleValue() == 10.0);
    }

    @Test
    public void testGenerateResponse(){
        TransactionDetails requestDetails = CardTransactionHelper
                .parseTx(new StringBuilder("0100ec1651051051051051001225000001100011MASTER YODA90089"));
        TransactionDetails responseDetails  = CardTransactionHelper.generateResponse(requestDetails);
        Assert.assertTrue(responseDetails.getResponseCode().equalsIgnoreCase("OK"));
        Assert.assertTrue(responseDetails.getAuthCode().equalsIgnoreCase("0110"));
        Assert.assertTrue(responseDetails.getCardHolderName().equalsIgnoreCase("MASTER YODA"));
    }

    @Test
    public void testPopulateResponseStr(){
        TransactionDetails requestDetails = CardTransactionHelper
                .parseTx(new StringBuilder("0100ec1651051051051051001225000001100011MASTER YODA90089"));
        TransactionDetails responseDetails  = CardTransactionHelper.generateResponse(requestDetails);
        String responseStr = CardTransactionHelper.populateResponseStr(responseDetails);
        Assert.assertTrue(responseStr.equalsIgnoreCase("0110fc175105105105105100112250000011000OK11MASTER YODA90089"));
    }

    @Test
    public void tstPopulateResponseStr2(){
        TransactionDetails requestDetails = CardTransactionHelper
                .parseTx(new StringBuilder("01006012250000001000"));
        TransactionDetails responseDetails  = CardTransactionHelper.generateResponse(requestDetails);
        String responseStr = CardTransactionHelper.populateResponseStr(responseDetails);
        Assert.assertTrue(responseStr.equalsIgnoreCase("01107012250000001000ER"));
    }


}
