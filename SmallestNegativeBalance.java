import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;



class Result {

    /*
     * Complete the 'smallestNegativeBalance' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts 2D_STRING_ARRAY debts as parameter.
     */

    public static List<String> smallestNegativeBalance(List<List<String>> debts) {
            Map<String, Long> balance = new HashMap<>();

        debts.forEach(debt -> {

            String borrower = debt.get(0); //devedor
            String lender = debt.get(1); //credor
            Long value = Long.parseLong(debt.get(2)); //valor
            Long currentValue; //atual
            Long newValue; //novo valor

            //calculo dos debitos
            currentValue = balance.get(borrower) == null ? 0L : balance.get(borrower);
            newValue = currentValue - value;
            balance.put(borrower, newValue);

            //calculo dos creditos
            currentValue = balance.get(lender) == null ? 0L : balance.get(lender);
            newValue = currentValue + value;
            balance.put(lender, newValue);
        });

        balance.entrySet().removeIf(e -> e.getValue() >= 0);

        System.out.println("negative balance: " + balance);

        if (balance.size() == 0) {
            return Arrays.asList("Nobody has a negative balance");
        }

        //menor valor
        Long lowestValue = 0L;
        for (Map.Entry<String, Long> entry : balance.entrySet()) {
            Long value = entry.getValue();
            if (value < lowestValue) {
                lowestValue = value;
            }
        }

        List<String> negativeBalance = new ArrayList<>();
        for (Map.Entry<String, Long> entry : balance.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();


            if (value == lowestValue) {
                negativeBalance.add(key);
            }
        }
        
        //apresentando os resultados
        System.out.println("before sorting: " + negativeBalance); //antes da ordenação
        
        negativeBalance = negativeBalance.stream().sorted().collect(Collectors.toList());//ordenação
        System.out.println("after sorting: " + negativeBalance);

        return negativeBalance;

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int debtsRows = Integer.parseInt(bufferedReader.readLine().trim());
        int debtsColumns = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> debts = new ArrayList<>();

        IntStream.range(0, debtsRows).forEach(i -> {
            try {
                debts.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<String> result = Result.smallestNegativeBalance(debts);

        bufferedWriter.write(
            result.stream()
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}