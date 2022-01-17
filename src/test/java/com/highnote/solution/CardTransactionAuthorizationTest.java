package com.highnote.solution;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CardTransactionAuthorizationTest {


    @Test
    public void testMain() throws IOException {
        String[] arguments = new String[]{"/Users/anupamojha/InterviewPrep/transactions.txt"
                , "/Users/anupamojha/InterviewPrep/output.txt"};
        CardTransactionAuthorization.main(arguments );

        BufferedReader fr1 = new BufferedReader(new FileReader(arguments[0]));
        int count1 = 0;
        while(fr1.readLine() != null){
            count1++;
        }
        fr1.close();

        BufferedReader fr2 = new BufferedReader(new FileReader(arguments[1]));
        int count2 = 0;
        while(fr2.readLine() != null){
            count2++;
        }
        fr2.close();
        File out = new File(arguments[1]);
        Assert.assertTrue(out.exists());
        Assert.assertTrue(count1 == count2);
    }


}
